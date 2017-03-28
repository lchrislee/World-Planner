package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryCharacter extends WorldPlannerBaseModel implements ImportanceRelation.Important {
    private ImportanceRelation importantItems;
    private ImportanceRelation importantLocations;
    private ImportanceRelation importantPlots;

    private String nickname;
    private String gender;
    private String occupation;
    private int age;

    public StoryCharacter(@NonNull String title, @NonNull String description) {
        super(title, description);
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

    public void addItem(@NonNull Item i)
    {
        importantItems.addObject(i);
    }

    public void removeItem(@NonNull Item i)
    {
        importantItems.removeObject(i);
    }

    public void addLocation(@NonNull Location l)
    {
        importantLocations.addObject(l);
    }
    public void removeLocation(@NonNull Location l)
    {
        importantLocations.removeObject(l);
    }


    public void addPlot(@NonNull Plot p)
    {
        importantPlots.addObject(p);
    }

    public void removePlot(@NonNull Plot p)
    {
        importantPlots.removeObject(p);
    }

    public @NonNull ImportanceRelation getImportantItems() {
        return importantItems;
    }

    public @NonNull ImportanceRelation getImportantLocations() {
        return importantLocations;
    }

    public @NonNull ImportanceRelation getImportantPlots() {
        return importantPlots;
    }

    @NonNull
    @Override
    public ImportanceRelation.ImportantType getImportanceType() {
        return ImportanceRelation.ImportantType.Character;
    }

    @NonNull
    @Override
    public String getImportantTypeString() {
        return "StoryCharacter";
    }
}
