package hoowe.locationmanagerlibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;

/**
 * Created by Admin on 2017/8/28.
 */

public class TableLocation {
    private static final String TAG = "TableLocation";

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

    /**
     * 解析查询得到的 Cursor
     *
     * @param cursor
     * @return
     */
    private HooweLocation queryLocationItem(Cursor cursor) {
        HooweLocation hooweLocation = new HooweLocation();

        hooweLocation.setLocationID(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.locationID)));
        hooweLocation.setLocType(cursor.getInt(cursor.getColumnIndex(DBDefine.t_location.locType)));
        hooweLocation.setLocTime(cursor.getLong(cursor.getColumnIndex(DBDefine.t_location.locTime)));
        hooweLocation.setLocTimeText(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.locTimeText)));
        hooweLocation.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBDefine.t_location.locLatitude)));
        hooweLocation.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBDefine.t_location.locLongitude)));
        hooweLocation.setRadius(cursor.getFloat(cursor.getColumnIndex(DBDefine.t_location.radius)));
        hooweLocation.setAddrStr(cursor.getString(cursor.getColumnIndex(DBDefine.t_location.addrStr)));
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
        Log.i(TAG, "解析cursor");
        return hooweLocation;
    }

    /**
     * 插入新数据
     *
     * @param location
     */
    protected void locationInsert(HooweLocation location) {
        Log.i(TAG, "[DB] location insert");
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBDefine.t_location.locationID, location.getLocationID());
        contentValues.put(DBDefine.t_location.locType, location.getLocType());
        contentValues.put(DBDefine.t_location.locTime, location.getLocTime());
        contentValues.put(DBDefine.t_location.locTimeText, location.getLocTimeText());
        contentValues.put(DBDefine.t_location.locLatitude, location.getLatitude());
        contentValues.put(DBDefine.t_location.locLongitude, location.getLongitude());
        contentValues.put(DBDefine.t_location.radius, location.getRadius());
        contentValues.put(DBDefine.t_location.addrStr, location.getAddrStr());
        contentValues.put(DBDefine.t_location.country, location.getCountry());
        contentValues.put(DBDefine.t_location.countryCode, location.getCountryCode());
        contentValues.put(DBDefine.t_location.city, location.getCity());
        contentValues.put(DBDefine.t_location.cityCode, location.getCityCode());
        contentValues.put(DBDefine.t_location.district, location.getDistrict());
        contentValues.put(DBDefine.t_location.street, location.getStreet());
        contentValues.put(DBDefine.t_location.streetNumber, location.getStreetNumber());
        contentValues.put(DBDefine.t_location.locationDescribe, location.getLocationDescribe());
        contentValues.put(DBDefine.t_location.buildingID, location.getBuildingID());
        contentValues.put(DBDefine.t_location.buildingName, location.getBuildingName());
        contentValues.put(DBDefine.t_location.floor, location.getFloor());
        contentValues.put(DBDefine.t_location.speed, location.getSpeed());
        contentValues.put(DBDefine.t_location.satelliteNumber, location.getSatelliteNumber());
        contentValues.put(DBDefine.t_location.altitude, location.getAltitude());
        contentValues.put(DBDefine.t_location.direction, location.getDirection());
        contentValues.put(DBDefine.t_location.operators, location.getOperators());

        dbHelper.insert(DBDefine.db_location, contentValues);
    }

    /**
     * 删除位置信息
     *
     * @param locationID
     */
    protected void locationRemove(String locationID) {
        Log.i(TAG, "[DB] location remove");
        String command = "DELETE FROM " +
                DBDefine.db_location +
                " WHERE " +
                DBDefine.t_location.locationID + "=" + locationID;

        dbHelper.del(command);
    }

    /**
     * 根据 locationID 更新位置
     *
     * @param location
     */
    protected void locationUpdate(HooweLocation location) {
        Log.i(TAG, "[DB] location remove");
        String command = String.format("UPDATE " +
                        DBDefine.db_location +
                        " SET " +
                        DBDefine.t_location.locType + " = '%d', " +
                        DBDefine.t_location.locTime + " = '%d', " +
                        DBDefine.t_location.locTimeText + " = '%s', " +
                        DBDefine.t_location.locLatitude + " = '%f', " +
                        DBDefine.t_location.locLongitude + " = '%f'," +
                        DBDefine.t_location.radius + " = '%f'," +
                        DBDefine.t_location.addrStr + " = '%s'," +
                        DBDefine.t_location.country + " = '%s'," +
                        DBDefine.t_location.countryCode + " = '%s'," +
                        DBDefine.t_location.city + " = '%s'," +
                        DBDefine.t_location.cityCode + " = '%s'," +
                        DBDefine.t_location.district + " = '%s'," +
                        DBDefine.t_location.street + " = '%s'," +
                        DBDefine.t_location.streetNumber + " = '%s'," +
                        DBDefine.t_location.locationDescribe + " = '%s'," +
                        DBDefine.t_location.buildingID + " = '%s'," +
                        DBDefine.t_location.buildingName + " = '%s'," +
                        DBDefine.t_location.floor + " = '%s'," +
                        DBDefine.t_location.speed + " = '%f'," +
                        DBDefine.t_location.satelliteNumber + " = '%d'," +
                        DBDefine.t_location.altitude + " = '%f'," +
                        DBDefine.t_location.direction + " = '%f'," +
                        DBDefine.t_location.operators + " = '%d" +
                        " WHERE " +
                        DBDefine.t_location.locationID + " = '%s'",
                location.getLocType(),
                location.getLocTime(),
                location.getLocTimeText(),
                location.getLatitude(),
                location.getLongitude(),
                location.getRadius(),
                location.getAddrStr(),
                location.getCountry(),
                location.getCountryCode(),
                location.getCity(),
                location.getCityCode(),
                location.getDistrict(),
                location.getStreet(),
                location.getStreetNumber(),
                location.getLocationDescribe(),
                location.getBuildingID(),
                location.getBuildingName(),
                location.getFloor(),
                location.getSpeed(),
                location.getSatelliteNumber(),
                location.getAltitude(),
                location.getDirection(),
                location.getOperators(),
                location.getLocationID());

        dbHelper.update(command);
    }

    /**
     * 获取最新的位置信息
     * @return
     */
    protected HooweLocation getLatestLocation() {
        Log.i(TAG, "[DB] load latest location");

        HooweLocation location = new HooweLocation();

        SQLiteDatabase database = dbHelper.DatabaseReadableGet();

        if (database != null) {
            String str = "SELECT * FROM " + DBDefine.db_location + " ORDER BY " +
                    DBDefine.t_location.ID + " DESC LIMIT(0,1)";
            try {
                Cursor cursor = database.rawQuery(str, null);

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        location = queryLocationItem(cursor);
                    }
                    cursor.close();
                }
            } catch (Exception exception) {
                Log.e(TAG, "[SQL EXCEPTION] " + str + " -> " + exception.getMessage());
            }

            dbHelper.DatabaseReadableClose(database);
        }

        return location;

    }

    /**
     * 查询时间段的位置信息
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    protected List<HooweLocation> locDBLoadByPeriod(long startTime, long endTime) {
        Log.i(TAG, "[DB] location load by period");

        List<HooweLocation> locationList = new ArrayList<>();

        SQLiteDatabase database = dbHelper.DatabaseReadableGet();

        if (database != null) {
            String str = "SELECT * FROM " + DBDefine.db_location + " WHERE " +
                    DBDefine.t_location.locTime + " between " + startTime + " and " +
                    endTime + " ORDER BY " + DBDefine.t_location.locTime + " DESC";
            Log.i(TAG, "sql: " + str);
            try {
                Cursor cursor = database.rawQuery(str, null);

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Log.i(TAG, "获取cursor");
                        HooweLocation location = queryLocationItem(cursor);
                        locationList.add(location);
                    }
                    cursor.close();
                }
            } catch (Exception exception) {
                Log.e(TAG, "[SQL EXCEPTION] " + str + " -> " + exception.getMessage());
            }

            dbHelper.DatabaseReadableClose(database);
        }

        return locationList;
    }

    private static int RECURSIVE_NUM = 4; // 因为最小频率为1秒，每次递归范围扩大10倍，最少4次递归后范围就可以达到一天
    private int recursive = 0;

    /**
     * 查询时间段的位置信息
     *
     * @param time   指定时间
     * @return 返回值可能为 null ，注意处理该返回
     */
    protected HooweLocation locDBLoadByTime(long time, int frequency) {
        Log.i(TAG, "[DB] location load by time");
        HooweLocation location = null;
        List<HooweLocation> locations = new ArrayList<>();
        locations.addAll(locDBLoadByPeriod((time - frequency),
                time + frequency));
        if (locations.size() == 1) {
            return locations.get(0);
        } else if (locations.size() > 1) {
            return getClosestLocation(time, locations);
        } else {
            if (recursive <= RECURSIVE_NUM) {
                recursive++;
                return locDBLoadByTime(time, frequency * 10); // 递归扩大查询范围

            }
        }
        Log.e(TAG, "return null");
        return null;
    }

    /**
     * 获取记录目标时间最近的位置点
     * 因为 locations 是个有序集合
     * 故采用二分查找优化后
     *
     * @param time
     * @param locations
     * @return
     */
    private HooweLocation getClosestLocation(long time, List<HooweLocation> locations) {
        HooweLocation location = null;
        int low = 0, high = locations.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (low < mid && mid < high) { // 中值未与边界重合
                long offsetLeft = Math.abs(locations.get(mid - 1).getLocTime() - time);
                long offectRight = Math.abs(locations.get(mid + 1).getLocTime() - time);
                if (offectRight > offsetLeft) { // 取左边继续折半
                    high = mid;
                } else if (offectRight < offsetLeft) { // 取右边继续折半
                    low = mid;
                } else {
                    location = locations.get(mid);
                    break;
                }
            } else if (mid == low || mid == high) { // 中值与边界重合
                if (mid == low) {
                    // 与左边界重合
                    long offsetLeft = Math.abs(locations.get(mid).getLocTime() - time);
                    long offectRight = Math.abs(locations.get(mid + 1).getLocTime() - time);
                    if (offsetLeft <= offectRight) {
                        location = locations.get(mid);
                        break;
                    } else {
                        location = locations.get(mid + 1);
                        break;
                    }
                } else {
                    // 与右边界重合
                    long offsetLeft = Math.abs(locations.get(mid - 1).getLocTime() - time);
                    long offectRight = Math.abs(locations.get(mid).getLocTime() - time);
                    if (offsetLeft <= offectRight) {
                        location = locations.get(mid - 1);
                        break;
                    } else {
                        location = locations.get(mid);
                        break;
                    }
                }
            }
        }
        return location;
    }

}
