package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

/**
 * Created by chrisl on 3/27/17.
 */

public class StoryRelationship extends SugarRecord implements StoryElement{

    private String name;
    private String description;

    private StoryWorld world;
    private StoryCharacter firstStoryCharacter;
    private StoryCharacter secondStoryCharacter;

    public StoryRelationship() {
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

    public StoryCharacter getFirstStoryCharacter() {
        return firstStoryCharacter;
    }

    public void setFirstStoryCharacter(StoryCharacter firstStoryCharacter) {
        this.firstStoryCharacter = firstStoryCharacter;
    }

    public StoryCharacter getSecondStoryCharacter() {
        return secondStoryCharacter;
    }

    public void setSecondStoryCharacter(StoryCharacter secondStoryCharacter) {
        this.secondStoryCharacter = secondStoryCharacter;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }
}
