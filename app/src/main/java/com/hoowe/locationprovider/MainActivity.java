package com.hoowe.locationprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import hoowe.locationmanagerlibrary.HooweLocationProvider;
import hoowe.locationmanagerlibrary.LocationManager;
import hoowe.locationmanagerlibrary.OnLocationTrackerListener;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    LocationManager mManager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
//        mManager = new LocationManager(this);
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

        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationTrackerListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation 1 Latitude = " + bdLocation.getLatitude());
            }
        });

        HooweLocationProvider.getInstance().getCurrentLocation(HooweLocationProvider.getInstance().getDefaultLocationClientOption(), new OnLocationTrackerListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation 2 Latitude = " + bdLocation.getLatitude());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            mManager.stop();
        }
    }
}
