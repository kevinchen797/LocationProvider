package hoowe.locationmanagerlibrary;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DreamFisn on 2017/8/26.
 */

public class HooweLocationProvider {

    public static final String TAG = "HooweLocationProvider";

    private Context mContext;

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用时才会装载，从而实现了延迟加载
     * @author dream
     *
     */
    private static class LocationProviderHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static HooweLocationProvider instance = new HooweLocationProvider();
    }

    /**
     * 私有化构造方法
     */
    private HooweLocationProvider(){

    }

    public static HooweLocationProvider getInstance(){
        return LocationProviderHolder.instance;
    }

    public void initialize(Context context) {
        this.mContext = context;
        SDKInitializer.initialize(context);
    }

    public BDLocation getCurrentLocation() {
        return  null;
    }

    public BDLocation getLocationByTime(long time) {
        return null;
    }

    public List<BDLocation> getLocationByPeriod(Long startTime, long endTime) {
        return new ArrayList<>();
    }

}
