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
    private String imagePath; // In SugarORM: image_path;

    public StoryItem() {
        imagePath = "";
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

    @Override
    public boolean setImage(@NonNull String path) {
        imagePath = path;
        return true;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    @NonNull
    @Override
    public String getImage() {
        return imagePath;
    }
}
