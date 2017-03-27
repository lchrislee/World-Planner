package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Location extends WorldPlannerBaseModel implements Relationship.Relationable {
    public Location(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    @NonNull
    @Override
    public Relationship.RelationableType getRelationableType() {
        return Relationship.RelationableType.Location;
    }

    @NonNull
    @Override
    public String getRelationableString() {
        return "Location";
    }
}
