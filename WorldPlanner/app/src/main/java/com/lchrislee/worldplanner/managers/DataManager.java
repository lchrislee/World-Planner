package com.lchrislee.worldplanner.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryRelationship;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;
import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by chrisl on 3/29/17.
 */

public class DataManager {

    public static final int CODE_WORLD = 100;
    public static final int CODE_CHARACTER = 200;
    public static final int CODE_LOCATION = 300;
    public static final int CODE_ITEM = 400;
    public static final int CODE_PLOT = 500;
    public static final int CODE_RELATIONSHIP = 600;

    private static DataManager instance;
    private ArrayList<StoryWorld> allWorlds;
    private int currentWorldIndex;

    private DataManager() {
        allWorlds = new ArrayList<>();
        allWorlds.add(new StoryWorld());
        allWorlds.get(0).setName("");
        allWorlds.get(0).setDescription("");
        currentWorldIndex = 0;
    }

    @NonNull
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public int add(@NonNull StoryElement model) {
        if (model instanceof StoryCharacter)
        {
            return getCurrentWorld().addCharacter((StoryCharacter) model);
        }
        else if (model instanceof StoryLocation)
        {
            return getCurrentWorld().addLocation((StoryLocation) model);
        }
        else if (model instanceof StoryItem)
        {
            return getCurrentWorld().addItem((StoryItem) model);
        }
        else if (model instanceof StoryPlot)
        {
            return getCurrentWorld().addPlot((StoryPlot) model);
        }
        else if (model instanceof StoryWorld)
        {
            allWorlds.add((StoryWorld) model);
            return allWorlds.size() - 1;
        }
        else if (model instanceof StoryRelationship)
        {
            return getCurrentWorld().addRelationship((StoryRelationship) model);
        }
        return -1;
    }

    public boolean update(@NonNull StoryElement model, int index) {
        if (index < 0) {
            return false;
        }

        if (model instanceof StoryCharacter) {
            return getCurrentWorld().setCharacter(index, (StoryCharacter) model);
        }
        else if (model instanceof StoryLocation) {
            return getCurrentWorld().setLocation(index, (StoryLocation) model);
        }
        else if (model instanceof StoryItem) {
            return getCurrentWorld().setItem(index, (StoryItem) model);
        }
        else if (model instanceof StoryPlot) {
            return getCurrentWorld().setPlot(index, (StoryPlot) model);
        }
        else if (model instanceof StoryWorld) {
            if (index < allWorlds.size()) {
                allWorlds.set(index, (StoryWorld) model);
                return true;
            }
        }
        return false;
    }

    // This is what Lint tells me to do. It looks terrybly ugly.
    public boolean update(int characterIndex, int relationIndex, @NonNull StoryElement model) {
        return !(characterIndex < 0 || relationIndex < 0) && model instanceof StoryRelationship && getCurrentWorld().setRelationship(characterIndex, relationIndex, (StoryRelationship) model);
    }

    public int getCountForCharacters() {
        return getCurrentWorld().getCountCharacters();
    }

    public int getCountForLocations() {
        return getCurrentWorld().getCountLocations();
    }

    public int getCountForItems() {
        return getCurrentWorld().getCountItems();
    }

    public int getCountForPlots() {
        return getCurrentWorld().getCountPlots();
    }

    public int getCountForWorlds() {
        return allWorlds.size();
    }

    public int getRelationshipCountForCharacter(int characterIndex)
    {
        if (characterIndex < 0)
        {
            return 0;
        }
        return getCurrentWorld().getRelationshipCountForCharacter(characterIndex);
    }

    @Nullable
    public StoryCharacter getCharacterAtIndex(int index)
    {
        if (index < 0) {
            return null;
        }
        return getCurrentWorld().getCharacterAtIndex(index);
    }

    @Nullable
    public StoryLocation getLocationAtIndex(int index)
    {
        if (index < 0) {
            return null;
        }
        return getCurrentWorld().getLocationAtIndex(index);
    }

    @Nullable
    public StoryItem getItemAtIndex(int index)
    {
        if (index < 0) {
            return null;
        }
        return getCurrentWorld().getItemAtIndex(index);
    }

    @Nullable
    public StoryPlot getPlotAtIndex(int index)
    {
        if (index < 0) {
            return null;
        }
        return getCurrentWorld().getPlotAtIndex(index);
    }

    @Nullable
    public StoryWorld getWorldAtIndex(int index) {
        if (index >= 0 && index < allWorlds.size()) {
            return allWorlds.get(index);
        }
        return null;
    }

    @Nullable
    public StoryRelationship getRelationshipForCharacterAtIndex(int characterIndex, int position)
    {
        if (characterIndex < 0 || position < 0)
        {
            return null;
        }

        return getCurrentWorld().getRelationshipForCharacterAtIndex(characterIndex, position);
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

    @Nullable
    public ArrayList<StoryCharacter> getCharactersExcept(int index)
    {
        if (index < 0)
        {
            return null;
        }

        return getCurrentWorld().getCharactersExcept(index);
    }
}
