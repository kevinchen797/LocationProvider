package hoowe.locationmanagerlibrary.db;

public final class DBDefine {

    protected static final String DBNAME = "locationdb";
    protected static final int DBVERSION = 1;
    protected static String[] Tables = {"t_location"};
    protected static final String db_location = "t_location";

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS ";
    protected static final String CREATE_T_LOCATION = CREATE + "t_location(ID integer PRIMARY KEY AUTOINCREMENT, " +
            "locationID TEXT,locType INTEGER,locTime INTEGER,locTimeText TEXT,locLatitude REAL,locLongitude REAL,radius REAL, " +
            "addrStr TEXT,country TEXT,countryCode TEXT,city TEXT,cityCode TEXT,district TEXT,street TEXT," +
            "streetNumber TEXT,locationDescribe TEXT,buildingID TEXT,buildingName TEXT,floor TEXT,speed REAL," +
            "satelliteNumber INTEGER,altitude REAL,direction REAL,operators INTEGER)";

    protected static final class t_location {
        protected static final String ID = "ID";
        protected static final String locationID = "locationID"; // 定位唯一ID，v7.2版本新增，用于排查定位问题 <String>
        protected static final String locType = "locType"; // 定位类型 <int>
        protected static final String locTime = "locTime"; // 定位时间 <long> 用于操作数据库
        protected static final String locTimeText = "locTimeText"; //  定位时间 <String> 用于查看
        protected static final String locLatitude = "locLatitude"; // 纬度信息 <double>
        protected static final String locLongitude = "locLongitude"; // 经度信息 <double>
        protected static final String radius = "radius"; // 定位精准度 <float>
        protected static final String addrStr = "addrStr"; // 地址信息 <String>
        protected static final String country = "country"; // 国家信息 <String>
        protected static final String countryCode = "countryCode"; // 国家码 <String>
        protected static final String city = "city"; // 城市信息 <String>
        protected static final String cityCode = "cityCode"; // 城市码 <String>
        protected static final String district = "district"; // 区县信息 <String>
        protected static final String street = "street"; // 街道信息 <String>
        protected static final String streetNumber = "streetNumber"; // 街道码 <String>
        protected static final String locationDescribe = "locationDescribe"; // 当前位置描述信息 <String>
        protected static final String buildingID = "buildingID"; // 室内精准定位下，获取楼宇ID <String>
        protected static final String buildingName = "buildingName"; // 室内精准定位下，获取楼宇名称 <String>
        protected static final String floor = "floor"; // 室内精准定位下，获取当前位置所处的楼层信息 <String>
        protected static final String speed = "speed"; // GPS位置中：当前速度，单位：公里每小时 <float>
        protected static final String satelliteNumber = "satelliteNumber"; // GPS位置中：当前卫星数 <int>
        protected static final String altitude = "altitude"; // GPS位置中：海拔高度信息，单位米 <double>
        protected static final String direction = "direction"; // GPS位置中：方向信息，单位度 <float>
        protected static final String operators = "operators"; // 网络定位结果：运营商信息 <int>
    }
}