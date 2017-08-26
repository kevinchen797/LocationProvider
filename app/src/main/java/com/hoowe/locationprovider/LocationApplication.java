package com.hoowe.locationprovider;

import android.app.Application;

import hoowe.locationmanagerlibrary.HooweLocationProvider;

/**
 * Created by DreamFisn on 2017/8/26.
 */

public class LocationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HooweLocationProvider.getInstance().initialize(this);
    }
}
