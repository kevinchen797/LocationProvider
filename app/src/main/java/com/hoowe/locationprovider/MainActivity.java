package com.hoowe.locationprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationTracker;
import hoowe.locationmanagerlibrary.hoowe.OnLocationUpdatedListener;
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

//        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationUpdatedListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 1 Latitude = " + bdLocation.getLatitude());
//            }
//        });
//
//        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationUpdatedListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                Log.d(TAG, "onReceiveLocation 2 Latitude = " + bdLocation.getLatitude());
//            }
//        });

        HooweLocationProvider.getInstance().startTracker(3000, new OnLocationUpdatedListener() {

            @Override
            public void onReceiveLocation(HooweLocation hooweLocation) {
                Log.d(TAG, "onReceiveLocation 3");
                Log.d(TAG, "onReceiveLocation 3 time = " + hooweLocation.getLocTimeText());
                Log.d(TAG, "currentTime = " + System.currentTimeMillis());
            }

            @Override
            public void onReceiveLocation(List<HooweLocation> list) {

            }

            @Override
            public void onLocationTrackerExist() {
                Log.d(TAG, "onLocationTrackerExist 1");
            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });

        HooweLocationProvider.getInstance().startTracker(3000, new OnLocationUpdatedListener() {
            @Override
            public void onReceiveLocation(HooweLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation 3");
                Log.d(TAG, "onReceiveLocation 4 Latitude = " + TimeUtils.string2Millis(bdLocation.getLocTimeText()));
            }

            @Override
            public void onReceiveLocation(List<HooweLocation> list) {

            }

            @Override
            public void onLocationTrackerExist() {
                Log.d(TAG, "onLocationTrackerExist 2");
            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HooweLocationProvider.getInstance().endTracker();
    }
}
