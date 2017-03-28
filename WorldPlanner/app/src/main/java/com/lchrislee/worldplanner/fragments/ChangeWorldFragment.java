package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.WorldListAdapter;

public class ChangeWorldFragment extends WorldPlannerBaseFragment {

    public ChangeWorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_world, container, false);

        WorldListAdapter adapter = new WorldListAdapter(getContext());
        final RecyclerView list = (RecyclerView) v.findViewById(R.id.fragment_world_list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return v;
    }

}
