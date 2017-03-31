package com.lchrislee.worldplanner.fragments;


import android.support.v4.app.Fragment;

import timber.log.Timber;

public class WorldPlannerBaseFragment extends Fragment {


    public WorldPlannerBaseFragment() {
        // Required empty public constructor
        Timber.tag(getClass().getSimpleName()).d("Base Fragment created");
    }

}
