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
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.StoryPlotListAdapter;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryWorld;

import timber.log.Timber;

public class WorldDetailFragment extends DetailFragment {

    private StoryPlotListAdapter adapter;
    private TextView listPrompt;
    private ImageView addPlot;
    private RecyclerView list;

    public WorldDetailFragment() {
        // Required empty public constructor
    }

    public static WorldDetailFragment newInstance(@NonNull Context context) {
        WorldDetailFragment fragment = new WorldDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.WORLD);
        args.putSerializable(DetailFragment.DATA, DataManager.getInstance(context).getCurrentWorld());
        fragment.setArguments(args);
        return fragment;
    }

    public static WorldDetailFragment newInstance() {
        WorldDetailFragment fragment = new WorldDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.WORLD);
        args.putSerializable(DetailFragment.DATA, null);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            model = new StoryWorld();
            model.setName("");
            model.setDescription("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            adapter = new StoryPlotListAdapter(getContext());
            list = (RecyclerView) mainView.findViewById(R.id.fragment_detail_world_plots);
            list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
            list.setAdapter(adapter);

            listPrompt = (TextView) mainView.findViewById(R.id.fragment_detail_world_plot_prompt);

            addPlot = (ImageView) mainView.findViewById(R.id.fragment_detail_world_plot_add);
            addPlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), EntityDetailActivity.class);
                    i.putExtra(EntityDetailActivity.TYPE, DataManager.PLOT);
                    startActivityForResult(i, DataManager.PLOT);
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
            if (adapter != null)
            {
                adapter.notifyDataSetChanged();
                list.scrollToPosition(adapter.getItemCount() - 1);
            }
        }
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            if (list != null)
            {
                if (isNew)
                {
                    list.setVisibility(View.GONE);
                    listPrompt.setVisibility(View.GONE);
                }
                else
                {
                    list.setVisibility(View.VISIBLE);
                    listPrompt.setVisibility(View.VISIBLE);
                }
                addPlot.setVisibility(View.GONE);
            }
        }
        else
        {
            if (addPlot != null) {
                addPlot.setVisibility(View.VISIBLE);
            }
        }

        if (adapter != null) {
            adapter.setDetailable(!isEditing);
        }
        super.updateViews();
    }

}
