package com.lchrislee.worldplanner.fragments.CurrentWorld;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.CurrentWorldEntityPagerAdapter;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;

import timber.log.Timber;

public class CurrentWorldTabFragment extends WorldPlannerBaseFragment {
    private FloatingActionMenu floatingActionMenu;
    private CurrentWorldEntityPagerAdapter adapter;

    public CurrentWorldTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CurrentWorldEntityPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_world_current_tab, container, false);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.fragment_master_viewpager);
        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.fragment_master_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        floatingActionMenu = (FloatingActionMenu) v.findViewById(R.id.fragment_master_fab_menu);

        // TODO: Replace this implementaion of FAB menu with custom one.
        // This is super deprecated and hard to use.
        View.OnClickListener toDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.tag("Main FAB").d(((FloatingActionButton) v).getLabelText());

                int requestCode;
                switch(v.getId())
                {
                    case R.id.fragment_master_fab_character:
                        requestCode = EntityDetailActivity.REQUEST_CODE_CHARACTER;
                        break;
                    case R.id.fragment_master_fab_location:
                        requestCode = EntityDetailActivity.REQUEST_CODE_LOCATION;
                        break;
                    case R.id.fragment_master_fab_item:
                        requestCode = EntityDetailActivity.REQUEST_CODE_ITEM;
                        break;
                    case R.id.fragment_master_fab_plot:
                        requestCode = EntityDetailActivity.REQUEST_CODE_PLOT;
                        break;
                    default:
                        requestCode = EntityDetailActivity.REQUEST_CODE_WORLD;
                }
                floatingActionMenu.close(true);

                Intent i = new Intent(getContext(), EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, requestCode);
                startActivityForResult(i, requestCode);
            }
        };

        final FloatingActionButton character = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_character);
        character.setOnClickListener(toDetail);
        final FloatingActionButton location = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_location);
        location.setOnClickListener(toDetail);
        final FloatingActionButton item = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_item);
        item.setOnClickListener(toDetail);
        final FloatingActionButton plot = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_plot);
        plot.setOnClickListener(toDetail);

        return v;
    }

}
