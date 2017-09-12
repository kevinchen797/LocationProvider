package hoowe.locationmanagerlibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import hoowe.locationmanagerlibrary.db.LocationDBHelper;
import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.utils.BaiduUtils;

/**
 * Created by chen.mingyao on 2017/9/12.
 */

public class TrackerService extends Service {
    String TAG = this.getClass().getName();

    private Object objLock = new Object();

    private LocationClient client = null;

    private MyBinder binder = new MyBinder();

    private LocationListener mLocationListener = new LocationListener();

    private BDLocation cashLocation = null;

    public class MyBinder extends Binder {
        public TrackerService getService() {
            return TrackerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库线程
        LocationDBHelper.getHelper(this).DbActionRun();

        initLocationClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开启定位
        startLocation();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注册百度地图定位功能
     */
    private void initLocationClient() {
        client = new LocationClient(this);
        client.setLocOption(getDefaultLocationClientOption());
        client.registerLocationListener(mLocationListener);
        start();
    }

    /**
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        LocationClientOption mOption = new LocationClientOption();
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
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
     * @param option
     */
    public void setLocationOption(LocationClientOption option) {
        if (option != null) {
            if (client.isStarted())
                client.stop();
            client.setLocOption(option);
        }
    }

    class LocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.d(TAG, "TrackerService onReceiveLocation");
            if (BaiduUtils.isValidLocation(bdLocation, cashLocation)) {
                BaiduUtils.prinftBDLocation(bdLocation);
                HooweLocation location = BaiduUtils.assemblyLocation(bdLocation);
                // 将数据插入数据库
                LocationDBHelper.getHelper(TrackerService.this).locationInsert(location);
            }
        }
    }

    public void startLocation() {
        if (null != client) {
            if (!client.isStarted()) {
                start();
            }
        } else {
            initLocationClient();
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
                client.registerLocationListener(mLocationListener);
                client.restart();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.unRegisterLocationListener(mLocationListener);
                client.stop();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }
}
