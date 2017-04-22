package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryPlot extends SugarRecord implements Serializable, StoryElement {

    private StoryWorld world;
    private String name;
    private String description;

    public StoryPlot() {
        name = "";
        description = "";
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

    @NonNull
    @Override
    public String getImage() {
        return "";
    }

    @Override
    public boolean setImage(@NonNull String path) {
        return false;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }
}
