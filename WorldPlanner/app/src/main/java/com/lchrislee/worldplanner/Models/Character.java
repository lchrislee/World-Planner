package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class Character extends Entity {
    private ArrayList<Relationship> availableRelationships;
    private String nickname;
    private String gender;
    private int age;

    public Character(@NonNull String title, @NonNull String description) {
        super(title, description);
        availableRelationships = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(@NonNull String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
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

    public void addEntityToRelationship(int relationshipIndex, @NonNull Entity ent)
    {
        if (relationshipIndex < 0 || relationshipIndex >= availableRelationships.size())
        {
            return;
        }

        availableRelationships.get(relationshipIndex).addEntity(ent);
    }
}
