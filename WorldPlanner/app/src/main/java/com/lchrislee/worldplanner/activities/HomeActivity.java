package com.lchrislee.worldplanner.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.presenters.HomePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * The main entry point. The user can view all their worlds and all entities in their worlds.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseFragmentActivity implements HomePresenter.HomeDelegate
{
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @ViewById NavigationView homeNavigation;

    @ViewById DrawerLayout homeDrawer;

    @ViewById Toolbar toolbar;

    @ViewById ViewPager homeViewPager;

    @ViewById TabLayout homeTabs;

    private ImageView headerImage;

    private TextView headerTitle;

    private ActionBarDrawerToggle mDrawerToggle;

    private FragmentStatePagerAdapter mPagerAdapter;

    private Fragment[] fragments = new Fragment[10];

    private HomePresenter mHomePresenter;

    @Override
    protected void onPostCreate (@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initializeNavigation();
        initializeTabs();
    }

    private void initializeNavigation () {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, homeDrawer, toolbar, 0, 0);
        homeDrawer.addDrawerListener(mDrawerToggle);
        final View headerView = homeNavigation.inflateHeaderView(R.layout.layout_home_navigation_header);
        headerImage = headerView.findViewById(R.id.homeNavigationHeaderImage);
        headerTitle = headerView.findViewById(R.id.homeNavigationHeaderTitle);
        mDrawerToggle.syncState();
    }

    private void initializeTabs() {
        for (int i = 0; i < fragments.length; ++i) {
            fragments[i] = new Fragment();
        }
        mPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager())
        {
            @NonNull
            @Override
            public Fragment getItem (final int position)
            {
                return fragments[position];
            }

            @Override
            public int getCount ()
            {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle (final int position)
            {
                return "User world " + String.valueOf(position);
            }
        };
        homeViewPager.setAdapter(mPagerAdapter);
        homeTabs.setupWithViewPager(homeViewPager, true);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull final MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home:
                homeDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @AfterViews
    protected void onViewLoaded() {
        mHomePresenter = new HomePresenter();
        mHomePresenter.attach(this);
    }
}
