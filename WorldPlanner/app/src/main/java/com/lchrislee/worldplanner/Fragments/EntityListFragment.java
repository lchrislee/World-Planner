package com.lchrislee.worldplanner.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;

public class EntityListFragment extends WorldPlannerBaseFragment {


    public EntityListFragment() {
        super();
        // Required empty public constructor
    }

    public static EntityListFragment newInstance() {
        EntityListFragment fragment = new EntityListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entity_list, container, false);
    }

}
