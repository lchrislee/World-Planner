package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

public class ItemViewHolder extends DefaultEntityViewHolder
{
    public final TextView effectsCount;
    public ItemViewHolder(View itemView) {
        super(itemView);
        effectsCount = (TextView) itemView.findViewById(R.id.list_item_effect_count);
    }
}
