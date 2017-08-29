package hoowe.locationmanagerlibrary.hoowe;

import com.baidu.location.BDLocation;

/**
 * Created by chen.mingyao on 2017/8/28.
 */

public interface OnLocationTrackerListener {

    void onReceiveLocation(BDLocation bdLocation);

    void onLocationTrackerExist();

}
