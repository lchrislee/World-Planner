package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryWorld extends WorldPlannerBaseModel implements ImportanceRelation.Important {

    private ImportanceRelation importantCharacters;
    private ImportanceRelation importantLocations;
    private ImportanceRelation importantItems;
    private ImportanceRelation importantPlots;

    public StoryWorld(@NonNull String title, @NonNull String description) {
        super(title, description);
        importantCharacters = new ImportanceRelation();
        importantLocations = new ImportanceRelation();
        importantItems = new ImportanceRelation();
        importantPlots = new ImportanceRelation();
    }

    public void addCharacter(@NonNull StoryCharacter c) {
        importantCharacters.addObject(c);
    }

    public void removeCharacter(@NonNull StoryCharacter c)
    {
        importantCharacters.removeObject(c);
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

    public @NonNull ImportanceRelation getImportantCharacters() {
        return importantCharacters;
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
        return ImportanceRelation.ImportantType.None;
    }

}
