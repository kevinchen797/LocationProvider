package com.hoowe.locationprovider.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.location.LocationClientOption;
import com.hoowe.locationprovider.R;
import com.hoowe.locationprovider.util.LocationUtils;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;
import hoowe.locationmanagerlibrary.hoowe.OnLocationUpdatedListener;
import hoowe.locationmanagerlibrary.utils.BaiduUtils;

/**
 * Created by chen.mingyao on 2017/9/7.
 */

public class LocationTrackActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ToggleButton tbTrack;
    private TextView tvMessage;
    public static final String TAG = "LocationTrackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        tbTrack = (ToggleButton) findViewById(R.id.tb_track);
        tvMessage = (TextView) findViewById(R.id.tv_location_message);

        tbTrack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startTrack();
                } else {
                    stopTrack();
                }
            }

        });
    }

    private void startTrack() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType(BaiduUtils.CoorType_GCJ02);
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);

        HooweLocationProvider.getInstance().startTracker(option, new OnLocationUpdatedListener() {

            @Override
            public void onReceiveLocation(HooweLocation location) {
                LocationUtils.displayLocation(location, tvMessage);
            }

            @Override
            public void onReceiveLocation(List<HooweLocation> list) {

            }

            @Override
            public void onLocationTrackerExist() {
                Toast.makeText(getApplicationContext(), R.string.locationTrackerExist, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onLocationTrackerExist");
            }

            @Override
            public void onLocationTrackerNotRun() {

            }
        });
    }

    private void stopTrack() {
        HooweLocationProvider.getInstance().endTracker();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
