package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;

import com.lchrislee.worldplanner.R;

/**
 * Created by chrisl on 4/20/17.
 */

public class ImageEntityViewHolder extends DefaultEntityViewHolder // Location
{
    public final ImageView image;
    public ImageEntityViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.list_entity_image);
    }
}
