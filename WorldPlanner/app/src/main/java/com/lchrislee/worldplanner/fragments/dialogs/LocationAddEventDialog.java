package com.lchrislee.worldplanner.fragments.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryEvent;
import com.lchrislee.worldplanner.models.StoryLocation;

import java.io.Serializable;
import java.util.List;

public class LocationAddEventDialog extends MultipleSelectDialog {

    private StoryLocation master;

    public LocationAddEventDialog() {
        // Required.
    }

    public static @NonNull
    LocationAddEventDialog newInstance(@NonNull Serializable obj)
    {
        LocationAddEventDialog fragment = new LocationAddEventDialog();
        Bundle argument = new Bundle();
        argument.putSerializable(MASTER, obj);
        argument.putSerializable(TITLE, "Add plot events to this location.");
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        master = (StoryLocation) seekingElement;
    }

    @NonNull
    @Override
    protected MultipleSelectListAdapter getAdapter() {
        return new AddEventListAdapter(getContext());
    }

    private class AddEventListAdapter extends MultipleSelectListAdapter
    {
        private final List<StoryEvent> events;
        AddEventListAdapter(@NonNull Context context) {
            super(context);
            events = DataManager.getInstance().getAllEventsNotInLocation(master);
        }

        @Override
        protected void additionalBinding(MultipleSelectListAdapter.MultipleSelectViewHolder holder, int position) {
            StoryEvent.StoryEventType type = events.get(position).getType();
            if (type != null && type.getColor() != 0)
            {
                holder.itemView.setBackgroundColor(type.getColor());
            }
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        @Override
        protected long getIdAtIndex(int position)
        {
            return events.get(position).getId();
        }

        @NonNull
        @Override
        protected StoryElement getElementAtIndex(int position) {
            return events.get(position);
        }
    }
}
