package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoryWorld extends SugarRecord implements Serializable, StoryElement{

    private String name;
    private String description;
    private String imagePath; // In SugarORM: image_path

    @Ignore
    private List<StoryElement> allElements;

    @Ignore
    private List<StoryEvent> allEvents;

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

    public int getElementsCount()
    {
        if (checkElements())
        {
            generateAllElements();
        }
        return allElements.size();
    }

    @Nullable
    public StoryElement getElementAtIndex(long index)
    {
        if (checkElements())
        {
            generateAllElements();
        }

        if (allElements.size() == 0)
        {
            return null;
        }
        return allElements.get((int) index);
    }

    protected boolean checkElements()
    {
        String idClause[] = new String[]{String.valueOf(getId())};
        long elementsCount = StoryItem.count(StoryItem.class, "world = ?", idClause);
        elementsCount += StoryLocation.count(StoryLocation.class, "world = ?", idClause);
        elementsCount += StoryCharacter.count(StoryCharacter.class, "world = ?", idClause);
        elementsCount += StoryGroup.count(StoryGroup.class, "world = ?", idClause);
        return allElements == null || allElements.size() != elementsCount;
    }

    protected void generateAllElements()
    {
        allElements = new ArrayList<>();
        allElements.addAll(StoryItem.find(StoryItem.class, "world = ?", String.valueOf(getId())));
        allElements.addAll(StoryLocation.find(StoryLocation.class, "world = ?", String.valueOf(getId())));
        allElements.addAll(StoryCharacter.find(StoryCharacter.class, "world = ?", String.valueOf(getId())));
        allElements.addAll(StoryGroup.find(StoryGroup.class, "world = ?", String.valueOf(getId())));
        Collections.shuffle(allElements);
    }

    public void insertElement(@NonNull StoryElement element)
    {
        allElements.add(0, element);
    }

}
