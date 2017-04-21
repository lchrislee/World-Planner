package com.lchrislee.worldplanner.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

/**
 * Created by chrisl on 4/20/17.
 */

public class CharacterViewHolder extends ImageEntityViewHolder
{
    public final TextView trueName;
    public final TextView gender_age;
    public CharacterViewHolder(View itemView) {
        super(itemView);
        trueName = (TextView) itemView.findViewById(R.id.list_character_true_name);
        gender_age = (TextView) itemView.findViewById(R.id.list_character_age_gender);
    }
}