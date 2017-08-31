package hoowe.locationmanagerlibrary.hoowe;

/**
 * Created by Admin on 2017/8/28.
 */

public class HooweLocation {
    /**
     *  定位唯一ID，v7.2版本新增，用于排查定位问题
     */
    private String locationID;
    /**
     *  定位类型
     */
    private int locType;
    /**
     *  定位时间
     */
    private long locTime;
    /**
     *  定位时间
     */
    private String locTimeText;
    /**
     *  纬度信息
     */
    private double latitude;
    /**
     *  经度信息
     */
    private double longitude;
    /**
     *  定位精准度
     */
    private float radius;
    /**
     *  地址信息
     */
    private String addrStr;
    /**
     *  国家信息
     */
    private String country;
    /**
     *  国家码
     */
    private String countryCode;
    /**
     *  城市信息
     */
    private String city;
    /**
     *  城市码
     */
    private String cityCode;
    /**
     *  区县信息
     */
    private String district;
    /**
     *  街道信息
     */
    private String street;
    /**
     *  街道码
     */
    private String streetNumber;
    /**
     *  当前位置描述信息
     */
    private String locationDescribe;
    /**
     *  室内精准定位下，获取楼宇ID
     */
    private String buildingID;
    /**
     *  室内精准定位下，获取楼宇名称
     */
    private String buildingName;
    /**
     *  室内精准定位下，获取当前位置所处的楼层信息
     */
    private String floor;
    /**
     *  GPS定位结果:当前速度，单位：公里每小时
     */
    private float  speed;
    /**
     *  GPS定位结果:当前卫星数
     */
    private int satelliteNumber;
    /**
     *  GPS定位结果:海拔高度信息，单位米
     */
    private double altitude;
    /**
     *  GPS定位结果:方向信息，单位度
     */
    private float direction;
    /**
     *  网络定位结果:运营商信息
     */
    private int operators;

    public HooweLocation() {

    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public int getLocType() {
        return locType;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }

    public long getLocTime() {
        return locTime;
    }

    public void setLocTime(long locTime) {
        this.locTime = locTime;
    }

    public String getLocTimeText() {
        return locTimeText;
    }

    public void setLocTimeText(String locTimeText) {
        this.locTimeText = locTimeText;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getLocationDescribe() {
        return locationDescribe;
    }

    public void setLocationDescribe(String locationDescribe) {
        this.locationDescribe = locationDescribe;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getSatelliteNumber() {
        return satelliteNumber;
    }

    public void setSatelliteNumber(int satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public int getOperators() {
        return operators;
    }

    public void setOperators(int operators) {
        this.operators = operators;
    }

    @Override
    public String toString() {
        return "HooweLocation{" +
                "locationID='" + locationID + '\'' +
                ", locType=" + locType +
                ", locTime='" + locTime + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radius=" + radius +
                ", addrStr=" + addrStr +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", locationDescribe='" + locationDescribe + '\'' +
                ", buildingID='" + buildingID + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", floor='" + floor + '\'' +
                ", speed=" + speed + '\'' +
                ", satelliteNumber=" + satelliteNumber + '\'' +
                ", altitude=" + altitude + '\'' +
                ", direction=" + direction + '\'' +
                ", operators=" + operators + '\'' +
                '}';
    }
}
