package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.views.SimpleDetailView;

/**
 * Created by chrisl on 4/20/17.
 */

public class ItemViewHolder extends WorldPlannerBaseViewHolder
{
    public final SimpleDetailView details;
    public ItemViewHolder(View itemView) {
        super(itemView);
        details = (SimpleDetailView) itemView.findViewById(R.id.list_item_simple);
    }
}
