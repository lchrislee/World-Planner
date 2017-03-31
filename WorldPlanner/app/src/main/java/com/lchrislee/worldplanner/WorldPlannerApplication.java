package com.lchrislee.worldplanner;

import android.support.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.orm.SugarApp;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by chrisl on 3/26/17.
 */

public class WorldPlannerApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        StoryWorld.last(StoryWorld.class);
        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
            CrashlyticsCore core = new CrashlyticsCore.Builder()
                    .disabled(BuildConfig.DEBUG)
                    .build();
            Fabric.with(this, core);
        }
        else
        {
            Timber.plant(new CrashlyticsTree());
        }
    }

    private class CrashlyticsTree extends Timber.Tree
    {
        private static final String KEY_PRIORITY = "priority";
        private static final String KEY_TAG = "tag";
        private static final String KEY_MESSAGE = "message";

        @Override
        protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
            {
                return;
            }

            Crashlytics.setInt(KEY_PRIORITY, priority);
            Crashlytics.setString(KEY_TAG, tag);
            Crashlytics.setString(KEY_MESSAGE, message);

            if (t == null)
            {
                Crashlytics.logException(new Exception(message));
            }
            else
            {
                Crashlytics.logException(t);
            }
        }
    }
}
