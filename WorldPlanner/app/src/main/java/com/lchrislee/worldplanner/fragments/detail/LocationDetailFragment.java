package com.lchrislee.worldplanner.fragments.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.WorldPlannerBaseListAdapter;
import com.lchrislee.worldplanner.adapters.viewholders.EventViewHolder;
import com.lchrislee.worldplanner.fragments.dialogs.LocationAddEventDialog;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;
import java.util.List;

public class LocationDetailFragment
        extends DetailFragment
        implements LocationAddEventDialog.LocationAddEventListener
{

    private StoryLocation location;

    private TextView eventsListPrompt;
    private ImageView addEvent;
    private RecyclerView eventsList;
    private EventInLocationListAdapter eventsAdapter;

    public LocationDetailFragment() {
        // Required
    }

    public static @NonNull LocationDetailFragment newInstance(@Nullable Serializable obj)
    {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.LOCATION);
        args.putSerializable(DetailFragment.DATA, obj);
        args.putInt(LAYOUT, R.layout.fragment_detail_location);
        args.putString(TITLE, "Location");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryLocation location = new StoryLocation();
            location.setWorld(currentWorld);
            model = location;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            eventsAdapter = new EventInLocationListAdapter(getContext());
            eventsList = (RecyclerView) mainView.findViewById(R.id.fragment_detail_location_events);
            eventsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
            eventsList.setAdapter(eventsAdapter);

            eventsListPrompt = (TextView) mainView.findViewById(R.id.fragment_detail_location_event_prompt);
            addEvent = (ImageView) mainView.findViewById(R.id.fragment_detail_location_event_add);
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationAddEventDialog fragment = LocationAddEventDialog.newInstance(location);
                    fragment.setListener(LocationDetailFragment.this);
                    fragment.show(getChildFragmentManager(), "Location add event");
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
            location = (StoryLocation) model;
            if (eventsAdapter != null)
            {
                eventsAdapter.notifyDataSetChanged();
                eventsList.scrollToPosition(eventsAdapter.getItemCount() - 1);
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
                eventsListPrompt.setVisibility(View.GONE);
                addEvent.setVisibility(View.GONE);
            }
        }
        else
        {
            if (addEvent != null) {
                addEvent.setVisibility(View.VISIBLE);
                eventsList.setVisibility(View.VISIBLE);
                eventsListPrompt.setVisibility(View.VISIBLE);
            }
        }

        super.updateViews();
    }

    @Override
    public void onPositiveClick(@NonNull List<Long> events) {
        for (long l : events) {
            DataManager.getInstance().setEventLocation(l, location);
        }
        eventsAdapter.notifyDataSetChanged();
    }

    private class EventInLocationListAdapter extends WorldPlannerBaseListAdapter<EventViewHolder>
    {
        private int lastPressed;
        private final View.OnClickListener trueListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPressed = (int) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Remove event from this location?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.getInstance().removeEventFromLocation(lastPressed, location);
                        eventsAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        };

        EventInLocationListAdapter(@NonNull Context context) {
            super(context, R.layout.list_event);
            setViewClickListener(trueListener);
        }

        @Override
        public int getItemCount() {
            return location.getEventsCount();
        }

        @Nullable
        @Override
        protected StoryElement obtainElement(int position) {
            return location.getEventAtIndex(position);
        }

        @NonNull
        @Override
        protected EventViewHolder generateViewHolder(View v) {
            return new EventViewHolder(v);
        }

        @Override
        protected void additionalBindViewHolder(EventViewHolder holder, @NonNull StoryElement element) {
            holder.location.setVisibility(View.GONE);
        }
    }

}
