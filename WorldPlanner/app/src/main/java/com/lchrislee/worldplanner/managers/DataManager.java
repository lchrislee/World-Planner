package com.lchrislee.worldplanner.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryGroup;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryEvent;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.orm.SugarRecord;

import java.util.List;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

public class DataManager extends WorldPlannerBaseManager{

    public static final int WORLD = 100;
    public static final int NOT_WORLD = 101;
    public static final int CHARACTER = 200;
    public static final int LOCATION = 300;
    public static final int ITEM = 400;
    public static final int EVENT = 500;
    public static final int GROUP = 600;

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
        if (model instanceof StoryWorld)
        {
            id = ((StoryWorld) model).save();
            return id;
        }
        else if (model instanceof SugarRecord)
        {
            currentWorld.save();
            id = ((SugarRecord) model).save();
        }
        else
        {
            id = -1;
        }

        if (!(model instanceof StoryEvent.StoryEventType)
                && !(model instanceof StoryItem.StoryItemEffect)
                && !(model instanceof StoryEvent)
                && id != -1)
        {
            currentWorld.insertElement(model);
        }

        return id;
    }

    public void update(@NonNull StoryElement model) {
        Timber.d("Updating model with name - %s, and description - %s.", model.getName(), model.getDescription());
        if (model instanceof StoryWorld) {
            ((StoryWorld) model).save();
        }
        else
        {
            currentWorld.save();
            ((SugarRecord) model).save();
            currentWorld.updateEvents();
        }
    }

    public void delete(@NonNull StoryElement model)
    {
        Timber.d("Deleting model with name - %s, and description - %s.", model.getName(), model.getDescription());
        if (model instanceof StoryWorld) {
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
        else {
            if (model instanceof StoryLocation) {
                currentWorld.removeLocationFromEvents((StoryLocation) model);
            } else if (model instanceof StoryEvent.StoryEventType) {
                currentWorld.removeEventTypeFromEvents((StoryEvent.StoryEventType) model);
            } else if (model instanceof StoryItem) {
                currentWorld.removeItemEffectsForItem((StoryItem) model);
            }
            if (model instanceof SugarRecord) {
                ((SugarRecord) model).delete();
                currentWorld.save();
                currentWorld.updateEvents();
            }
        }
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
    public StoryElement getElementAtIndex(int type, long index)
    {
        if (index < 0)
        {
            return null;
        }

        StoryElement element = null;
        switch(type)
        {
            case CHARACTER:
                element = currentWorld.getCharacterAtIndex(index);
                break;
            case LOCATION:
                element = currentWorld.getLocationAtIndex(index);
                break;
            case ITEM:
                element = currentWorld.getItemAtIndex(index);
                break;
            case GROUP:
                element = currentWorld.getGroupAtIndex(index);
                break;
        }

        return element;
    }

    public int getCountForEvents()
    {
        return currentWorld.getEventCount();
    }

    public @NonNull List<StoryEvent> getAllEventsNotInLocation(@NonNull StoryLocation location)
    {
        return currentWorld.getAllEventsNotInLocation(location);
    }

    @Nullable
    public StoryEvent getEventAtIndex(long index)
    {
        if (index < 0)
        {
            return null;
        }
        return currentWorld.getEventAtIndex(index);
    }

    public void setEventLocation(long index, @NonNull StoryLocation location)
    {
        StoryEvent event = StoryEvent.findById(StoryEvent.class, index);
        event.setLocation(location);
        update(event);
        updateEventsInCurrentWorld();
    }

    private void updateEventsInCurrentWorld()
    {
        currentWorld.updateEvents();
    }

    public void removeEventFromLocation(int index, @NonNull StoryLocation location)
    {
        StoryEvent event = location.removeEvent(index);
        event.setLocation(null);
        update(event);
        updateEventsInCurrentWorld();
    }

    public @Nullable StoryEvent.StoryEventType getEventTypeAtIndex(int index)
    {
        if (index < 0)
        {
            return null;
        }
        return currentWorld.getEventTypeAtIndex(index);
    }

    public int getEventTypeCountForWorld()
    {
        return currentWorld.getEventTypeCountForWorld();
    }

    public long getCountForWorlds() {
        return StoryWorld.count(StoryWorld.class);
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

    public int getCountForElementsOfType(int type)
    {
        int count = 0;
        switch(type)
        {
            case CHARACTER:
                count = currentWorld.getCharacterCount();
                break;
            case LOCATION:
                count = currentWorld.getLocationCount();
                break;
            case ITEM:
                count = currentWorld.getItemCount();
                break;
            case GROUP:
                count = currentWorld.getGroupCount();
                break;
        }
        return count;
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
            else if (element instanceof StoryGroup)
            {
                return GROUP;
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
