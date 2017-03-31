package com.lchrislee.worldplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.fragments.EntityListFragment;
import com.lchrislee.worldplanner.managers.DataManager;

/**
 * Created by chrisl on 3/26/17.
 */

public class CurrentWorldEntityPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 4;

    private final EntityListFragment fragments[];
    private final String[] titles;

    public CurrentWorldEntityPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new EntityListFragment[TAB_COUNT];
        fragments[0] = EntityListFragment.newInstance(DataManager.CODE_CHARACTER);
        fragments[1] = EntityListFragment.newInstance(DataManager.CODE_LOCATION);
        fragments[2] = EntityListFragment.newInstance(DataManager.CODE_ITEM);
        fragments[3] = EntityListFragment.newInstance(DataManager.CODE_PLOT);

        titles = new String[TAB_COUNT];
        titles[0] = "Characters";
        titles[1] = "Locations";
        titles[2] = "Items";
        titles[3] = "Plot Points";
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}
