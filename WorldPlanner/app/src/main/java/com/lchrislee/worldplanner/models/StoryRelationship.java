package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/27/17.
 */

public class StoryRelationship extends WorldPlannerBaseModel {
    private StoryCharacter firstStoryCharacter;
    private StoryCharacter secondStoryCharacter;

    public StoryRelationship(@NonNull String description, @NonNull StoryCharacter first, @NonNull StoryCharacter second) {
        super("StoryRelationship", description);
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
