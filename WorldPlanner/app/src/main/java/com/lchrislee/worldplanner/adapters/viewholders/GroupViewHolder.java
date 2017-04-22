package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

public class GroupViewHolder extends DefaultEntityViewHolder {
    public final TextView size;

    public GroupViewHolder(View itemView) {
        super(itemView);
        size = (TextView) itemView.findViewById(R.id.list_group_size);
    }
}
