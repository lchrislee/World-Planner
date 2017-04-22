package com.lchrislee.worldplanner.fragments.current_world;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.WorldDetailFragment;

public class CurrentWorldFragment extends WorldPlannerBaseFragment {

    public interface WorldTabChange
    {
        void updateToolbarWorldTabChange(boolean editable);
    }

    private WorldDetailFragment worldFragment;
    private CurrentWorldTabFragment tabFragment;
    private CurrentWorldSettingsFragment settingsFragment;
    private WorldTabChange tabChangeListener;

    public CurrentWorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_world_current, container, false);
        setupBottomNav(v);

        worldFragment = WorldDetailFragment.newInstance(getContext());
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_world_current_frame, worldFragment)
                .commit();
        tabChangeListener.updateToolbarWorldTabChange(true);

        return v;
    }

    public void setTabChangeListener(WorldTabChange tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
    }

    public void iconAction()
    {
        worldFragment.editAction();
    }

    private void setupBottomNav(@NonNull View v)
    {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.fragment_world_current_bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                WorldPlannerBaseFragment frag = null;
                switch(item.getItemId())
                {
                    case R.id.menu_bottom_world_details:
                        if (worldFragment == null)
                        {
                            worldFragment = WorldDetailFragment.newInstance(getContext());
                        }
                        frag = worldFragment;
                        tabChangeListener.updateToolbarWorldTabChange(true);
                        worldFragment.stopEditing();
                        break;
                    case R.id.menu_bottom_world_elements:
                        if (tabFragment == null)
                        {
                            tabFragment = new CurrentWorldTabFragment();
                        }
                        frag = tabFragment;
                        tabChangeListener.updateToolbarWorldTabChange(false);
                        if (worldFragment != null)
                        {
                            worldFragment.stopEditing();
                        }
                        break;
                    case R.id.menu_bottom_world_settings:
                        if (settingsFragment == null)
                        {
                            settingsFragment = new CurrentWorldSettingsFragment();
                        }
                        frag = settingsFragment;
                        tabChangeListener.updateToolbarWorldTabChange(false);
                        if (worldFragment != null)
                        {
                            worldFragment.stopEditing();
                        }
                        break;
                }

                if (frag != null) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_world_current_frame, frag)
                            .addToBackStack(frag.getClass().getSimpleName())
                            .commit();
                }
                return true;
            }
        });
    }
}
