package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by chrisl on 3/26/17.
 */

public class WorldPlannerBaseModel implements Serializable{

    private String name;
    private String description;

    public WorldPlannerBaseModel(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @NonNull String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
