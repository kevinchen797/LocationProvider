package com.hoowe.locationprovider.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hoowe.locationprovider.R;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;

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
        HooweLocationProvider.getInstance().startTracker();
        Toast.makeText(getApplicationContext(), R.string.locationTrackerExist, Toast.LENGTH_SHORT).show();
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
