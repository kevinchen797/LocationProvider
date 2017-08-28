package hoowe.locationmanagerlibrary;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;


/**
 * Created by DreamFisn on 2017/8/26.
 */

public class HooweLocationProvider {

    public static final String TAG = "HooweLocationProvider";

    private Context mContext;

    private OnLocationTrackerListener mListener;

    private LocationClientOption mOption;

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用时才会装载，从而实现了延迟加载
     * @author dream
     *
     */
    private static class LocationProviderHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static HooweLocationProvider instance = new HooweLocationProvider();
    }

    /**
     * 私有化构造方法
     */
    private HooweLocationProvider(){

    }

    public static HooweLocationProvider getInstance(){
        return LocationProviderHolder.instance;
    }

    /**
     * initialize Map SDK
     * @param context
     */
    public void initialize(Context context) {
        this.mContext = context;
        SDKInitializer.initialize(context);
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
            mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
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
     * @param mOption
     * @param listener
     */
    public void getCurrentLocation(LocationClientOption mOption, final OnLocationTrackerListener listener) {
        this.mListener = listener;
        final LocationManager locationManager = new LocationManager(mContext);
        locationManager.setLocationOption(mOption);
        locationManager.registerListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (listener != null) {
                    listener.onReceiveLocation(bdLocation);
                    locationManager.unregisterListener(this);

                    // TODO: 2017/8/28 将位置信息插入数据库

                }
            }
        });
        locationManager.start();
    }

    /**
     * 获取特定时间该设备的位置（前提是指定时间开启过位置追踪）
     * @param time
     */
    public void getLocationByTime(long time) {
    }

    /**
     * 获取特定时间断该设备的位置（前提是指定时间断开启过位置追踪）
     * @param startTime
     * @param endTime
     */
    public void getLocationByPeriod(Long startTime, long endTime) {
    }

    /**
     *  开始位置追踪
     * @param mOption
     */
    public void startTracker(LocationClientOption mOption) {

    }

    /**
     *  结束位置追踪
     * @param mOption
     */
    public void endTracker(LocationClientOption mOption) {

    }

}
