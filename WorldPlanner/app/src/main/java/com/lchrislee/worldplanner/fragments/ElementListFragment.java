package com.lchrislee.worldplanner.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.WorldElementListAdapter;

public class ElementListFragment extends WorldPlannerBaseFragment {

    private static final String TYPE = "TYPE";

    private WorldElementListAdapter adapter;

    private ImageView empty;

    private int type;

    public ElementListFragment() {
        // Required empty public constructor
    }

    public static @NonNull
    ElementListFragment newInstance(int typeToDisplay)
    {
        ElementListFragment fragment = new ElementListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(TYPE, typeToDisplay);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_element_list, container, false);
        empty = (ImageView) v.findViewById(R.id.fragment_element_list_empty);

        adapter = new WorldElementListAdapter(getActivity(), type);

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.fragment_element_list_elements);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        setupFAB(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
            int size = adapter.getItemCount();
            if (size == 0)
            {
                empty.setVisibility(View.VISIBLE);
            }
            else
            {
                empty.setVisibility(View.GONE);
            }
        }
    }

    private void setupFAB(@NonNull View v)
    {
        // TODO: Replace this implementaion of FAB menu with custom one.
        // This is super deprecated and hard to use.
        View.OnClickListener toDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, type);
                startActivityForResult(i, type);
            }
        };

        final FloatingActionButton create = (FloatingActionButton) v.findViewById(R.id.fragment_element_list_create);
        create.setOnClickListener(toDetail);
    }
}
