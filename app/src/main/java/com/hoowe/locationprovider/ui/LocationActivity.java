package com.hoowe.locationprovider.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hoowe.locationprovider.R;
import com.hoowe.locationprovider.util.LocationUtils;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;
import hoowe.locationmanagerlibrary.hoowe.OnLocationUpdatedListener;

/**
 * Created by chen.mingyao on 2017/9/7.
 */

public class LocationActivity extends CheckPermissionsActivity implements OnClickListener {

    private TextView tvMessage;
    private Button btLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        btLocation = (Button) findViewById(R.id.bt_location);
        tvMessage = (TextView) findViewById(R.id.tv_location_message);
        btLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_location:
                HooweLocationProvider.getInstance().getCurrentLocation(new OnLocationUpdatedListener() {
                    @Override
                    public void onReceiveLocation(HooweLocation location) {
                        LocationUtils.displayLocation(location, tvMessage);
                    }

                    @Override
                    public void onReceiveLocation(List<HooweLocation> LocationList) {
                    }

                    @Override
                    public void onLocationTrackerExist() {
                    }

                    @Override
                    public void onLocationTrackerNotRun() {
                    }
                });
                break;
        }
    }
}
