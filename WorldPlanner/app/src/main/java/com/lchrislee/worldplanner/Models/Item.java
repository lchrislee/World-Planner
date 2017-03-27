package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Item extends WorldPlannerBaseModel implements Relationship.Relationable {
    public Item(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    @NonNull
    @Override
    public Relationship.RelationableType getRelationableType() {
        return Relationship.RelationableType.Item;
    }

    @NonNull
    @Override
    public String getRelationableString() {
        return "Item";
    }
}
