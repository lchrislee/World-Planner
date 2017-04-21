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
        args.putSerializable(DetailFragment.RELATION_TYPE, DataManager.WORLD);
        args.putSerializable(DetailFragment.DATA, DataManager.getInstance(context).getCurrentWorld());
        fragment.setArguments(args);
        return fragment;
    }

    public static WorldDetailFragment newInstance() {
        WorldDetailFragment fragment = new WorldDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.RELATION_TYPE, DataManager.WORLD);
        args.putSerializable(DetailFragment.DATA, null);
        fragment.setArguments(args);
        return fragment;
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

            swapEdit();
        }

        return mainView;
    }

    @Override
    public void onResume() {
        Timber.d("onResume start");
        if (model != null)
        {
            if (adapter != null)
            {
                adapter.notifyDataSetChanged();
            }

            if (list != null)
            {
                list.scrollToPosition(DataManager.getInstance().getCountForPlots() - 1);
            }
        }
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DataManager.PLOT)
        {
            isEditing = true;
        }
    }

    @Override
    protected void swapEdit() {
        if (isEditing)
        {
            if (list != null)
            {
                if (isNew)
                {
                    addPlot.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    listPrompt.setVisibility(View.GONE);
                }
                else
                {
                    addPlot.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                    listPrompt.setVisibility(View.VISIBLE);
                }
            }
        }
        else
        {
            if (addPlot != null) {
                addPlot.setVisibility(View.GONE);
            }
        }

        if (adapter != null) {
            adapter.setDetailable(!isEditing);
        }
        super.swapEdit();
    }

    @Override
    public long editAction() {
        return super.editAction();
    }
}
