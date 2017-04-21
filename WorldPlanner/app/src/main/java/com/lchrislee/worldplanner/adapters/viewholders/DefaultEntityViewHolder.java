package com.lchrislee.worldplanner.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.views.SimpleDetailView;

/**
 * Created by chrisl on 4/20/17.
 */

public class DefaultEntityViewHolder extends RecyclerView.ViewHolder {

    public final TextView name;
    public final TextView description;


    public DefaultEntityViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.list_entity_name);
        description = (TextView) itemView.findViewById(R.id.list_entity_description);
    }
}
