package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.viewholders.PlotViewHolder;
import com.lchrislee.worldplanner.adapters.viewholders.WorldPlannerBaseViewHolder;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryPlot;

import timber.log.Timber;

public class StoryPlotListAdapter extends WorldPlannerBaseListAdapter<PlotViewHolder>
{
    private boolean isDetailable;
    private View.OnClickListener trueListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isDetailable) {
                Intent i = new Intent(context, EntityDetailActivity.class);
                int index = (int) v.getTag();
                i.putExtra(EntityDetailActivity.INDEX, (long) index);
                i.putExtra(EntityDetailActivity.TYPE, DataManager.PLOT);
                context.startActivity(i);
            }
        }
    };

    public StoryPlotListAdapter(@NonNull Context context) {
        super(context, R.layout.list_plot);
        isDetailable = true;
        setViewClickListener(trueListener);
    }

    @Override
    protected void additionalBindViewHolder(PlotViewHolder holder, @NonNull StoryElement element) {
        StoryPlot plot = (StoryPlot) element;
        StoryElement location = plot.getLocation();
        if (location == null)
        {
            holder.location.setVisibility(View.GONE);
        }
        else
        {
            String text = "Occurred at " + location.getName();
            holder.location.setText(text);
            holder.location.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getCountForPlots();
    }

    public void setDetailable(boolean newDetail)
    {
        isDetailable = newDetail;
    }

    @Nullable
    @Override
    protected StoryElement obtainElement(int position)
    {
        return DataManager.getInstance().getPlotAtIndex(position);
    }

    @NonNull
    @Override
    protected PlotViewHolder generateViewHolder(View v) {
        return new PlotViewHolder(v);
    }
}
