package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryCharacter implements Serializable, StoryElement{

    private String name;
    private String description;
    private String nickname;
    private String gender;
    private int age;

    private StoryWorld world;

    public StoryCharacter() {
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
        return getDescription();
    }

    public void setOccupation(@NonNull String occupation) {
        setDescription(occupation);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

}
