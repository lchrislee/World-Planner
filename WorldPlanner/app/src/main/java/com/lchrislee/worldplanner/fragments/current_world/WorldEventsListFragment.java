package com.lchrislee.worldplanner.fragments.current_world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.StoryEventListAdapter;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.dialogs.CreateEventTypeDialog;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryEvent;

public class WorldEventsListFragment
        extends WorldPlannerBaseFragment
        implements CreateEventTypeDialog.EventTypeDialogListener
{

    private EventTypeListAdapter eventTypeListAdapter;

    private StoryEventListAdapter eventsAdapter;
    private RecyclerView eventsList;

    public WorldEventsListFragment() {
        // Required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_world_event_list, container, false);

        setupEventsList(v);
        setupTypesList(v);

        return v;
    }

    private void setupEventsList(@NonNull View v)
    {
        eventsAdapter = new StoryEventListAdapter(getContext());
        eventsList = (RecyclerView) v.findViewById(R.id.fragment_world_event_list_events);
        eventsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventsList.setAdapter(eventsAdapter);

        TextView eventListPrompt = (TextView) v.findViewById(R.id.fragment_world_event_list_event_prompt);
        eventListPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, DataManager.EVENT);
                startActivityForResult(i, DataManager.EVENT);
            }
        });
    }

    private void setupTypesList(@NonNull View v)
    {
        eventTypeListAdapter = new EventTypeListAdapter(getContext());
        final RecyclerView eventTypeList = (RecyclerView) v.findViewById(R.id.fragment_world_event_list_types);
        eventTypeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventTypeList.setAdapter(eventTypeListAdapter);

        final TextView addEventPrompt = (TextView) v.findViewById(R.id.fragment_world_event_list_type_prompt);
        addEventPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventTypeDialog fragment = CreateEventTypeDialog.newInstance(-1);
                fragment.setListener(WorldEventsListFragment.this);
                fragment.show(getChildFragmentManager(), "Add EventType");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (eventsAdapter != null)
        {
            eventsAdapter.notifyDataSetChanged();
            eventsList.scrollToPosition(0);
        }
    }

    @Override
    public void onUpdate() {
        eventTypeListAdapter.notifyDataSetChanged();
    }

    private class EventTypeListAdapter
            extends RecyclerView.Adapter<EventTypeListAdapter.EventTypeViewHolder>
    {
        class EventTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            final TextView name;
            final TextView description;
            EventTypeViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(android.R.id.text1);
                description = (TextView) itemView.findViewById(android.R.id.text2);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                CreateEventTypeDialog fragment = CreateEventTypeDialog.newInstance((int) v.getTag());
                fragment.setListener(WorldEventsListFragment.this);
                fragment.show(getChildFragmentManager(), "Update EventType");
            }
        }

        private Context context;

        EventTypeListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public EventTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            return new EventTypeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EventTypeViewHolder holder, int position) {
            holder.itemView.setTag(position);
            StoryEvent.StoryEventType type = DataManager.getInstance().getEventTypeAtIndex(position);
            if (type == null)
            {
                return;
            }
            holder.name.setText(type.getName());
            if (type.getColor() != 0) {
                holder.itemView.setBackgroundColor(type.getColor());
            }
            holder.description.setText(type.getDescription());
        }

        @Override
        public int getItemCount() {
            return DataManager.getInstance().getEventTypeCountForWorld();
        }
    }
}
