package com.lchrislee.worldplanner.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

public class WorldPlannerBaseViewHolder extends RecyclerView.ViewHolder {

    public final TextView name;
    public final TextView description;

    WorldPlannerBaseViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.list_entity_name);
        description = (TextView) itemView.findViewById(R.id.list_entity_description);
    }
}
