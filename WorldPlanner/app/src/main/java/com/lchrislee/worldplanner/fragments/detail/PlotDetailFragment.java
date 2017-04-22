package com.lchrislee.worldplanner.fragments.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

public class PlotDetailFragment extends DetailFragment {

    private StoryPlot plot;
    private TextView locationText;

    public PlotDetailFragment() {

    }

    public static @NonNull PlotDetailFragment newInstance(@Nullable Serializable obj)
    {
        PlotDetailFragment fragment = new PlotDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.PLOT);
        args.putSerializable(DetailFragment.DATA, obj);
        args.putSerializable(LAYOUT, R.layout.fragment_detail_plot);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryPlot item = new StoryPlot();
            item.setWorld(currentWorld);
            model = item;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            locationText = (TextView) mainView.findViewById(R.id.fragment_detail_plot_location);
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            plot = (StoryPlot) model;
            if (locationText != null)
            {
                StoryLocation location = plot.getLocation();
                String locationOutput = "Occurred at ";
                if (location == null)
                {
                    locationOutput += "undisclosed location";
                    locationText.setText(locationOutput);
                }
                else
                {
                    locationOutput += location.getName();
                    locationText.setText(locationOutput);
                }
            }
        }
        updateViews();
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            if (locationText != null) {
                locationText.setVisibility(View.GONE);
            }
        }
        else
        {
            if (locationText != null) {
                locationText.setVisibility(View.VISIBLE);
            }
        }
        super.updateViews();
    }

}
