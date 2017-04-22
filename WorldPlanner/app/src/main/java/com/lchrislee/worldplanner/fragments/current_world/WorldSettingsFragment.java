package com.lchrislee.worldplanner.fragments.current_world;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.dialogs.EventTypeDialogFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryEvent;

public class WorldSettingsFragment
        extends WorldPlannerBaseFragment
        implements EventTypeDialogFragment.EventTypeDialogListener
{

    private EventTypeListAdapter eventTypeListAdapter;

    public WorldSettingsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_world_current_settings, container, false);

        eventTypeListAdapter = new EventTypeListAdapter(getContext());
        final RecyclerView eventTypeList = (RecyclerView) v.findViewById(R.id.fragment_settings_event_type_list);
        eventTypeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        eventTypeList.setAdapter(eventTypeListAdapter);

        final ImageView addEventType = (ImageView) v.findViewById(R.id.fragment_settings_event_type_add);
        addEventType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventTypeDialogFragment fragment = EventTypeDialogFragment.newInstance(-1);
                fragment.setListener(WorldSettingsFragment.this);
                fragment.show(getChildFragmentManager(), "Add EventType");
            }
        });
        return v;
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
                EventTypeDialogFragment fragment = EventTypeDialogFragment.newInstance((int) v.getTag());
                fragment.setListener(WorldSettingsFragment.this);
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
