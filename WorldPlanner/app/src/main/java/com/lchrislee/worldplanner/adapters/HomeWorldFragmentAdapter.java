package com.lchrislee.worldplanner.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lchrislee.worldplanner.fragments.HomeWorldFragment;
import com.lchrislee.worldplanner.models.World;

import java.util.List;

/**
 * The PagerAdapter that manages the Fragments which each represent a {@link World} on the
 * {@link com.lchrislee.worldplanner.activities.HomeActivity}.
 */
public class HomeWorldFragmentAdapter extends FragmentStatePagerAdapter
{
    private List<HomeWorldFragment> fragments;

    private List<World> worlds;

    public HomeWorldFragmentAdapter (@NonNull final FragmentManager fm,
                                     @NonNull final List<HomeWorldFragment> fragments,
                                     @NonNull final List<World> worlds)
    {
        super(fm);
        this.fragments = fragments;
        this.worlds = worlds;
    }

    @Override
    public Fragment getItem (final int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount ()
    {
        return fragments.size();
    }

    @NonNull
    @Override
    public CharSequence getPageTitle (final int position)
    {
        return worlds.get(position).name();
    }
}
