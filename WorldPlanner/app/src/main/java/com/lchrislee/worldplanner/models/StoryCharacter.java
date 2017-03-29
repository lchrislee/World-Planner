package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryCharacter extends WorldPlannerBaseModel implements ImportanceRelation.Important {
    private ImportanceRelation importantItems;
    private ImportanceRelation importantLocations;
    private ImportanceRelation importantPlots;

    private String nickname;
    private String gender;
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

    public void addItem(@NonNull StoryItem i)
    {
        importantItems.addObject(i);
    }

    public void removeItem(@NonNull StoryItem i)
    {
        importantItems.removeObject(i);
    }

    public void addLocation(@NonNull StoryLocation l)
    {
        importantLocations.addObject(l);
    }
    public void removeLocation(@NonNull StoryLocation l)
    {
        importantLocations.removeObject(l);
    }


    public void addPlot(@NonNull StoryPlot p)
    {
        importantPlots.addObject(p);
    }

    public void removePlot(@NonNull StoryPlot p)
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
}
