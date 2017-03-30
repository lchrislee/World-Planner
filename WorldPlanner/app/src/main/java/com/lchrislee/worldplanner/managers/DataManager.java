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
    private ArrayList<StoryWorld> allWorlds;
    private int currentWorldIndex;

    private DataManager() {
        allWorlds = new ArrayList<>();
        allWorlds.add(new StoryWorld("", ""));
        currentWorldIndex = 0;
    }

    @NonNull
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public int add(@NonNull WorldPlannerBaseModel model, @NonNull ImportanceRelation.ImportantType type) {
        switch (type) {
            case Character:
                return getCurrentWorld().addCharacter((StoryCharacter) model);
            case Location:
                return getCurrentWorld().addLocation((StoryLocation) model);
            case Item:
                return getCurrentWorld().addItem((StoryItem) model);
            case Plot:
                return getCurrentWorld().addPlot((StoryPlot) model);
            case None:
                allWorlds.add((StoryWorld) model);
                return allWorlds.size() - 1;
        }
        return -1;
    }

    public boolean update(@NonNull WorldPlannerBaseModel model, int index, @NonNull ImportanceRelation.ImportantType type) {
        if (index < 0) {
            return false;
        }

        switch (type) {
            case Character:
                return getCurrentWorld().setCharacter(index, (StoryCharacter) model);
            case Location:
                return getCurrentWorld().setLocation(index, (StoryLocation) model);
            case Item:
                return getCurrentWorld().setItem(index, (StoryItem) model);
            case Plot:
                return getCurrentWorld().setPlot(index, (StoryPlot) model);
            case None:
                if (index < allWorlds.size()) {
                    allWorlds.set(index, (StoryWorld) model);
                    return true;
                }
                break;
        }
        return false;
    }

    public int getCountForType(@NonNull ImportanceRelation.ImportantType type) {
        Timber.tag(getClass().getSimpleName()).d("getCountForType - " + type.name());
        switch (type) {
            case Character:
                return getCurrentWorld().getCountCharacters();
            case Location:
                return getCurrentWorld().getCountLocations();
            case Item:
                return getCurrentWorld().getCountItems();
            case Plot:
                return getCurrentWorld().getCountPlots();
            default:
                return allWorlds.size();
        }
    }

    @Nullable
    public WorldPlannerBaseModel getAtIndexWithType(int index, @NonNull ImportanceRelation.ImportantType type) {
        if (index < 0) {
            return null;
        }

        switch (type) {
            case Character:
                return getCurrentWorld().getCharacterAtIndex(index);
            case Location:
                return getCurrentWorld().getLocationAtIndex(index);
            case Item:
                return getCurrentWorld().getItemAtIndex(index);
            case Plot:
                return getCurrentWorld().getPlotAtIndex(index);
            case None:
                if (index < allWorlds.size()) {
                    return allWorlds.get(index);
                }
                break;
        }
        return null;
    }

    public int getCurrentWorldIndex() {
        return currentWorldIndex;
    }

    @NonNull
    public StoryWorld getCurrentWorld() {
        return allWorlds.get(currentWorldIndex);
    }

    public void changeWorldToIndex(int index) {
        if (index >= 0 && index < allWorlds.size()) {
            currentWorldIndex = index;
        }
    }

    // Worlds

    public int getCountForWorlds() {
        return allWorlds.size();
    }

    @Nullable
    public StoryWorld getWorldAtIndex(int index) {
        Timber.tag(getClass().getSimpleName()).d("Getting world for index - " + index);
        if (index >= 0 && index < allWorlds.size()) {
            Timber.tag(getClass().getSimpleName()).d("World to return - " + allWorlds.get(index).getName());
            return allWorlds.get(index);
        }
        Timber.tag(getClass().getSimpleName()).d("No world found.");
        return null;
    }

}
