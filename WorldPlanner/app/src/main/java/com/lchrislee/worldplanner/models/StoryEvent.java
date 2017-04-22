package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;

public class StoryEvent extends SugarRecord implements Serializable, StoryElement {

    private StoryWorld world;
    private StoryLocation location;
    private StoryEventType type;

    private String name;
    private String description;

    public StoryEvent() {
        name = "";
        description = "";
        type = null;
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
    public void setImage(@NonNull String path) {
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    public void setLocation(StoryLocation location) {
        this.location = location;
    }

    public StoryLocation getLocation() {
        return location;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public StoryEventType getType() {
        return type;
    }

    public void setType(StoryEventType type) {
        this.type = type;
    }

    public static class StoryEventType extends SugarRecord implements Serializable, StoryElement
    {
        private StoryWorld world;

        private String name;
        private String description;

        private int color;

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
        public void setImage(@NonNull String path) {
            // TODO: Nothing
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public StoryWorld getWorld() {
            return world;
        }

        public void setWorld(StoryWorld world) {
            this.world = world;
        }
    }
}
