package com.lchrislee.worldplanner.managers;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryRelationship;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

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
    private static long currentWorldIndex;

    private DataManager() {
        if (StoryWorld.count(StoryWorld.class) == 0)
        {
            StoryWorld world = new StoryWorld();
            world.setName("");
            world.setDescription("");
            world.save();
        }
    }

    @NonNull
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            currentWorldIndex = 0;
        }
        return instance;
    }

    public long add(@NonNull StoryElement model) {
        if (model instanceof StoryCharacter)
        {
            return ((StoryCharacter) model).save();
        }
        else if (model instanceof StoryLocation)
        {
            return ((StoryLocation) model).save();
        }
        else if (model instanceof StoryItem)
        {
            return ((StoryItem) model).save();
        }
        else if (model instanceof StoryPlot)
        {
            return ((StoryPlot) model).save();
        }
        else if (model instanceof StoryWorld)
        {
            return ((StoryWorld) model).save();
        }
        else if (model instanceof StoryRelationship)
        {
            //TODO FIX Relationships
            return -1;
        }

        return -1;
    }

    public void update(@NonNull StoryElement model, long index) {
        if (index < 0) {
            return;
        }

        if (model instanceof StoryCharacter) {
            ((StoryCharacter) model).save();
        }
        else if (model instanceof StoryLocation) {
            ((StoryLocation) model).save();
        }
        else if (model instanceof StoryItem) {
            ((StoryItem) model).save();
        }
        else if (model instanceof StoryPlot) {
            ((StoryPlot) model).save();
        }
        else if (model instanceof StoryWorld) {
            ++index;
            ((StoryWorld) model).save();
        }
        else if (model instanceof StoryRelationship)
        {
            // TODO FIX Relationship
            return;
        }
    }

    public long getCountForCharacters()
    {
        return StoryCharacter.count(StoryCharacter.class, "world = ?", new String[]{String.valueOf(currentWorldIndex)});
    }

    public long getCountForLocations()
    {
        return StoryLocation.count(StoryLocation.class, "world = ?", new String[]{String.valueOf(currentWorldIndex)});
    }

    public long getCountForItems()
    {
        return StoryItem.count(StoryItem.class, "world = ?", new String[]{String.valueOf(currentWorldIndex)});
    }

    public long getCountForPlots()
    {
        return StoryPlot.count(StoryPlot.class, "world = ?", new String[]{String.valueOf(currentWorldIndex)});
    }

    public long getCountForWorlds() {
        return StoryWorld.count(StoryWorld.class);
    }

    public long getRelationshipCountForCharacter(long characterIndex)
    {
        return 0;
        // TODO: Fix relationships
    }

    @Nullable
    public StoryCharacter getCharacterAtIndex(long index)
    {
        return StoryCharacter.findById(StoryCharacter.class, index);
    }

    @Nullable
    public StoryLocation getLocationAtIndex(long index)
    {
        if (index < 0) {
            return null;
        }
        return StoryLocation.findById(StoryLocation.class, index);
    }

    @Nullable
    public StoryItem getItemAtIndex(long index)
    {
        if (index < 0) {
            return null;
        }
        return StoryItem.findById(StoryItem.class, index);
    }

    @Nullable
    public StoryPlot getPlotAtIndex(long index)
    {
        if (index < 0) {
            return null;
        }
        return StoryPlot.findById(StoryPlot.class, index);
    }

    @Nullable
    public StoryWorld getWorldAtIndex(long index) {
        ++index;
        if (index < 1)
        {
            return null;
        }
        return StoryWorld.findById(StoryWorld.class, index);
    }

    @Nullable
    public StoryRelationship getRelationshipForCharacterAtIndex(long characterIndex, long position)
    {
        if (characterIndex < 0 || position < 0)
        {
            return null;
        }

        return null;
    }

    public long getCurrentWorldIndex() {
        return currentWorldIndex;
    }

    @NonNull
    public StoryWorld getCurrentWorld() {
        return StoryWorld.findById(StoryWorld.class, currentWorldIndex + 1);
    }

    public void changeWorldToIndex(long index) {
        if (index >= 0 && index < StoryWorld.count(StoryWorld.class)) {
            currentWorldIndex = index;
        }
    }

    @Nullable
    public List<StoryCharacter> getCharactersExcept(long index)
    {
        return Select.from(StoryCharacter.class).where(Condition.prop("id").notEq(index)).list();
    }
}
