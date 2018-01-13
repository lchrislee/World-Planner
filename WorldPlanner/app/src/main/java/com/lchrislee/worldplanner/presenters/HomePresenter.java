package com.lchrislee.worldplanner.presenters;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HomePresenter
{
    private static final String LOG_TAG = HomePresenter.class.getSimpleName();

    private NavigationView homeNavigation;

    private DrawerLayout homeDrawer;

    private Toolbar toolbar;

    private ViewPager homeViewPager;

    private TabLayout homeTabs;

    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment[] fragments = new Fragment[10];

    public HomePresenter (@NonNull final NavigationView homeNavigation,
                          @NonNull final DrawerLayout homeDrawer,
                          @NonNull final Toolbar toolbar,
                          @NonNull final ViewPager homeViewPager,
                          @NonNull final TabLayout homeTabs)
    {
        this.homeNavigation = homeNavigation;
        this.homeDrawer = homeDrawer;
        this.toolbar = toolbar;
        this.homeViewPager = homeViewPager;
        this.homeTabs = homeTabs;
    }

    public void attach(@NonNull final AppCompatActivity activity) {
        mDrawerToggle = new ActionBarDrawerToggle(activity, homeDrawer, toolbar, 0, 0);
        homeDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        initializeTabs(activity);
    }

    private void initializeTabs(@NonNull final AppCompatActivity activity) {
        for (int i = 0; i < fragments.length; ++i) {
            fragments[i] = new Fragment();
        }
        homeViewPager.setAdapter(new FragmentStatePagerAdapter(activity.getSupportFragmentManager())
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
        });
        homeTabs.setupWithViewPager(homeViewPager, true);
    }

    public void onHomeSelected() {
        homeDrawer.openDrawer(GravityCompat.START);
    }
}
