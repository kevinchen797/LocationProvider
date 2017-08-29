package hoowe.locationmanagerlibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import hoowe.locationmanagerlibrary.HooweLocation;

/**
 * Created by Admin on 2017/8/28.
 */

public class TableLocation {
    private static final String TAG = "TableChannel";

    private static LocationDBHelper dbHelper;

    private volatile static TableLocation instance = null;

    public TableLocation() {
    }

    public static TableLocation getInstance(LocationDBHelper helper) {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (instance == null) {
            //同步块，线程安全的创建实例
            synchronized (TableLocation.class) {
                //再次检查实例是否存在，如果不存在才真正的创建实例
                instance = new TableLocation();
            }
        }
        dbHelper = helper;
        return instance;
    }

    private HooweLocation queryBroadcastItem(Cursor cursor){
        HooweLocation hooweLocation = new HooweLocation();

        hooweLocation.setLocationID(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.locationID)));
        hooweLocation.setLocType(cursor.getInt(cursor.getColumnIndex(DBDefine.t_location.locType)));
        hooweLocation.setLocTime(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.locTime)));
        hooweLocation.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBDefine.t_location.locLatitude)));
        hooweLocation.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBDefine.t_location.locLongitude)));
        hooweLocation.setRadius(cursor.getFloat(cursor.getColumnIndex(DBDefine.t_location.radius)));
        hooweLocation.setAddrStr(cursor.getFloat(cursor.getColumnIndex(DBDefine.t_location.addrStr)));
        hooweLocation.setCountry(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.country)));
        hooweLocation.setCountryCode(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.countryCode)));
        hooweLocation.setCity(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.city)));
        hooweLocation.setCityCode(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.cityCode)));
        hooweLocation.setDistrict(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.district)));
        hooweLocation.setStreet(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.street)));
        hooweLocation.setStreetNumber(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.streetNumber)));
        hooweLocation.setLocationDescribe(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.locationDescribe)));
        hooweLocation.setBuildingID(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.buildingID)));
        hooweLocation.setBuildingName(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.buildingName)));
        hooweLocation.setFloor(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.floor)));
        hooweLocation.setSpeed(cursor.getFloat(cursor.getColumnIndex(DBDefine.t_location.speed)));
        hooweLocation.setSatelliteNumber(cursor.getInt(cursor.getColumnIndex(DBDefine.t_location.satelliteNumber)));
        hooweLocation.setAltitude(cursor.getDouble(cursor.getColumnIndex(DBDefine.t_location.altitude)));
        hooweLocation.setDirection(cursor.getFloat(cursor.getColumnIndex(DBDefine.t_location.direction)));
        hooweLocation.setOperators(cursor.getInt(cursor.getColumnIndex(DBDefine.t_location.operators)));

        return hooweLocation;
    }

    protected void broadcastAppend(HooweLocation location){
        Log.i(TAG,"[DB] broadCast append");
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBDefine.t_location.locationID, location.getLocationID());
        contentValues.put(DBDefine.t_location.locType,location.getLocType());
        contentValues.put(DBDefine.t_location.locTime,location.getLocTime());
        contentValues.put(DBDefine.t_location.locLatitude,location.getLatitude());
        contentValues.put(DBDefine.t_location.locLongitude,location.getLongitude());
        contentValues.put(DBDefine.t_location.radius,location.getRadius());
        contentValues.put(DBDefine.t_location.addrStr,location.getAddrStr());
        contentValues.put(DBDefine.t_location.country,location.getCountry());
        contentValues.put(DBDefine.t_location.countryCode,location.getCountryCode());
        contentValues.put(DBDefine.t_location.city,location.getCity());
        contentValues.put(DBDefine.t_location.cityCode,location.getCityCode());
        contentValues.put(DBDefine.t_location.district,location.getDistrict());
        contentValues.put(DBDefine.t_location.street,location.getStreet());
        contentValues.put(DBDefine.t_location.streetNumber,location.getStreetNumber());
        contentValues.put(DBDefine.t_location.locationDescribe,location.getLocationDescribe());
        contentValues.put(DBDefine.t_location.buildingID,location.getBuildingID());
        contentValues.put(DBDefine.t_location.buildingName,location.getBuildingName());
        contentValues.put(DBDefine.t_location.floor,location.getFloor());
        contentValues.put(DBDefine.t_location.speed,location.getSpeed());
        contentValues.put(DBDefine.t_location.satelliteNumber,location.getSatelliteNumber());
        contentValues.put(DBDefine.t_location.altitude,location.getAltitude());
        contentValues.put(DBDefine.t_location.direction,location.getDirection());
        contentValues.put(DBDefine.t_location.operators,location.getOperators());

        dbHelper.insert(DBDefine.db_location,contentValues);
    }

}
