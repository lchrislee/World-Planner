package com.lchrislee.worldplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.fragments.PlannerObjectListFragment;
import com.lchrislee.worldplanner.models.Relationship;

/**
 * Created by chrisl on 3/26/17.
 */

public class MasterFragmentPagerAdapter extends FragmentPagerAdapter {
    private final PlannerObjectListFragment fragments[];
    private final String[] titles;

    public MasterFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new PlannerObjectListFragment[Relationship.RELATIONABLE_TYPE_COUNT];
        fragments[0] = PlannerObjectListFragment.newInstance(Relationship.RelationableType.Character);
        fragments[1] = PlannerObjectListFragment.newInstance(Relationship.RelationableType.Location);
        fragments[2] = PlannerObjectListFragment.newInstance(Relationship.RelationableType.Item);
        fragments[3] = PlannerObjectListFragment.newInstance(Relationship.RelationableType.Plot);

        titles = new String[Relationship.RELATIONABLE_TYPE_COUNT];
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
        return Relationship.RELATIONABLE_TYPE_COUNT;
    }
}
