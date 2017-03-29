package com.lchrislee.worldplanner.fragments.CurrentWorld;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.fragments.DetailFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.ImportanceRelation;

import timber.log.Timber;

public class WorldDetailFragment extends DetailFragment {

    public WorldDetailFragment() {
        // Required empty public constructor
    }

    public static WorldDetailFragment newInstance() {
        WorldDetailFragment fragment = new WorldDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.RELATION_TYPE, ImportanceRelation.ImportantType.None);
        args.putBoolean(DetailFragment.EDIT, false);
        args.putInt(DetailFragment.INDEX, DataManager.getInstance().getCurrentWorldIndex()); // TODO: This may need revising.
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
    protected void swapEdit() {
        // TODO: If some custom feature is not null: do not try to edit their appearance
        super.swapEdit();
    }

    @Override
    public void editAction() {
        Timber.tag(getClass().getSimpleName()).d("editAction");
        super.editAction();
    }
}