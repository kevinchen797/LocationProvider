package com.hoowe.locationprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import hoowe.locationmanagerlibrary.HooweLocationProvider;
import hoowe.locationmanagerlibrary.LocationTracker;
import hoowe.locationmanagerlibrary.OnLocationTrackerListener;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    LocationTracker mManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
//        mManager = new LocationTracker(this);
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

        HooweLocationProvider.getInstance().startTracker(3, new OnLocationTrackerListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation 3 Latitude = " + bdLocation.getLatitude());
            }

            @Override
            public void onLocationTrackerExist() {
                Log.d(TAG, "onLocationTrackerExist 1");
            }
        });

        HooweLocationProvider.getInstance().startTracker(3, new OnLocationTrackerListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation 4 Latitude = " + bdLocation.getLatitude());
            }

            @Override
            public void onLocationTrackerExist() {
                Log.d(TAG, "onLocationTrackerExist 2");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HooweLocationProvider.getInstance().endTracker();
    }
}
