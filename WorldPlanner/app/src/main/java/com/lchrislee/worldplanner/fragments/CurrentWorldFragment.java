package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.ImportanceRelation;

public class CurrentWorldFragment extends WorldPlannerBaseFragment {

    private CurrentWorldTabFragment tabFragment;
    private DetailFragment worldFragment;

    public CurrentWorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_world_current, container, false);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.fragment_world_current_bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                WorldPlannerBaseFragment frag = null;
                switch(item.getItemId())
                {
                    case R.id.menu_navigation_world_current_information:
                        if (tabFragment == null)
                        {
                            tabFragment = new CurrentWorldTabFragment();
                        }
                        frag = tabFragment;
                        break;
                    case R.id.menu_navigation_world_current_world:
                        if (worldFragment == null)
                        {
                            worldFragment = DetailFragment.newInstance(ImportanceRelation.ImportantType.None);
                        }
                        frag = worldFragment;
                        break;
                }

                if (frag != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_world_current_frame, frag).commit();
                }
                return true;
            }
        });

        tabFragment = new CurrentWorldTabFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_world_current_frame, tabFragment).commit();

        return v;
    }

}
