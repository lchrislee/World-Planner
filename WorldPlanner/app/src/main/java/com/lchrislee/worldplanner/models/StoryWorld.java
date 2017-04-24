package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.managers.DataManager;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoryWorld extends SugarRecord implements Serializable, StoryElement{

    private String name;
    private String description;
    private String imagePath; // In SugarORM: image_path

    @Ignore
    private List<StoryCharacter> allCharacters;

    @Ignore
    private List<StoryItem> allItems;

    @Ignore
    private List<StoryLocation> allLocations;

    @Ignore
    private List<StoryGroup> allGroups;

    @Ignore
    private List<StoryEvent> allEvents;

    @Ignore
    private List<StoryEvent.StoryEventType> allEventTypes;

    public StoryWorld() {
        name = "";
        description = "";
        allEvents = null;
        imagePath = "";
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String getImage() {
        return imagePath;
    }

    @Override
    public void setImage(@NonNull String path) {
        imagePath = path;
    }

    @Nullable
    public StoryEvent getEventAtIndex(long index)
    {
        generateEvents();
        if (allEvents.size() == 0)
        {
            return null;
        }
        return allEvents.get((int) index);
    }

    public int getEventCount()
    {
        generateEvents();
        return allEvents.size();
    }

    public @NonNull List<StoryEvent> getAllEventsNotInLocation(@NonNull StoryLocation location)
    {
        generateEvents();
        List<StoryEvent> outputList = new ArrayList<>();

        for (StoryEvent event : allEvents)
        {
            StoryLocation eventLocation = event.getLocation();
            if (eventLocation == null)
            {
                outputList.add(event);
            }
            else if (!(eventLocation.getId().equals(location.getId())))
            {
                outputList.add(event);
            }
        }

        return outputList;
    }

    public @NonNull List<StoryCharacter> getAllCharactersNotInGroup(@NonNull StoryGroup group)
    {
        generateCharacters();
        List<StoryCharacter> outputList = new ArrayList<>();
        for (StoryCharacter c : allCharacters)
        {
            List<StoryGroup.CharacterInGroup> list =
                    Select.from(StoryGroup.CharacterInGroup.class).where(
                    Condition.prop("character").eq(c.getId()),
                    Condition.prop("master_group").eq(group.getId())
            ).list();
            if (list.size() == 0)
            {
                outputList.add(c);
            }
        }
        return outputList;
    }

    private void generateEvents()
    {
        if (allEvents == null || allEvents.size() != StoryEvent.count(StoryEvent.class)) {
            allEvents = StoryEvent.find(StoryEvent.class, "world = ?", String.valueOf(getId()));
        }
    }

    public void updateEvents()
    {
        allEvents = null;
        generateEvents();
    }

    public @NonNull
    StoryEvent.StoryEventType getEventTypeAtIndex(int index)
    {
        generateEventTypes();
        return allEventTypes.get(index);
    }

    public int getEventTypeCountForWorld()
    {
        generateEventTypes();
        return allEventTypes.size();
    }

    private void generateEventTypes()
    {
        if (allEventTypes == null || allEventTypes.size() != StoryEvent.StoryEventType.count(StoryEvent.StoryEventType.class)) {
            allEventTypes = StoryEvent.StoryEventType.find(StoryEvent.StoryEventType.class,
                    "world = ?", String.valueOf(getId()));
        }
    }

    public int getCharacterCount()
    {
        generateCharacters();
        return allCharacters.size();
    }

    public @Nullable StoryCharacter getCharacterAtIndex(long index)
    {
        generateCharacters();
        if (allCharacters.size() == 0)
        {
            return null;
        }
        return allCharacters.get((int) index);
    }

    private void generateCharacters()
    {
        if (allCharacters == null
                || allCharacters.size() != StoryCharacter.count(StoryCharacter.class)) {
            allCharacters = StoryCharacter.find(StoryCharacter.class,
                    "world = ?", String.valueOf(getId()));
        }
    }

    public int getLocationCount()
    {
        generateLocations();
        return allLocations.size();
    }

    public @Nullable StoryLocation getLocationAtIndex(long index)
    {
        generateLocations();
        if (allLocations.size() == 0)
        {
            return null;
        }
        return allLocations.get((int) index);
    }

    private void generateLocations()
    {
        if (allLocations == null
                || allLocations.size() != StoryLocation.count(StoryLocation.class)) {
            allLocations = StoryLocation.find(StoryLocation.class,
                    "world = ?", String.valueOf(getId()));
        }
    }

    public int getItemCount()
    {
        generateItems();
        return allItems.size();
    }

    public @Nullable StoryItem getItemAtIndex(long index)
    {
        generateItems();
        if (allItems.size() == 0)
        {
            return null;
        }
        return allItems.get((int) index);
    }

    private void generateItems()
    {
        if (allItems == null
                || allItems.size() != StoryItem.count(StoryItem.class)) {
            allItems = StoryItem.find(StoryItem.class,
                    "world = ?", String.valueOf(getId()));
        }
    }

    public int getGroupCount()
    {
        generateGroups();
        return allGroups.size();
    }

    public @Nullable StoryGroup getGroupAtIndex(long index)
    {
        generateGroups();
        if (allGroups.size() == 0)
        {
            return null;
        }
        return allGroups.get((int) index);
    }

    private void generateGroups()
    {
        if (allGroups == null
                || allGroups.size() != StoryGroup.count(StoryGroup.class)) {
            allGroups = StoryGroup.find(StoryGroup.class,
                    "world = ?", String.valueOf(getId()));
        }
    }

    public void removeLocationFromEvents(@NonNull StoryLocation location)
    {
        List<StoryEvent> events = Select.from(StoryEvent.class).where(
                Condition.prop("world").eq(String.valueOf(getId())),
                Condition.prop("location").eq(String.valueOf(location.getId()))
        ).list();

        for (StoryEvent event : events)
        {
            event.setLocation(null);
            DataManager.getInstance().update(event);
            generateEvents();
        }
    }

    public void removeEventTypeFromEvents(@NonNull StoryEvent.StoryEventType type)
    {
        List<StoryEvent> events = Select.from(StoryEvent.class).where(
                Condition.prop("world").eq(String.valueOf(getId())),
                Condition.prop("type").eq(String.valueOf(type.getId()))
        ).list();

        for (StoryEvent event : events)
        {
            event.setType(null);
            DataManager.getInstance().update(event);
            generateEvents();
        }
    }

    public void removeItemEffectsForItem(@NonNull StoryItem item)
    {
        List<StoryItem.StoryItemEffect> effects = Select.from(StoryItem.StoryItemEffect.class).where(
                Condition.prop("world").eq(String.valueOf(getId())),
                Condition.prop("master_item").eq(String.valueOf(item.getId()))
        ).list();

        for (StoryItem.StoryItemEffect effect : effects)
        {
            DataManager.getInstance().delete(effect);
        }
    }
}
