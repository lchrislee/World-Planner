package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

public class PlotViewHolder extends WorldPlannerBaseViewHolder
{
    public final TextView location;
    public PlotViewHolder(View itemView) {
        super(itemView);
        location = (TextView) itemView.findViewById(R.id.list_plot_location);
    }
}
