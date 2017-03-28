package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.CharacterDetailPagerAdapter;
import com.lchrislee.worldplanner.adapters.CharacterRelationListAdapter;

public class CharacterDetailFragment extends WorldPlannerBaseFragment implements EditableFragment {

    private CharacterDetailPagerAdapter adapter;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_character_master, container, false);

        adapter = new CharacterDetailPagerAdapter(getChildFragmentManager());

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.fragment_detail_character_master_viewpager);
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.fragment_detail_character_master_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    @Override
    public boolean isEditing() {
        return ((EditableFragment)adapter.getItem(0)).isEditing();
    }

    @Override
    public void iconAction() {
        ((EditableFragment)adapter.getItem(0)).iconAction();
    }
}
