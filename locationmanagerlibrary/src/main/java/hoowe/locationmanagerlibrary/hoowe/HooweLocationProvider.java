package hoowe.locationmanagerlibrary.hoowe;

import android.content.Context;

import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import hoowe.locationmanagerlibrary.db.LocationDBHelper;


/**
 * Created by DreamFisn on 2017/8/26.
 */

public class HooweLocationProvider {

    public static final String TAG = "HooweLocationProvider";

    private Context mContext;

    private LocationClientOption mOption;

    private HooweLocationTracker mTracker;

    private boolean hasTracker = false;

    private static int LOCATION_FREQUENCY = 1000; // 默认定位频度 1 秒/次 (必须 >= 1 秒)

    private int mFrequency = LOCATION_FREQUENCY; // 定位频度

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用时才会装载，从而实现了延迟加载
     *
     * @author dream
     */
    private static class LocationProviderHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static HooweLocationProvider instance = new HooweLocationProvider();
    }

    /**
     * 私有化构造方法
     */
    private HooweLocationProvider() {

    }

    public static HooweLocationProvider getInstance() {
        return LocationProviderHolder.instance;
    }

    /**
     * initialize Map SDK
     *
     * @param context
     */
    public void initialize(Context context) {
        this.mContext = context;
        SDKInitializer.initialize(context);
        LocationDBHelper.getHelper(context).DbActionRun();
    }

    /**
     * 是否存在追踪任务
     *
     * @return
     */
    public boolean isHasTracker() {
        return hasTracker;
    }

    public int getmFrequency() {
        return mFrequency;
    }

    /***
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(LOCATION_FREQUENCY);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    /**
     * 获取当前位置（实时定位）
     *
     * @param listener
     */
    public void getCurrentLocation(OnLocationUpdatedListener listener) {
        this.getCurrentLocation(null, listener);
    }

    /**
     * 获取当前位置（实时定位）
     *
     * @param mOption
     * @param listener
     */
    public void getCurrentLocation(LocationClientOption mOption, OnLocationUpdatedListener listener) {
        if (hasTracker) { // 有追踪任务在运行
            // 获取最新位置返回
            HooweLocation location = LocationDBHelper.getHelper(mContext).getLatestLocation();
            listener.onReceiveLocation(location);
        } else {
            final HooweLocationTracker locationTracker = new HooweLocationTracker(mContext);
            if (mOption != null)
                locationTracker.setLocationOption(mOption);

            locationTracker.registerListener(listener);
            locationTracker.start();
        }
    }

    /**
     * 获取特定时间该设备的位置（前提是指定时间开启过位置追踪）
     *
     * @param time
     */
    public void getLocationByTime(long time, OnLocationUpdatedListener listener) {
        HooweLocation location = LocationDBHelper.getHelper(mContext).locDBLoadByTime(time);
        if (location != null) {
            listener.onReceiveLocation(location);
        } else {
            listener.onLocationTrackerNotRun();
        }
    }

    /**
     * 获取特定时间断该设备的位置（前提是指定时间断开启过位置追踪）
     *
     * @param startTime
     * @param endTime
     */
    public void getLocationByPeriod(Long startTime, long endTime, OnLocationUpdatedListener listener) {
        List<HooweLocation> locationList = new ArrayList<>();
        locationList.addAll(LocationDBHelper.getHelper(mContext).locDBLoadByPeriod(startTime, endTime));
        if (locationList.size() > 0) {
            listener.onReceiveLocation(locationList);
        } else {
            listener.onLocationTrackerNotRun();
        }
    }

    /**
     * 开始位置追踪
     *
     * @param frequency 定位频率 单位：毫秒
     * @param listener
     */
    public void startTracker(int frequency, OnLocationUpdatedListener listener) {
        this.startTracker(null, frequency, listener);
    }

    /**
     * 开始位置追踪
     *
     * @param mOption   定位配置
     * @param frequency 定位频率 单位：毫秒
     * @param listener
     */
    public void startTracker(LocationClientOption mOption, int frequency, OnLocationUpdatedListener listener) {
        if (!hasTracker) {
            mTracker = new HooweLocationTracker(mContext);
            if (mOption != null) {
                if (frequency > 1000) {
                    mOption.setScanSpan(frequency);
                    mFrequency = frequency;
                } else {
                    mOption.setScanSpan(LOCATION_FREQUENCY);
                    mFrequency = LOCATION_FREQUENCY;
                }
            } else {
                mOption = getDefaultLocationClientOption();
            }

            mTracker.setLocationOption(mOption);
            mTracker.registerListener(listener);
            mTracker.start();
            hasTracker = true;
        } else { // 已经存在追踪任务

            // TODO: 2017/8/28 回调通知调用者
            if (listener != null) {
                listener.onLocationTrackerExist();
            }
        }
    }

    /**
     * 结束位置追踪
     */
    public void endTracker() {
        if (mTracker != null) {
            mTracker.unregisterListener();
            mTracker.stop();
        }
        hasTracker = false;

    }

}
