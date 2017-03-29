package com.lchrislee.worldplanner.fragments.CurrentWorld;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;

public class CurrentWorldFragment extends WorldPlannerBaseFragment {

    public interface WorldTabChange
    {
        void onWorldTabChanged();
    }

    private CurrentWorldTabFragment tabFragment;
    private WorldDetailFragment worldFragment;

    private WorldTabChange tabChangeListener;

    private boolean isShowingWorld;

    public CurrentWorldFragment() {
        // Required empty public constructor
        isShowingWorld = true;
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
                        isShowingWorld = false;
                        break;
                    case R.id.menu_navigation_world_current_world:
                        if (worldFragment == null)
                        {
                            worldFragment = WorldDetailFragment.newInstance();
                        }
                        frag = worldFragment;
                        isShowingWorld = true;
                        break;
                }

                if (frag != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_world_current_frame, frag).commit();
                }

                if (tabChangeListener != null)
                {
                    tabChangeListener.onWorldTabChanged();
                }
                return true;
            }
        });

        worldFragment = WorldDetailFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_world_current_frame, worldFragment).commit();

        return v;
    }

    public boolean isShowingWorld() {
        return isShowingWorld;
    }

    public void setTabChangeListener(WorldTabChange tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
    }

    public void iconAction()
    {
        worldFragment.editAction();
    }
}
