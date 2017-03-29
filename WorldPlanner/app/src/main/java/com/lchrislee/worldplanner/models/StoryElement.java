package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/29/17.
 */

public abstract class StoryElement extends WorldPlannerBaseModel {

    private StoryWorld world;

    StoryElement(@NonNull String name, @NonNull String description, StoryWorld world) {
        super(name, description);
    }

    @NonNull
    public StoryWorld getWorld() {
        return world;
    }
}
