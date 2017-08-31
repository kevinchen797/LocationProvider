package hoowe.locationmanagerlibrary.hoowe;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import hoowe.locationmanagerlibrary.db.LocationDBHelper;
import hoowe.locationmanagerlibrary.utils.TimeUtils;

/**
 * @author DreamFish
 */
public class HooweLocationTracker extends BDAbstractLocationListener {
    public static final String TAG = "HooweLocationTracker";

    private Context mContext;

    private LocationClient client = null;

    private LocationClientOption mOption, DIYoption;

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
                client.setLocOption(getDefaultLocationClientOption());
            }
        }
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
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(LocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (client.isStarted())
                client.stop();
            DIYoption = option;
            client.setLocOption(option);
        }
        return isSuccess;
    }

    public LocationClientOption getOption() {
        return DIYoption;
    }

    /***
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(true);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
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
        Log.d(TAG, "tracker onReceiveLocation");
        prinftBDLocation(bdLocation);
        HooweLocation location = assemblyLocation(bdLocation);
        locationInsert(location);
        if (!HooweLocationProvider.getInstance().isHasTracker()) {
            // 每次位置更新回调通知界面
            mListener.onReceiveLocation(location);
            unregisterListener();
        }
    }

    /**
     * 拼装 HooweLocation
     *
     * @param bdLocation
     * @return
     */
    private HooweLocation assemblyLocation(BDLocation bdLocation) {

        HooweLocation location = new HooweLocation();
        location.setLocationID(bdLocation.getLocationID());
        location.setLocType(bdLocation.getLocType());
        location.setLocTime(TimeUtils.string2Millis(bdLocation.getTime()));
        location.setLocTimeText(bdLocation.getTime());
        location.setLatitude(bdLocation.getLatitude());
        location.setLongitude(bdLocation.getLongitude());
        location.setRadius(bdLocation.getRadius());
        location.setAddrStr(bdLocation.getAddrStr());
        location.setCountry(bdLocation.getCountry());
        location.setCountryCode(bdLocation.getCountryCode());
        location.setCity(bdLocation.getCity());
        location.setCityCode(bdLocation.getCityCode());
        location.setDistrict(bdLocation.getDistrict());
        location.setStreet(bdLocation.getStreet());
        location.setStreetNumber(bdLocation.getStreetNumber());
        location.setLocationDescribe(bdLocation.getLocationDescribe());
        location.setBuildingID(bdLocation.getBuildingID());
        location.setBuildingName(bdLocation.getBuildingName());
        location.setFloor(bdLocation.getFloor());
        location.setSpeed(bdLocation.getSpeed());
        location.setSatelliteNumber(bdLocation.getSatelliteNumber());
        location.setAltitude(bdLocation.getAltitude());
        location.setDirection(bdLocation.getDirection());
        location.setOperators(bdLocation.getOperators());

        return location;
    }


    private void locationInsert(HooweLocation location) {
        // 将数据插入数据库
        LocationDBHelper.getHelper(mContext).locationInsert(location);
        if (location.getLocType() == BDLocation.TypeGpsLocation) {
            // 当前为GPS定位结果，可获取以下信息

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
            // 当前为网络定位结果，可获取以下信息
            // 将数据插入数据库
            LocationDBHelper.getHelper(mContext).locationInsert(location);
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
            // 当前为离线定位结果

        } else if (location.getLocType() == BDLocation.TypeServerError) {
            // 当前网络定位失败
            // 可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            // 当前网络不通

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            // 当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
            // 可进一步参考onLocDiagnosticMessage中的错误返回码

        }

    }

    private void prinftBDLocation(BDLocation bdLocation) {
        Log.d("@@@", "====================BDLocation Strat====================");
        String str = "BDLocation{" + "locationID='" + bdLocation.getLocationID() + '\'' +
                ", locType=" + bdLocation.getLocType() +
                ", locTime='" + bdLocation.getTime() + '\'' +
                ", latitude=" + bdLocation.getLatitude() +
                ", longitude=" + bdLocation.getLongitude() +
                ", radius=" + bdLocation.getRadius() +
                ", addrStr=" + bdLocation.getAddrStr() +
                ", country='" + bdLocation.getCountry() + '\'' +
                ", countryCode='" + bdLocation.getCountryCode() + '\'' +
                ", city='" + bdLocation.getCity() + '\'' +
                ", cityCode='" + bdLocation.getCityCode() + '\'' +
                ", district='" + bdLocation.getDistrict() + '\'' +
                ", street='" + bdLocation.getStreet() + '\'' +
                ", streetNumber='" + bdLocation.getStreetNumber() + '\'' +
                ", locationDescribe='" + bdLocation.getLocationDescribe() + '\'' +
                ", buildingID='" + bdLocation.getBuildingID() + '\'' +
                ", buildingName='" + bdLocation.getBuildingName() + '\'' +
                ", floor='" + bdLocation.getFloor() + '\'' +
                ", speed=" + bdLocation.getSpeed() + '\'' +
                ", satelliteNumber=" + bdLocation.getSatelliteNumber() + '\'' +
                ", altitude=" + bdLocation.getAltitude() + '\'' +
                ", direction=" + bdLocation.getDirection() + '\'' +
                ", operators=" + bdLocation.getOperators() + '\'' +
                "}";
        Log.d("@@@", str);
        Log.d("@@@", "====================BDLocation End====================");
    }
}
