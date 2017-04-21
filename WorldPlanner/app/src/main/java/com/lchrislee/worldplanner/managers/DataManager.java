package com.lchrislee.worldplanner.managers;

import android.content.Context;
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

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chrisl on 3/29/17.
 */

public class DataManager extends WorldPlannerBaseManager{

    public static final int WORLD = 100;
    public static final int NOT_WORLD = 101;
    public static final int CHARACTER = 200;
    public static final int LOCATION = 300;
    public static final int ITEM = 400;
    public static final int PLOT = 500;

    private static final String MASTER_PREFERENCES = "com.lchrislee.worldplanner.managers.DataManager.PREFERENCES";
    private static final String SELECTED_WORLD = "DataManager_SELECTED_WORLD";

    private static DataManager instance;
    private static long currentWorldIndex;
    private boolean changedWorld;
    private StoryWorld currentWorld;

    private DataManager() {
        if (StoryWorld.count(StoryWorld.class) == 0)
        {
            StoryWorld world = new StoryWorld();
            world.save();
            currentWorld = null;
            changedWorld = true;
        }
    }

    @NonNull
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            currentWorldIndex = 1;
        }
        return instance;
    }

    @NonNull
    public static synchronized DataManager getInstance(@NonNull Context context)
    {
        if (instance == null)
        {
            SharedPreferences preferences = context.getSharedPreferences(MASTER_PREFERENCES, MODE_PRIVATE);
            long worldID = preferences.getLong(SELECTED_WORLD, 0);
            instance = new DataManager();
            instance.changeWorldToIndex(worldID);
        }
        return instance;
    }

    public long add(@NonNull StoryElement model) {
        Timber.d("Saving model with name: %s and description %s.", model.getName(), model.getDescription());
        long id;
        if (model instanceof StoryCharacter)
        {
            getCurrentWorld().save();
            id = ((StoryCharacter) model).save();
        }
        else if (model instanceof StoryLocation)
        {
            getCurrentWorld().save();
            id = ((StoryLocation) model).save();
        }
        else if (model instanceof StoryItem)
        {
            getCurrentWorld().save();
            id = ((StoryItem) model).save();
        }
        else if (model instanceof StoryPlot)
        {
            getCurrentWorld().save();
            id = ((StoryPlot) model).save();
        }
        else if (model instanceof StoryWorld)
        {
            id = ((StoryWorld) model).save();
        }
        else
        {
            id = -1;
        }

        if (!(model instanceof StoryWorld) && !(model instanceof StoryPlot) && id != -1)
        {
            getCurrentWorld().insertElement(model);
        }

        return id;
    }

    public void update(@NonNull StoryElement model) {
        Timber.d("Updating model with name - %s, and description - %s.", model.getName(), model.getDescription());
        if (model instanceof StoryCharacter) {
            getCurrentWorld().save();
            ((StoryCharacter) model).save();
        }
        else if (model instanceof StoryLocation) {
            getCurrentWorld().save();
            ((StoryLocation) model).save();
        }
        else if (model instanceof StoryItem) {
            getCurrentWorld().save();
            ((StoryItem) model).save();
        }
        else if (model instanceof StoryPlot) {
            getCurrentWorld().save();
            ((StoryPlot) model).save();
        }
        else if (model instanceof StoryWorld) {
            getCurrentWorld().save();
            ((StoryWorld) model).save();
        }
        else if (model instanceof StoryRelationship)
        {
            // TODO FIX Relationship
            return;
        }
    }

    public void setImage(@NonNull StoryElement model, @Nullable String path)
    {
        if (path == null)
        {
            return;
        }

        if (model.setImage(path))
        {
            update(model);
        }
    }

    public void delete(@NonNull StoryElement model)
    {
        Timber.d("Deleting model with name - %s, and description - %s.", model.getName(), model.getDescription());
        if (model instanceof StoryCharacter) {
            ((StoryCharacter) model).delete();
            getCurrentWorld().save();
        }
        else if (model instanceof StoryLocation) {
            ((StoryLocation) model).delete();
            getCurrentWorld().save();
        }
        else if (model instanceof StoryItem) {
            ((StoryItem) model).delete();
            getCurrentWorld().save();
        }
        else if (model instanceof StoryPlot) {
            ((StoryPlot) model).delete();
            getCurrentWorld().save();
        }
        else if (model instanceof StoryWorld) {
            StoryWorld delete = ((StoryWorld) model);
            long id = delete.getId();
            delete.delete();
            if (id == currentWorldIndex)
            {
                if (StoryWorld.count(StoryWorld.class) == 1)
                {
                    currentWorld = new StoryWorld();
                    currentWorldIndex = currentWorld.save();
                }
                else
                {
                    currentWorld = StoryWorld.first(StoryWorld.class);
                    currentWorldIndex = currentWorld.getId();
                }
            }
        }
        else if (model instanceof StoryRelationship)
        {
            // TODO FIX Relationship
            return;
        }
    }

    public int getCountForPlots()
    {
        return currentWorld.getPlotCount();
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
        if (index < 0)
        {
            return null;
        }
        return currentWorld.getCharacterAtIndex(index);
    }

    @Nullable
    public StoryPlot getPlotAtIndex(long index)
    {
        if (index < 0)
        {
            return null;
        }
        return currentWorld.getPlotAtIndex(index);
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
    public StoryElement getElementAtIndex(long index)
    {
        if (index < 0)
        {
            return null;
        }
        return currentWorld.getElementAtIndex(index);
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

    @NonNull
    public StoryWorld getCurrentWorld() {
        Timber.d("Getting world with id: %d", currentWorldIndex);
        if (changedWorld || currentWorld == null) {
            changedWorld = false;
            currentWorld = StoryWorld.findById(StoryWorld.class, currentWorldIndex);
        }
        return currentWorld;
    }

    public void changeWorldToIndex(long index) {
        ++index;
        Timber.d("Switching to world %d, there are %d in total.", index, StoryWorld.count(StoryWorld.class));
        if (index >= 1 && index <= StoryWorld.count(StoryWorld.class)) {
            currentWorldIndex = index;
            changedWorld = true;
            getCurrentWorld();
        }
    }

    public int getCountForAllWorldElements()
    {
        return getCurrentWorld().getElementsCount();
    }

    public int getElementTypeAtIndex(int index)
    {
        StoryElement element = getElementAtIndex(index);
        if (element != null)
        {
            if (element instanceof StoryCharacter)
            {
                return CHARACTER;
            }
            else if (element instanceof StoryLocation)
            {
                return LOCATION;
            }
            else if (element instanceof StoryItem)
            {
                return ITEM;
            }
            else if (element instanceof StoryPlot)
            {
                return PLOT;
            }
        }
        return -1;
    }

    public void retainSelectedWorld(@NonNull Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(MASTER_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SELECTED_WORLD, currentWorldIndex - 1);
        editor.apply();
    }
}
