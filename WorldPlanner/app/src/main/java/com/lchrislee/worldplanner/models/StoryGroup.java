package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;

public class StoryGroup extends SugarRecord implements Serializable, StoryElement {

    private String name;
    private String description;
    private String imagePath; // In SugarORM: image_path;
    private int size;

    private StoryWorld world;

    public StoryGroup() {
        this.name = "";
        this.description = "";
        this.imagePath = "";
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
        return imagePath;
    }

    @Override
    public void setImage(@NonNull String path) {
        imagePath = path;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public StoryWorld getWorld() {
        return world;
    }
}
