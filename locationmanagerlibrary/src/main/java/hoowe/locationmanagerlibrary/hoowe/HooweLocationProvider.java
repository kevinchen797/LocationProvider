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
            HooweLocationTracker locationTracker = new HooweLocationTracker(mContext);
            if (mOption != null) {
                locationTracker.setLocationOption(mOption);
            } else {
                locationTracker.setLocationOption(locationTracker.getDefaultLocationClientOption());
            }
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
     * @param listener
     */
    public void startTracker(OnLocationUpdatedListener listener) {
        this.startTracker(null, listener);
    }

    /**
     * 开始位置追踪
     *
     * @param mOption   定位配置
     * @param listener
     */
    public void startTracker(LocationClientOption mOption, OnLocationUpdatedListener listener) {
        if (!hasTracker) {
            mTracker = new HooweLocationTracker(mContext);
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
