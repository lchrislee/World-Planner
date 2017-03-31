package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryItem extends SugarRecord implements Serializable, StoryElement{

    private StoryWorld world;
    private String name;
    private String description;

    public StoryItem() {
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }
}
