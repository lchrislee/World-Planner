package com.lchrislee.worldplanner.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * The main controller for {@link com.lchrislee.worldplanner.activities.HomeActivity}. This determines
 * how to act and change visuals.
 */
public class HomePresenter
{
    private static final String LOG_TAG = HomePresenter.class.getSimpleName();

    private HomeDelegate mDelegate;

    public interface HomeDelegate {

    }

    public void attach(@NonNull final HomeDelegate delegate) {
        Log.d(LOG_TAG, "Attached home presenter.");
        mDelegate = delegate;
    }

}
