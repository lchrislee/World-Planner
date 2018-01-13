package com.lchrislee.worldplanner.activities;

import com.lchrislee.worldplanner.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * The loading activity to do any necessary work before going to {@link HomeActivity}.
 */
@EActivity(R.layout.activity_first)
public class FrontActivity extends BaseFragmentActivity
{
    private static final int MINIMUM_TRANSITION_TIME_MS = 1000;

    @AfterViews
    protected void onViewLoaded() {
        goToHome();
    }

    private void goToHome() {
        findViewById(android.R.id.content).postDelayed(() ->
            HomeActivity_.intent(this).start(), MINIMUM_TRANSITION_TIME_MS);
    }
}
