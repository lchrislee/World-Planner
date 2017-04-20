package com.lchrislee.worldplanner.fragments.detail;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.managers.DataManager;

public class WorldDetailFragment extends DetailFragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            swapEdit();
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            // TODO update more.
        }
        super.onResume();
    }

    @Override
    protected void swapEdit() {
        // TODO: If some custom feature is not null: do not try to edit their appearance
        super.swapEdit();
    }

    @Override
    public long editAction() {
        return super.editAction();
    }
}