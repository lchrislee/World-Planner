package com.lchrislee.worldplanner.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.WorldListAdapter;
import com.lchrislee.worldplanner.managers.DataManager;

public class ChangeWorldFragment extends WorldPlannerBaseFragment implements WorldListAdapter.WorldSwitch {

    public interface FragmentSwap
    {
        void onWorldSwitch();
    }

    private FragmentSwap listener;
    private WorldListAdapter adapter;

    public ChangeWorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_world, container, false);

        adapter = new WorldListAdapter(getContext(), this);
        final RecyclerView list = (RecyclerView) v.findViewById(R.id.fragment_world_list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        final FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.fragment_world_list_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, DataManager.WORLD);
                getActivity().startActivityForResult(i, DataManager.WORLD);
            }
        });

        return v;
    }

    public void setListener(FragmentSwap listener) {
        this.listener = listener;
    }

    @Override
    public void onWorldSwitch() {
        listener.onWorldSwitch();
    }
}
