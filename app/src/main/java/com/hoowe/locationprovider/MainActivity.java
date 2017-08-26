package com.hoowe.locationprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

import hoowe.locationmanagerlibrary.LocationService;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    LocationService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        service = new LocationService(this);
        service.registerListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d("hoowe", "onReceiveLocation");
                tvContent.setText(bdLocation.getLocationDescribe());
                service.unregisterListener(this);
            }
        });

        service.setLocationOption(service.getDefaultLocationClientOption());

        service.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (service != null) {
            service.stop();
        }
    }
}
