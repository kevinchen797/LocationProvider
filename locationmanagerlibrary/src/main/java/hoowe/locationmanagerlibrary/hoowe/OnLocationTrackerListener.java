package hoowe.locationmanagerlibrary.hoowe;


import java.util.List;

/**
 * Created by chen.mingyao on 2017/8/28.
 */

public interface OnLocationTrackerListener {

    void onReceiveLocation(HooweLocation location);

    void onReceiveLocation(List<HooweLocation> LocationList);

    void onLocationTrackerExist();

    void onLocationTrackerNotRun();

}
