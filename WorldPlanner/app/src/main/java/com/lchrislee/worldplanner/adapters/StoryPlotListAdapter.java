package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.viewholders.WorldPlannerBaseViewHolder;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;

/**
 * Created by chrisl on 4/20/17.
 */

public class StoryPlotListAdapter extends RecyclerView.Adapter<WorldPlannerBaseViewHolder>
{
    private final Context context;
    private boolean isDetailable;

    public StoryPlotListAdapter(@NonNull Context context) {
        this.context = context;
        isDetailable = true;
    }

    @Override
    public WorldPlannerBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_plot, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDetailable) {
                    Intent i = new Intent(context, EntityDetailActivity.class);
                    i.putExtra(EntityDetailActivity.INDEX, (long) v.getTag());
                    i.putExtra(EntityDetailActivity.TYPE, DataManager.PLOT);
                    context.startActivity(i);
                }
            }
        });
        return(new WorldPlannerBaseViewHolder(v));
    }

    @Override
    public void onBindViewHolder(WorldPlannerBaseViewHolder holder, int position) {
        StoryElement obj = DataManager.getInstance().getPlotAtIndex(position);
        if (obj == null)
        {
            return;
        }
        holder.itemView.setTag((long) position);
        holder.name.setText(obj.getName());
        holder.description.setText(obj.getDescription());
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getCountForPlots();
    }

    public void setDetailable(boolean newDetail)
    {
        isDetailable = newDetail;
    }
}
