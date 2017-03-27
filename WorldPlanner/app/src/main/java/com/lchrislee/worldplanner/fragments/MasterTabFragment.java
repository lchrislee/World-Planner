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
import com.lchrislee.worldplanner.activities.ModelDetailActivity;
import com.lchrislee.worldplanner.adapters.MasterFragmentPagerAdapter;
import com.lchrislee.worldplanner.models.Relationship;

import timber.log.Timber;

public class MasterTabFragment extends WorldPlannerBaseFragment {

    public MasterTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_tab, container, false);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.fragment_master_viewpager);
        viewPager.setAdapter(new MasterFragmentPagerAdapter(getChildFragmentManager()));

        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.fragment_master_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        // TODO: Replace this implementaion of FAB menu with custom one.
        // This is super deprecated and hard to use.
        View.OnClickListener toDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.tag("Main FAB").d(((FloatingActionButton) v).getLabelText());
                Intent i = new Intent(getContext(), ModelDetailActivity.class);
                i.putExtra(ModelDetailActivity.NEW, true);
                int requestCode;
                switch(v.getId())
                {
                    case R.id.fragment_master_fab_character:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Character);
                        requestCode = ModelDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    case R.id.fragment_master_fab_location:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Location);
                        requestCode = ModelDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    case R.id.fragment_master_fab_item:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Item);
                        requestCode = ModelDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    case R.id.fragment_master_fab_plot:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Plot);
                        requestCode = ModelDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL;
                        break;
                    default:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.None);
                        requestCode = ModelDetailActivity.REQUEST_CODE_WORLD_DETAIL;
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
