package com.hoowe.locationprovider.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hoowe.locationprovider.R;
import com.hoowe.locationprovider.util.LocationUtils;

import java.util.List;

import hoowe.locationmanagerlibrary.hoowe.HooweLocation;
import hoowe.locationmanagerlibrary.hoowe.HooweLocationProvider;
import hoowe.locationmanagerlibrary.hoowe.OnLocationUpdatedListener;
import hoowe.locationmanagerlibrary.utils.TimeUtils;

/**
 * Created by chen.mingyao on 2017/9/7.
 */

public class TimeLocationActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private TextView tvMessage;
    private Button btLocation;
    private EditText etTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_time);
        btLocation = (Button) findViewById(R.id.bt_location);
        etTime = (EditText) findViewById(R.id.et_time);
        tvMessage = (TextView) findViewById(R.id.tv_content_time);
        btLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_location:
                String time = etTime.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    Toast.makeText(getApplicationContext(), "请输入时间", Toast.LENGTH_SHORT).show();
                } else {
                    HooweLocationProvider.getInstance().getLocationByTime(TimeUtils.string2Millis(time), new OnLocationUpdatedListener() {
                        @Override
                        public void onReceiveLocation(HooweLocation location) {
                            LocationUtils.displayLocation(location,tvMessage);
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
                }
                break;
        }
    }
}
