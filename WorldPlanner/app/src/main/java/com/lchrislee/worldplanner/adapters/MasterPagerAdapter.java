package com.lchrislee.worldplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lchrislee.worldplanner.fragments.PlannerObjectListFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;

/**
 * Created by chrisl on 3/26/17.
 */

public class MasterPagerAdapter extends FragmentPagerAdapter {
    private final PlannerObjectListFragment fragments[];
    private final String[] titles;

    public MasterPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new PlannerObjectListFragment[ImportanceRelation.IMPORTANT_TYPE_COUNT];
        fragments[0] = PlannerObjectListFragment.newInstance(ImportanceRelation.ImportantType.Character);
        fragments[1] = PlannerObjectListFragment.newInstance(ImportanceRelation.ImportantType.Location);
        fragments[2] = PlannerObjectListFragment.newInstance(ImportanceRelation.ImportantType.Item);
        fragments[3] = PlannerObjectListFragment.newInstance(ImportanceRelation.ImportantType.Plot);

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
