package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

public class EventViewHolder extends WorldPlannerBaseViewHolder
{
    public final TextView location;
    public EventViewHolder(View itemView) {
        super(itemView);
        location = (TextView) itemView.findViewById(R.id.list_event_location);
    }
}
