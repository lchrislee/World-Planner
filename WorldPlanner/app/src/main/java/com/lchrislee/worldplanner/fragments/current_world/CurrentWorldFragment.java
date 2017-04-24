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

    private WorldDetailFragment detailFragment;
    private WorldElementsFragment tabFragment;
    private WorldEventsListFragment settingsFragment;
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

        detailFragment = WorldDetailFragment.newInstance(getContext());
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_world_current_frame, detailFragment)
                .commit();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        tabChangeListener.updateToolbarWorldTabChange(detailFragment.isVisible());
    }

    public void setTabChangeListener(WorldTabChange tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
    }

    public void iconAction()
    {
        detailFragment.editAction();
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
                        if (detailFragment == null)
                        {
                            detailFragment = WorldDetailFragment.newInstance(getContext());
                        }
                        frag = detailFragment;
                        tabChangeListener.updateToolbarWorldTabChange(true);
                        detailFragment.stopEditing();
                        break;
                    case R.id.menu_bottom_world_elements:
                        if (tabFragment == null)
                        {
                            tabFragment = new WorldElementsFragment();
                        }
                        frag = tabFragment;
                        tabChangeListener.updateToolbarWorldTabChange(false);
                        if (detailFragment != null)
                        {
                            detailFragment.stopEditing();
                        }
                        break;
                    case R.id.menu_bottom_world_events:
                        if (settingsFragment == null)
                        {
                            settingsFragment = new WorldEventsListFragment();
                        }
                        frag = settingsFragment;
                        tabChangeListener.updateToolbarWorldTabChange(false);
                        if (detailFragment != null)
                        {
                            detailFragment.stopEditing();
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
