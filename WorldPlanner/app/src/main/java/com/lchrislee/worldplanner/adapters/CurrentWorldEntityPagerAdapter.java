package com.lchrislee.worldplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.fragments.CurrentWorldEntityListFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;

/**
 * Created by chrisl on 3/26/17.
 */

public class CurrentWorldEntityPagerAdapter extends FragmentPagerAdapter {
    private final CurrentWorldEntityListFragment fragments[];
    private final String[] titles;

    public CurrentWorldEntityPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new CurrentWorldEntityListFragment[ImportanceRelation.IMPORTANT_TYPE_COUNT];
        fragments[0] = CurrentWorldEntityListFragment.newInstance(ImportanceRelation.ImportantType.Character);
        fragments[1] = CurrentWorldEntityListFragment.newInstance(ImportanceRelation.ImportantType.Location);
        fragments[2] = CurrentWorldEntityListFragment.newInstance(ImportanceRelation.ImportantType.Item);
        fragments[3] = CurrentWorldEntityListFragment.newInstance(ImportanceRelation.ImportantType.Plot);

        titles = new String[ImportanceRelation.IMPORTANT_TYPE_COUNT];
        titles[0] = "Characters";
        titles[1] = "Locations";
        titles[2] = "Objects";
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
        return ImportanceRelation.IMPORTANT_TYPE_COUNT;
    }
}
