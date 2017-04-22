package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.adapters.viewholders.WorldPlannerBaseViewHolder;
import com.lchrislee.worldplanner.models.StoryElement;

/**
 * Created by chrisl on 4/21/17.
 */

public abstract class WorldPlannerBaseListAdapter extends RecyclerView.Adapter<WorldPlannerBaseViewHolder>
{
    protected final Context context;
    private View.OnClickListener viewClickListener;
    private int layout;

    public WorldPlannerBaseListAdapter(@NonNull Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    protected void setViewClickListener(@Nullable View.OnClickListener l)
    {
        viewClickListener = l;
    }

    @Nullable
    protected abstract StoryElement obtainElement(int position);

    @Override
    public WorldPlannerBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        v.setOnClickListener(viewClickListener);
        return new WorldPlannerBaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WorldPlannerBaseViewHolder holder, int position) {
        StoryElement obj = obtainElement(position);
        if (obj == null)
        {
            return;
        }
        holder.itemView.setTag(position);
        holder.name.setText(obj.getName());
        holder.description.setText(obj.getDescription());
    }
}
