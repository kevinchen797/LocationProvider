package hoowe.locationmanagerlibrary.hoowe;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import hoowe.locationmanagerlibrary.db.LocationDBHelper;
import hoowe.locationmanagerlibrary.utils.BaiduUtils;
import hoowe.locationmanagerlibrary.utils.TimeUtils;

/**
 * @author DreamFish
 */
public class HooweLocationTracker extends BDAbstractLocationListener {
    public static final String TAG = "HooweLocationTracker";

    private Context mContext;

    private LocationClient client = null;

    private Object objLock = new Object();

    private OnLocationUpdatedListener mListener;

    /***
     *
     * @param locationContext
     */
    public HooweLocationTracker(Context locationContext) {
        synchronized (objLock) {
            mContext = locationContext;
            if (client == null) {
                client = new LocationClient(locationContext);
//                client.setLocOption(getDefaultLocationClientOption());
            }
        }
    }

    /***
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        LocationClientOption mOption = new LocationClientOption();
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.setIsNeedAltitude(true);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        return mOption;
    }

    /***
     *
     * @param listener
     * @return
     */
    public boolean registerListener(OnLocationUpdatedListener listener) {
        boolean isSuccess = false;
        if (listener != null) {
            this.mListener = listener;
            client.registerLocationListener(this);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener() {
        this.mListener = null;
        client.unRegisterLocationListener(this);
    }

    /***
     *
     * @param option
     */
    public void setLocationOption(LocationClientOption option) {
        if (option != null) {
            if (client.isStarted())
                client.stop();
            client.setLocOption(option);
        }
    }

    public void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.start();
                client.requestLocation();
            }
        }
    }

    public void reStart() {
        synchronized (objLock) {
            if (client != null) {
                client.restart();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }

    /**
     * 定位返回
     *
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.d(TAG, "HooweLocationTracker onReceiveLocation");
        if (BaiduUtils.isValidLocation(bdLocation, null)) {
            BaiduUtils.prinftBDLocation(bdLocation);
            HooweLocation location = BaiduUtils.assemblyLocation(bdLocation);
            // 将数据插入数据库
            LocationDBHelper.getHelper(mContext).locationInsert(location);

            mListener.onReceiveLocation(location);
        }
        if (!HooweLocationProvider.getInstance().isHasTracker()) {
            // 每次位置更新回调通知界面
            unregisterListener();
        }
    }
}
