package com.lchrislee.worldplanner;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by chrisl on 3/26/17.
 */

public class WorldPlannerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }
        // TODO Add a crash reporting library and use Timber to report logs to it.
        // This can be done by subclassing Timber.Tree and overriding the log method.
    }
}
