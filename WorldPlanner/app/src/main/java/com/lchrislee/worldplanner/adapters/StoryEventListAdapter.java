package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.viewholders.EventViewHolder;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryEvent;
import com.lchrislee.worldplanner.models.StoryLocation;

public class StoryEventListAdapter extends WorldPlannerBaseListAdapter<EventViewHolder>
{
    private final View.OnClickListener trueListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EntityDetailActivity.class);
            int index = (int) v.getTag();
            i.putExtra(EntityDetailActivity.INDEX, (long) index);
            i.putExtra(EntityDetailActivity.TYPE, DataManager.EVENT);
            context.startActivity(i);
        }
    };

    public StoryEventListAdapter(@NonNull Context context) {
        super(context, R.layout.list_event);
        setViewClickListener(trueListener);
    }

    @Override
    protected void additionalBindViewHolder(EventViewHolder holder, @NonNull StoryElement element) {
        StoryEvent event = (StoryEvent) element;
        StoryLocation location = event.getLocation();
        if (location == null)
        {
            holder.location.setVisibility(View.GONE);
        }
        else
        {
            String locationName = location.getName();
            String text = "Occurred at " + (locationName.length() == 0 ? " unnamed location" : locationName);
            holder.location.setText(text);
            holder.location.setVisibility(View.VISIBLE);
        }

        StoryEvent.StoryEventType type = event.getType();

        int color = type != null && type.getColor() != 0 ? type.getColor() : Color.WHITE;
        holder.itemView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getCountForEvents();
    }

    @Nullable
    @Override
    protected StoryElement obtainElement(int position)
    {
        return DataManager.getInstance().getEventAtIndex(position);
    }

    @NonNull
    @Override
    protected EventViewHolder generateViewHolder(View v) {
        return new EventViewHolder(v);
    }
}
