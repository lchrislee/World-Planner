package com.lchrislee.worldplanner.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.presenters.HomePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * The main entry point. The user can view all their worlds and all entities in their worlds.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseFragmentActivity
{
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @ViewById NavigationView homeNavigation;

    @ViewById DrawerLayout homeDrawer;

    @ViewById Toolbar toolbar;

    @ViewById ViewPager homeViewPager;

    @ViewById TabLayout homeTabs;

    private HomePresenter mHomePresenter;

    @Override
    protected void onPostCreate (@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initializeNavigation();
    }

    private void initializeNavigation () {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull final MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home:
                mHomePresenter.onHomeSelected();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @AfterViews
    protected void onViewLoaded() {
        mHomePresenter = new HomePresenter(homeNavigation, homeDrawer, toolbar, homeViewPager, homeTabs);
        mHomePresenter.attach(this);
    }
}
