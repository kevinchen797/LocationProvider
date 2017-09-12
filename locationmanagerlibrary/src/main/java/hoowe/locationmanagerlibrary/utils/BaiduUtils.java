package hoowe.locationmanagerlibrary.utils;

import android.util.Log;

import com.baidu.location.BDLocation;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;

public class BaiduUtils {
    public final static String CoorType_GCJ02 = "gcj02";
    public final static String CoorType_BD09LL = "bd09ll";
    public final static String CoorType_BD09MC = "bd09";
    /***
     *61 ： GPS定位结果，GPS定位成功。
     *62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
     *63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     *65 ： 定位缓存的结果。
     *66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     *67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     *68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     *161： 网络定位结果，网络定位定位成功。
     *162： 请求串密文解析失败。
     *167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     *502： key参数错误，请按照说明文档重新申请KEY。
     *505： key不存在或者非法，请按照说明文档重新申请KEY。
     *601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
     *602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
     *501～700：key验证失败，请按照说明文档重新申请KEY。
     */

    public static float[] EARTH_WEIGHT = {0.1f, 0.2f, 0.4f, 0.6f, 0.8f}; // 推算计算权重_地球
    //public static float[] MOON_WEIGHT = {0.0167f,0.033f,0.067f,0.1f,0.133f};
    //public static float[] MARS_WEIGHT = {0.034f,0.068f,0.152f,0.228f,0.304f};

    /**
     * 判断位置是否可用
     *
     * @param bdLocation
     * @return
     */
    public static final boolean isValidLocation(BDLocation bdLocation, BDLocation cashLocation) {
        boolean isValid = false;
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
            // 当前为GPS定位结果
            isValid = true;
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            // 当前为网络定位结果
            if (cashLocation == null || !cashLocation.getLocationID().equals(bdLocation.getLocationID()))
                isValid = true;
        } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
            // 当前为离线定位结果
        } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
            // 当前网络定位失败
            // 可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
            // 当前网络不通
        } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
            // 当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
            // 可进一步参考onLocDiagnosticMessage中的错误返回码
        }

        return isValid;
    }

    /**
     * 拼装 HooweLocation
     *
     * @param bdLocation
     * @return
     */
    public static HooweLocation assemblyLocation(BDLocation bdLocation) {

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

    public static void prinftBDLocation(BDLocation bdLocation) {
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
