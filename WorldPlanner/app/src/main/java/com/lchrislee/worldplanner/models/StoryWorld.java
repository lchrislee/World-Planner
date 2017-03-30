package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryWorld extends WorldPlannerBaseModel implements ImportanceRelation.Important {

    private ArrayList<StoryCharacter> allCharacters;
    private ArrayList<StoryLocation> allLocations;
    private ArrayList<StoryItem> allItems;
    private ArrayList<StoryPlot> allPlots;
    private ArrayList<StoryRelationship> allRelationships;

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
        allCharacters = new ArrayList<>();
        allLocations = new ArrayList<>();
        allItems = new ArrayList<>();
        allPlots = new ArrayList<>();
        allRelationships = new ArrayList<>();
    }

    public int addCharacter(@NonNull StoryCharacter character)
    {
        allCharacters.add(character);
        return allCharacters.size() - 1;
    }

    public int addLocation(@NonNull StoryLocation loc)
    {
        allLocations.add(loc);
        return allLocations.size() - 1;
    }

    public int addItem(@NonNull StoryItem item)
    {
        allItems.add(item);
        return allItems.size() - 1;
    }

    public int addPlot(@NonNull StoryPlot plot)
    {
        allPlots.add(plot);
        return allPlots.size() - 1;
    }

    public boolean setCharacter(int index, @NonNull StoryCharacter character)
    {
        if (index > allCharacters.size()) {
            return false;
        }
        allCharacters.set(index, character);
        return true;
    }

    public boolean setLocation(int index, @NonNull StoryLocation location)
    {
        if (index > allLocations.size()) {
            return false;
        }
        allLocations.set(index, location);
        return true;
    }

    public boolean setItem(int index, @NonNull StoryItem item)
    {
        if (index > allItems.size()) {
            return false;
        }
        allItems.set(index, item);
        return true;
    }

    public boolean setPlot(int index, @NonNull StoryPlot plot)
    {
        if (index > allPlots.size()) {
            return false;
        }
        allPlots.set(index, plot);
        return true;
    }

    public int getCountCharacters()
    {
        return allCharacters.size();
    }

    public int getCountLocations()
    {
        return allLocations.size();
    }

    public int getCountItems()
    {
        return allItems.size();
    }

    public int getCountPlots()
    {
        return allPlots.size();
    }

    @Nullable
    public StoryCharacter getCharacterAtIndex(int index)
    {
        if (index < allCharacters.size())
        {
            return allCharacters.get(index);
        }
        return null;
    }

    @Nullable
    public StoryLocation getLocationAtIndex(int index)
    {
        if (index < allLocations.size())
        {
            return allLocations.get(index);
        }
        return null;
    }

    @Nullable
    public StoryItem getItemAtIndex(int index)
    {
        if (index < allItems.size())
        {
            return allItems.get(index);
        }
        return null;
    }

    @Nullable
    public StoryPlot getPlotAtIndex(int index)
    {
        if (index < allPlots.size())
        {
            return allPlots.get(index);
        }
        return null;
    }

    public void addImportantCharacter(@NonNull StoryCharacter c) {
        importantCharacters.addObject(c);
    }

    public void removeImportantCharacter(@NonNull StoryCharacter c)
    {
        importantCharacters.removeObject(c);
    }


    public void addImportantItem(@NonNull StoryItem i)
    {
        importantItems.addObject(i);
    }

    public void removeImportantItem(@NonNull StoryItem i)
    {
        importantItems.removeObject(i);
    }

    public void addImportantLocation(@NonNull StoryLocation l)
    {
        importantLocations.addObject(l);
    }

    public void removeImportantLocation(@NonNull StoryLocation l)
    {
        importantLocations.removeObject(l);
    }


    public void addImportantPlot(@NonNull StoryPlot p)
    {
        importantPlots.addObject(p);
    }

    public void removeImportantPlot(@NonNull StoryPlot p)
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
