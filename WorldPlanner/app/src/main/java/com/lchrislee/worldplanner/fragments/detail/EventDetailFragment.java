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
import com.lchrislee.worldplanner.models.StoryEvent;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

public class EventDetailFragment extends DetailFragment {

    private StoryEvent event;
    private TextView locationText;

    public EventDetailFragment() {

    }

    public static @NonNull
    EventDetailFragment newInstance(@Nullable Serializable obj)
    {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.EVENT);
        args.putSerializable(DetailFragment.DATA, obj);
        args.putSerializable(LAYOUT, R.layout.fragment_detail_event);
        args.putString(TITLE, "Event");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryEvent item = new StoryEvent();
            item.setWorld(currentWorld);
            model = item;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            locationText = (TextView) mainView.findViewById(R.id.fragment_detail_event_location);
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            event = (StoryEvent) model;
            if (locationText != null)
            {
                StoryLocation location = event.getLocation();
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
