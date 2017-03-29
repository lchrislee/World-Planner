package com.lchrislee.worldplanner.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.CurrentWorldEntityPagerAdapter;
import com.lchrislee.worldplanner.models.ImportanceRelation;

import timber.log.Timber;

public class CurrentWorldTabFragment extends WorldPlannerBaseFragment {

    public CurrentWorldTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_world_current_tab, container, false);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.fragment_master_viewpager);
        viewPager.setAdapter(new CurrentWorldEntityPagerAdapter(getChildFragmentManager()));

        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.fragment_master_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        // TODO: Replace this implementaion of FAB menu with custom one.
        // This is super deprecated and hard to use.
        View.OnClickListener toDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.tag("Main FAB").d(((FloatingActionButton) v).getLabelText());
                Intent i = new Intent(getContext(), EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.NEW, true);
                int requestCode;
                switch(v.getId())
                {
                    case R.id.fragment_master_fab_character:
                        i.putExtra(EntityDetailActivity.TYPE, ImportanceRelation.ImportantType.Character);
                        requestCode = EntityDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    case R.id.fragment_master_fab_location:
                        i.putExtra(EntityDetailActivity.TYPE, ImportanceRelation.ImportantType.Location);
                        requestCode = EntityDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    case R.id.fragment_master_fab_item:
                        i.putExtra(EntityDetailActivity.TYPE, ImportanceRelation.ImportantType.Item);
                        requestCode = EntityDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    case R.id.fragment_master_fab_plot:
                        i.putExtra(EntityDetailActivity.TYPE, ImportanceRelation.ImportantType.Plot);
                        requestCode = EntityDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    default:
                        i.putExtra(EntityDetailActivity.TYPE, ImportanceRelation.ImportantType.None);
                        requestCode = EntityDetailActivity.REQUEST_CODE_WORLD_DETAIL;
                }
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
