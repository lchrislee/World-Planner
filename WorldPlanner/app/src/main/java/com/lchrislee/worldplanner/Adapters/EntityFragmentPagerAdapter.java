package com.lchrislee.worldplanner.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.Fragments.EntityDetailFragment;
import com.lchrislee.worldplanner.Fragments.EntityListFragment;
import com.lchrislee.worldplanner.Models.Entity;

/**
 * Created by chrisl on 3/26/17.
 */

public class EntityFragmentPagerAdapter extends FragmentPagerAdapter {
    private EntityListFragment fragments[];
    private String titles[];

    public EntityFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new EntityListFragment[Entity.MAX_COUNT];
        for (int i = 0; i < Entity.MAX_COUNT; ++i)
        {
            fragments[i] = EntityListFragment.newInstance();
        }

        titles = new String[Entity.MAX_COUNT];
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
        return Entity.MAX_COUNT;
    }
}
