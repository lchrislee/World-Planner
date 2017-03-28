package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/27/17.
 */

public class Relationship extends WorldPlannerBaseModel {
    private StoryCharacter firstStoryCharacter;
    private StoryCharacter secondStoryCharacter;

    public Relationship(@NonNull String description, @NonNull StoryCharacter first, @NonNull StoryCharacter second) {
        super("Relationship", description);
        firstStoryCharacter = first;
        secondStoryCharacter = second;
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
}
