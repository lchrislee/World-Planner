package com.lchrislee.worldplanner.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntityListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntityListFragment extends WorldPlannerBaseFragment {

    public EntityListFragment() {
        // Required empty public constructor
    }

    public static EntityListFragment newInstance() {
        EntityListFragment fragment = new EntityListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entity_list, container, false);
    }

}
