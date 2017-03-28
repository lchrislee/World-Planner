package com.lchrislee.worldplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.fragments.CharacterRelationListFragment;
import com.lchrislee.worldplanner.fragments.PlannerObjectDetailFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;

/**
 * Created by chrisl on 3/27/17.
 */

public class CharacterDetailPagerAdapter extends FragmentPagerAdapter {
    private static final int CHARACTER_TABS = 2;

    private WorldPlannerBaseFragment fragments[];
    private String titles[];

    public CharacterDetailPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new WorldPlannerBaseFragment[CHARACTER_TABS];
        fragments[0] = PlannerObjectDetailFragment.newInstance(ImportanceRelation.ImportantType.Character, false);
        fragments[1] = new CharacterRelationListFragment();

        titles = new String[CHARACTER_TABS];
        titles[0] = "Details";
        titles[1] = "Relationships";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return CHARACTER_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
