package com.hoowe.locationprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationTracker;
import hoowe.locationmanagerlibrary.hoowe.OnLocationTrackerListener;
import hoowe.locationmanagerlibrary.utils.TimeUtils;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    HooweLocationTracker mManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
//        mManager = new HooweLocationTracker(this);
//        mManager.registerListener(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d("hoowe", "onReceiveLocation");
//                tvContent.setText(bdLocation.getLocationDescribe());
//                mManager.unregisterListener(this);
//            }
//        });
//
//        mManager.setLocationOption(mManager.getDefaultLocationClientOption());
//
//        mManager.start();

//        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationTrackerListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 1 Latitude = " + bdLocation.getLatitude());
//            }
//        });
//
//        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationTrackerListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 2 Latitude = " + bdLocation.getLatitude());
//            }
//        });

//        HooweLocationProvider.getInstance().startTracker(3, new OnLocationTrackerListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 3 time = " + TimeUtils.string2Millis(bdLocation.getTime()));
//                Log.d(TAG, "currentTime = " + System.currentTimeMillis());
//            }
//
//            @Override
//            public void onLocationTrackerExist() {
//                Log.d(TAG, "onLocationTrackerExist 1");
//            }
//        });
//
//        HooweLocationProvider.getInstance().startTracker(3, new OnLocationTrackerListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 4 Latitude = " + TimeUtils.string2Millis(bdLocation.getTime()));
//            }
//
//            @Override
//            public void onLocationTrackerExist() {
//                Log.d(TAG, "onLocationTrackerExist 2");
//            }
//        });

        test();

    }

    private void test() {
        long time = System.currentTimeMillis();
        Log.i(TAG, "currentTimeMillis = " + time);
        List<HooweLocation> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            HooweLocation location = new HooweLocation();
            location.setLocTime(time + new Random().nextInt(10000));
            list.add(location);
        }
        for (HooweLocation loc : list) {
            Log.i(TAG, "locTime = " + loc.getLocTime());
        }
        Log.d(TAG, "@@@ = " + TimeUtils.getClosestLocation2(time, list).getLocTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HooweLocationProvider.getInstance().endTracker();
    }
}
