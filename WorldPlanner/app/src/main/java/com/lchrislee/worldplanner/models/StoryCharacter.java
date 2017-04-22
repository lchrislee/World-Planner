package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orm.SugarRecord;

import java.io.Serializable;

public class StoryCharacter extends SugarRecord implements Serializable, StoryElement{

    private String name;
    private String description;
    private String nickname;
    private String gender;
    private String imagePath; // In SugarORM: image_path;
    private int age;

    private StoryWorld world;

    public StoryCharacter() {
        name = "";
        description = "";
        imagePath = "";
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    public StoryWorld getWorld() {
        return world;
    }
}
