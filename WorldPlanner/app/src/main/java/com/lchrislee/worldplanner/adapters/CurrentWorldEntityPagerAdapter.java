package com.lchrislee.worldplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.fragments.EntityListFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;

/**
 * Created by chrisl on 3/26/17.
 */

public class CurrentWorldEntityPagerAdapter extends FragmentPagerAdapter {
    private final EntityListFragment fragments[];
    private final String[] titles;

    public CurrentWorldEntityPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new EntityListFragment[ImportanceRelation.IMPORTANT_TYPE_COUNT];
        fragments[0] = EntityListFragment.newInstance(ImportanceRelation.ImportantType.Character);
        fragments[1] = EntityListFragment.newInstance(ImportanceRelation.ImportantType.Location);
        fragments[2] = EntityListFragment.newInstance(ImportanceRelation.ImportantType.Item);
        fragments[3] = EntityListFragment.newInstance(ImportanceRelation.ImportantType.Plot);

        titles = new String[ImportanceRelation.IMPORTANT_TYPE_COUNT];
        titles[0] = "Characters";
        titles[1] = "Locations";
        titles[2] = "Objects";
        titles[3] = "StoryPlot Points";
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
