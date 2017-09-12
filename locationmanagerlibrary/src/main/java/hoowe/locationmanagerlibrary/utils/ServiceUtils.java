package hoowe.locationmanagerlibrary.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by chen.mingyao on 2017/9/12.
 */

public class ServiceUtils {

    public static final String TRACKER_SERVICE_CLASS_NAME = "hoowe.locationmanagerlibrary.service.TrackerService";

    /**
     * 判断服务是否后台运行
     *
     * @param mContext
     *            Context
     * @param className
     *            判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRun(Context mContext, String className) {
        boolean isRun = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(40);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRun = true;
                break;
            }
        }
        return isRun;
    }
}
