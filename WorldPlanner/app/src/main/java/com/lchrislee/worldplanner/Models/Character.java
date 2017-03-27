package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class Character extends WorldPlannerBaseModel implements Relationship.Relationable {
    private ArrayList<Relationship> availableRelationships;
    private String nickname;
    private String gender;
    private String occupation;
    private int age;

    public Character(@NonNull String title, @NonNull String description) {
        super(title, description);
        availableRelationships = new ArrayList<>();
    }

    public @Nullable String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public @Nullable String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    public @Nullable String getOccupation() {
        return occupation;
    }

    public void setOccupation(@NonNull String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addRelationship(@NonNull Relationship newRel)
    {
        availableRelationships.add(newRel);
    }

    public void addObjectToRelationship(int relationshipIndex, @NonNull WorldPlannerBaseModel obj)
    {
        if (relationshipIndex < 0 || relationshipIndex >= availableRelationships.size())
        {
            return;
        }

        if (obj instanceof Relationship.Relationable) {
            availableRelationships.get(relationshipIndex).addRelevantObject((Relationship.Relationable) obj);
        }
    }

    public @Nullable Relationship getRelationship(int index)
    {
        if (index < 0 || index > availableRelationships.size())
        {
            return null;
        }
        return availableRelationships.get(index);
    }

    public @NonNull ArrayList<Relationship> getAvailableRelationships()
    {
        return availableRelationships;
    }

    @NonNull
    @Override
    public Relationship.RelationableType getRelationableType() {
        return Relationship.RelationableType.Character;
    }

    @NonNull
    @Override
    public String getRelationableString() {
        return "Character";
    }
}
