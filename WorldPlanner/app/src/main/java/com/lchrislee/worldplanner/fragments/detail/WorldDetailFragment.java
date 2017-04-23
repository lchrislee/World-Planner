package com.lchrislee.worldplanner.fragments.detail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.StoryEventListAdapter;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryWorld;

public class WorldDetailFragment extends DetailFragment {

    private StoryEventListAdapter eventsAdapter;
    private TextView eventListPrompt;
    private RecyclerView eventsList;

    public WorldDetailFragment() {
        // Required empty public constructor
    }

    public static WorldDetailFragment newInstance(@NonNull Context context) {
        WorldDetailFragment fragment = new WorldDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.WORLD);
        args.putSerializable(DetailFragment.DATA, DataManager.getInstance(context).getCurrentWorld());
        args.putInt(LAYOUT, R.layout.fragment_detail_world);
        args.putString(TITLE, "World");
        fragment.setArguments(args);
        return fragment;
    }

    public static WorldDetailFragment newInstance() {
        WorldDetailFragment fragment = new WorldDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.WORLD);
        args.putSerializable(DetailFragment.DATA, null);
        args.putInt(LAYOUT, R.layout.fragment_detail_world);
        args.putString(TITLE, "World");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            model = new StoryWorld();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            eventsAdapter = new StoryEventListAdapter(getContext());
            eventsList = (RecyclerView) mainView.findViewById(R.id.fragment_detail_world_plots);
            eventsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            eventsList.setAdapter(eventsAdapter);

            eventListPrompt = (TextView) mainView.findViewById(R.id.fragment_detail_world_plot_prompt);
            eventListPrompt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), EntityDetailActivity.class);
                    i.putExtra(EntityDetailActivity.TYPE, DataManager.EVENT);
                    startActivityForResult(i, DataManager.EVENT);
                }
            });

            updateViews();
        }

        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            if (eventsAdapter != null)
            {
                eventsAdapter.notifyDataSetChanged();
                eventsList.scrollToPosition(0);
            }
        }
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            if (eventsList != null)
            {
                eventsList.setVisibility(View.GONE);
                eventListPrompt.setVisibility(View.GONE);
            }
        }
        else
        {
            if (eventListPrompt != null) {
                eventsList.setVisibility(View.VISIBLE);
                eventListPrompt.setVisibility(View.VISIBLE);
            }
        }

        if (eventsAdapter != null) {
            eventsAdapter.setDetailable(!isEditing);
        }
        super.updateViews();
    }

}
