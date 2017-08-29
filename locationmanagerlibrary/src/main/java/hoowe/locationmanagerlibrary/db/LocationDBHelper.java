package hoowe.locationmanagerlibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;

/**
 * Created by Admin on 2017/8/28.
 */

public class LocationDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    private static SQLiteDatabase db = null;

    private Context mContext = null;

    private volatile static LocationDBHelper dbHelper = null;

    private TableLocation tableLocation = TableLocation.getInstance(this);

    private boolean isDBReading = false;

    private boolean isDBWriting = false;
    /**
     * 处理数据库写入消息事件
     */
    private Handler dbWriteHandler = null;
    /**
     * 数据库写入操作 - 插入
     */
    private static final int DB_WRITE_ACTION_TYPE_INSERT_CV = 0;
    /**
     * 数据库写入操作 - 批量插入
     */
    private static final int DB_WRITE_ACTION_TYPE_INSERT_CV_LIST = 1;
    /**
     * 数据库写入操作 - 更新
     */
    private static final int DB_WRITE_ACTION_TYPE_UPDATE = 2;
    /**
     * 数据库写入操作 - 删除
     */
    private static final int DB_WRITE_ACTION_TYPE_DELETE = 3;

    public static LocationDBHelper getHelper(Context context) {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if(dbHelper == null){
            //同步块，线程安全的创建实例
            synchronized (LocationDBHelper.class) {
                //再次检查实例是否存在，如果不存在才真正的创建实例
                dbHelper = new LocationDBHelper(context);
            }
        }
        return dbHelper;
    }

    public LocationDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public LocationDBHelper(Context context) {
        super(context, DBDefine.DBNAME, null, DBDefine.DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        db = database;
        //创建数据表
        createDBTable();
    }

    /**
     * 创建数据表
     */
    private void createDBTable() {
        Log.e(TAG ,":DB CREATE");
        db.execSQL(DBDefine.CREATE_T_LOCATION);
    }

    /**
     * 清除数据库
     */
    private void dropTable() {
        Log.e(TAG, ":DB DROP");
        if ((DBDefine.Tables != null) && (DBDefine.Tables.length > 0)) {
            for (int i = 0; i < DBDefine.Tables.length; i++) {
                String del = "DROP TABLE IF EXISTS " + DBDefine.Tables[i];
                db.execSQL(del);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG,"database onUpgrade: old version:" + oldVersion
                + " newVersion: " + newVersion);
        if (database != null) {
            db = database;
            dropTable();
            db.setVersion(DBDefine.DBVERSION);
            onCreate(database);
        }else {
            Log.e(TAG,"### database null");
        }
    }

    /**
     * ********************DB属性操作************************
     */
    public void DatabaseReadableClose(SQLiteDatabase database) {
        try {
            database.close();
            this.isDBReading = false;
        } catch (Exception exception) {
            Log.e(TAG, ": [DB Exception->]" + exception.getMessage());
        }
    }

    public SQLiteDatabase DatabaseReadableGet() {
        try {
            if ((!this.isDBReading) && (!this.isDBWriting)) {
                this.isDBReading = true;
                return getReadableDatabase();
            }
        } catch (Exception exception) {
            Log.e(TAG, ":[DB Exception->]" + exception.getMessage());

            this.isDBReading = false;
        }

        return null;
    }

    public void DatabaseWritableClose(SQLiteDatabase database) {
        try {
            database.close();
            this.isDBWriting = false;
        } catch (Exception exception) {
            Log.e(TAG, ":[DB Exception->]" + exception.getMessage());
        }
    }

    public SQLiteDatabase DatabaseWritableGet() {
        try {
            if ((!this.isDBReading) && (!this.isDBWriting)) {
                this.isDBWriting = true;
                return getWritableDatabase();
            } else {
                Log.d(TAG, "[DB] get writeable database err");
                Thread.sleep(10);
            }
        } catch (Exception exception) {
            Log.e(TAG, "[DB Exception->]" + exception.getMessage());
            this.isDBWriting = false;
        }

        return null;
    }


    /**
     * ********************发送DB写操作 Action************************
     */

    /**
     * 写操作类
     */
    private class DBWriteAction {
        public String command = "";
        public Object value = null;

        public DBWriteAction() {
        }
    }

    /**
     * 处理数据库操作
     *
     * @param action
     * @param command
     * @param value
     */
    private void dbActionDo(int action, String command, Object value) {
        for (int i = 400; i > 0; i--) {
            if (this.dbWriteHandler != null) {
                DBWriteAction writeAction = new DBWriteAction();
                writeAction.command = command;
                writeAction.value = value;

                Message message = this.dbWriteHandler.obtainMessage();
                message.arg1 = action;
                message.obj = writeAction;

                this.dbWriteHandler.sendMessage(message);
                return;
            } else {
                Log.i(TAG, "[DB Waiting ready!!]");
                try {
                    Thread.sleep(5L);
                } catch (Exception exception) {

                }
            }
        }
    }

    private void dbActionRun() {
        this.dbWriteThread.start();
    }

    /**
     * 数据库插入
     * @param table
     * @param values
     */
    protected void insert(String table, ContentValues values) {
        Log.d(TAG, ":[DB]insert  tableName=[" + table + "] cv = [" + values.toString() + "]");

        dbActionDo(DB_WRITE_ACTION_TYPE_INSERT_CV, table, values);
    }

    /**
     * 数据库批量插入
     * @param table
     * @param valuesList
     */
    protected void insert(String table, List<ContentValues> valuesList) {
        Log.d(TAG, ":[DB]insert  tableName=[" + table + "]");

        dbActionDo(DB_WRITE_ACTION_TYPE_INSERT_CV_LIST, table, valuesList);
    }

    /**
     * 数据库更新
     * @param command
     */
    protected void update(String command) {
        Log.d(TAG, ":[DB]update  sql=[" + command + "]");

        dbActionDo(DB_WRITE_ACTION_TYPE_UPDATE, command, null);
    }

    /**
     * 数据库删除
     * @param command
     */
    protected void del(String command) {
        Log.d(TAG, ":[DB]del sql=[" + command + "]");

        dbActionDo(DB_WRITE_ACTION_TYPE_DELETE, command, null);
    }

    /**
     * 数据库写线程
     */
    private Thread dbWriteThread = new Thread() {
        public void run() {
            Log.i(TAG, ":[DB Action Thread Begin]");

            Looper.prepare();

            dbWriteHandler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message inputMessage) {
                    Log.i(TAG, ":handle db write message");
                    DBWriteAction dbWriteAction = (DBWriteAction) inputMessage.obj;
                    int actionType = inputMessage.arg1;
                    String command = dbWriteAction.command;

                    try {
                        SQLiteDatabase database = DatabaseWritableGet();


                        if (database != null) {
                            try {
                                Log.i(TAG, "[DB Action Begin] actionType = " + actionType + "; command = " + command);

                                switch (actionType) {
                                    case DB_WRITE_ACTION_TYPE_INSERT_CV:
                                        ContentValues values = (ContentValues) dbWriteAction.value;

                                        if (values != null) {
                                            database.beginTransaction();
                                            database.insert(command, null, values);
                                            database.setTransactionSuccessful();
                                            database.endTransaction();
                                        }
                                        break;
                                    case DB_WRITE_ACTION_TYPE_INSERT_CV_LIST:
                                        @SuppressWarnings("unchecked")
                                        List<ContentValues> valuesList = (List<ContentValues>) dbWriteAction.value;

                                        if (valuesList != null) {
                                            for (ContentValues contentValue : valuesList) {
                                                database.beginTransaction();
                                                database.insert(command, null, contentValue);
                                                database.setTransactionSuccessful();
                                                database.endTransaction();
                                            }
                                        }
                                        break;
                                    case DB_WRITE_ACTION_TYPE_UPDATE:
                                        database.beginTransaction();
                                        database.execSQL(command);
                                        database.setTransactionSuccessful();
                                        database.endTransaction();
                                        break;
                                    case DB_WRITE_ACTION_TYPE_DELETE:
                                        database.beginTransaction();
                                        database.execSQL(command);
                                        database.setTransactionSuccessful();
                                        database.endTransaction();
                                        break;
                                }
                                Log.i(TAG, "[DB Action End]");
                            } catch (Exception exception) {
                                Log.e(TAG, "[DB Exception->]" + exception.getMessage() + ";[command->]" + command);
                            }

                            try {
                                DatabaseWritableClose(database);
                            } catch (Exception exception) {
                                Log.e(TAG, "ERROR close:" + exception.getMessage());
                            }
                        } else {
                            Log.e(TAG, "ERROR readable database is Null");
                        }

                    } catch (Exception exception) {
                        Log.e(TAG, "ERROR getWritableDatabase: " + exception.getMessage());
                    }
                }
            };

            try {
                Thread.sleep(10);
            } catch (Exception exception) {

            }
            Log.i(TAG, ":[DB Action Thread END]");
            Looper.loop();
            return;
        }
    };

    /***************** 位置信息操作 ****************/

    /**
     * 插入新数据
     *
     * @param location
     */
    public void locationInsert(HooweLocation location) {
        tableLocation.locationInsert(location);
    }

    /**
     * 删除位置信息
     *
     * @param locationID
     */
    public void locationRemove(String locationID) {
        tableLocation.locationRemove(locationID);
    }

    /**
     * 根据 locationID 更新位置
     *
     * @param location
     */
    public void locationUpdate(HooweLocation location) {
        tableLocation.locationUpdate(location);
    }

    /**
     * 获取最新的位置信息
     * @return
     */
    public HooweLocation getLatestLocation() {
        return tableLocation.getLatestLocation();
    }

    /**
     * 查询时间段的位置信息
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public List<HooweLocation> locDBLoadByPeriod(long startTime, long endTime) {
        return tableLocation.locDBLoadByPeriod(startTime, endTime);
    }

    /**
     * 查询时间段的位置信息
     *
     * @param time   指定时间
     * @return 返回值可能为 null ，注意处理该返回
     */
    public HooweLocation locDBLoadByTime(long time) {
        return tableLocation.locDBLoadByTime(time, HooweLocationProvider.getInstance().getmFrequency());
    }

}
