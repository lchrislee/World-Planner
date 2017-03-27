package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Plot extends WorldPlannerBaseModel implements Relationship.Relationable {
    public Plot(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    @NonNull
    @Override
    public Relationship.RelationableType getRelationableType() {
        return Relationship.RelationableType.Plot;
    }

    @NonNull
    @Override
    public String getRelationableString() {
        return "Plot";
    }
}
