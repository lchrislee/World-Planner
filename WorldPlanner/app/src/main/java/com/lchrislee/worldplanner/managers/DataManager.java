package com.lchrislee.worldplanner.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.models.StoryRelationship;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by chrisl on 3/29/17.
 */

public class DataManager {

    private static DataManager instance;
    private ArrayList<StoryCharacter> allCharacters;
    private ArrayList<StoryLocation> allLocations;
    private ArrayList<StoryItem> allItems;
    private ArrayList<StoryPlot> allPlots;
    private ArrayList<StoryWorld> allWorlds;
    private ArrayList<StoryRelationship> allRelationships;

    private int currentWorldIndex;

    private DataManager()
    {
        allCharacters = new ArrayList<>();
        allLocations = new ArrayList<>();
        allItems = new ArrayList<>();
        allPlots = new ArrayList<>();
        allWorlds = new ArrayList<>();
        allWorlds.add(new StoryWorld("", ""));
        currentWorldIndex = 0;
    }

    @NonNull
    public static DataManager getInstance()
    {
        if (instance == null)
        {
            instance = new DataManager();
        }
        return instance;
    }

    public int add(@NonNull WorldPlannerBaseModel model, @NonNull ImportanceRelation.ImportantType type)
    {
        switch(type)
        {
            case Character:
                allCharacters.add((StoryCharacter) model);
                return allCharacters.size() - 1;
            case Location:
                allLocations.add((StoryLocation) model);
                return allLocations.size() - 1;
            case Item:
                allItems.add((StoryItem) model);
                return allItems.size() - 1;
            case Plot:
                allPlots.add((StoryPlot) model);
                return  allPlots.size() - 1;
            case None:
                allWorlds.add((StoryWorld) model);
                return allWorlds.size() - 1;
        }
        return -1;
    }

    public boolean update(@NonNull WorldPlannerBaseModel model, int index, @NonNull ImportanceRelation.ImportantType type)
    {
        if (index < 0)
        {
            return false;
        }

        switch(type)
        {
            case Character:
                if (index < allCharacters.size())
                {
                    allCharacters.set(index, (StoryCharacter) model);
                    return true;
                }
                break;
            case Location:
                if (index < allLocations.size())
                {
                    allLocations.set(index, (StoryLocation) model);
                    return true;
                }
                break;
            case Item:
                if (index < allItems.size())
                {
                    allItems.set(index, (StoryItem) model);
                    return true;
                }
                break;
            case Plot:
                if (index < allPlots.size())
                {
                    allPlots.set(index, (StoryPlot) model);
                    return true;
                }
                break;
            case None:
                if (index < allWorlds.size())
                {
                    allWorlds.set(index, (StoryWorld) model);
                    return true;
                }
                break;
        }
        return false;
    }

    public int getCountForType(@NonNull ImportanceRelation.ImportantType type)
    {
        Timber.tag(getClass().getSimpleName()).d("getCountForType - " + type.name());
        switch(type)
        {
            case Character:
                return allCharacters.size();
            case Location:
                return allLocations.size();
            case Item:
                return allItems.size();
            case Plot:
                return allPlots.size();
            default:
                return allWorlds.size();
        }
    }

    @Nullable
    public WorldPlannerBaseModel getAtIndexWithType(int index, @NonNull ImportanceRelation.ImportantType type)
    {
        if (index < 0)
        {
            return null;
        }

        switch(type)
        {
            case Character:
                if (index < allCharacters.size())
                {
                    return allCharacters.get(index);
                }
            case Location:
                if (index < allLocations.size())
                {
                    return allLocations.get(index);
                }
            case Item:
                if (index < allItems.size())
                {
                    return allItems.get(index);
                }
            case Plot:
                if (index < allPlots.size())
                {
                    return allPlots.get(index);
                }
            case None:
                if (index < allWorlds.size())
                {
                    return allWorlds.get(index);
                }
        }
        return null;
    }

    public int getCurrentWorldIndex() {
        return currentWorldIndex;
    }

    @NonNull
    public StoryWorld getCurrentWorld()
    {
        return allWorlds.get(currentWorldIndex);
    }

    // Characters

    @NonNull
    public ArrayList<StoryCharacter> getAllCharacters() {
        return allCharacters;
    }

    public void deleteCharacter(int index)
    {
        if (index < 0 || index >= allCharacters.size())
        {
            return;
        }
        allCharacters.remove(index);
    }

    // Locations

    @NonNull
    public ArrayList<StoryLocation> getAllLocations() {
        return allLocations;
    }

    public void deleteLocations(int index)
    {
        if (index < 0 || index >= allLocations.size())
        {
            return;
        }
        allLocations.remove(index);
    }

    // Items

    @NonNull
    public ArrayList<StoryItem> getAllItems() {
        return allItems;
    }

    public void deleteItem(int index)
    {
        if (index < 0 || index >= allItems.size())
        {
            return;
        }
        allItems.remove(index);
    }

    // Plots

    @NonNull
    public ArrayList<StoryPlot> getAllPlots() {
        return allPlots;
    }

    public void deletePlot(int index)
    {
        if (index < 0 || index >= allPlots.size())
        {
            return;
        }
        allPlots.remove(index);
    }

    // Worlds

    @NonNull
    public ArrayList<StoryWorld> getAllWorlds() {
        return allWorlds;
    }

    public void deleteWorld(int index)
    {
        if (index < 0 || index >= allWorlds.size())
        {
            return;
        }
        allWorlds.remove(index);
    }

    // Relationships

    @NonNull
    public ArrayList<StoryRelationship> getRelationshipsForCharacter(@NonNull StoryCharacter from)
    {
        ArrayList<StoryRelationship> output = new ArrayList<>();
        for (StoryRelationship relationship : allRelationships)
        {
            if (relationship.getFirstStoryCharacter() == from
                    || relationship.getSecondStoryCharacter() == from)
            {
                output.add(relationship);
            }
        }
        return output;
    }

    public void updateRelationship(@NonNull StoryRelationship relationship)
    {
        // TODO: This needs to update. Can't track index?
    }

    public void deleteRelationship(@NonNull StoryRelationship relationship)
    {
        // TODO: This needs to delete. Can't track index?
    }
}
