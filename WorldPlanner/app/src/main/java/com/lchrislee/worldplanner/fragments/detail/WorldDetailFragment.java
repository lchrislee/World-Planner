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
            // Find views.
            updateViews();
        }

        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            // Update views.
        }
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            // Hide things while editing.
        }
        else
        {
            // Make things visible.
        }

        super.updateViews();
    }

}
