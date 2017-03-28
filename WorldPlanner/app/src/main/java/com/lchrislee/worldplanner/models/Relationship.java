package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/27/17.
 */

public class Relationship extends WorldPlannerBaseModel {
    private Character firstCharacter;
    private Character secondCharacter;

    public Relationship(@NonNull String description, @NonNull Character first, @NonNull Character second) {
        super("Relationship", description);
        firstCharacter = first;
        secondCharacter = second;
    }

    public Character getFirstCharacter() {
        return firstCharacter;
    }

    public void setFirstCharacter(Character firstCharacter) {
        this.firstCharacter = firstCharacter;
    }

    public Character getSecondCharacter() {
        return secondCharacter;
    }

    public void setSecondCharacter(Character secondCharacter) {
        this.secondCharacter = secondCharacter;
    }
}
