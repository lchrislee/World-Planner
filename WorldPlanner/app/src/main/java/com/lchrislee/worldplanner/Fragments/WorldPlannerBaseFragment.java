package com.lchrislee.worldplanner.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class WorldPlannerBaseFragment extends Fragment {


    public WorldPlannerBaseFragment() {
        // Required empty public constructor
        Timber.tag("Lifecycle");
        Timber.d("Base Fragment created");
    }

}
