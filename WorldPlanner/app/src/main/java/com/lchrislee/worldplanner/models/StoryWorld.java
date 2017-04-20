package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryWorld extends SugarRecord implements Serializable, StoryElement{

    private String name;
    private String description;

    @Ignore
    private List<StoryElement> allElements;

    @Ignore
    private
    List<StoryCharacter> allCharacters;
    @Ignore
    private
    List<StoryLocation> allLocations;
    @Ignore
    private
    List<StoryItem> allItems;
    @Ignore
    private
    List<StoryPlot> allPlots;

    public StoryWorld() {
        name = "";
        description = "";
        allCharacters = null;
        allLocations = null;
        allItems = null;
        allPlots = null;
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

    @Nullable
    public StoryCharacter getCharacterAtIndex(long index)
    {
        if (allCharacters == null || allCharacters.size() != StoryCharacter.count(StoryCharacter.class)) {
            allCharacters = StoryCharacter.find(StoryCharacter.class, "world = ?", String.valueOf(getId()));
        }
        if (allCharacters.size() == 0)
        {
            return null;
        }
        return allCharacters.get((int) index);
    }

    @Nullable
    public StoryLocation getLocationAtIndex(long index)
    {
        if (allLocations == null || allLocations.size() != StoryLocation.count(StoryLocation.class)) {
            allLocations = StoryLocation.find(StoryLocation.class, "world = ?", String.valueOf(getId()));
        }
        if (allLocations.size() == 0)
        {
            return null;
        }
        return allLocations.get((int) index);
    }

    @Nullable
    public StoryItem getItemAtIndex(long index)
    {
        if (allItems == null || allItems.size() != StoryItem.count(StoryItem.class)) {
            allItems = StoryItem.find(StoryItem.class, "world = ?", String.valueOf(getId()));
        }
        if (allItems.size() == 0)
        {
            return null;
        }
        return allItems.get((int) index);
    }

    @Nullable
    public StoryPlot getPlotAtIndex(long index)
    {
        if (allPlots == null || allPlots.size() != StoryPlot.count(StoryPlot.class)) {
            allPlots = StoryPlot.find(StoryPlot.class, "world = ?", String.valueOf(getId()));
        }
        if (allPlots.size() == 0)
        {
            return null;
        }
        return allPlots.get((int) index);
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
        elementsCount += StoryPlot.count(StoryPlot.class, "world = ?", idClause);
        return allElements == null || allElements.size() != elementsCount;
    }

    protected void generateAllElements()
    {
        allElements = new ArrayList<>();
        allElements.addAll(StoryItem.find(StoryItem.class, "world = ?", String.valueOf(getId())));
        allElements.addAll(StoryLocation.find(StoryLocation.class, "world = ?", String.valueOf(getId())));
        allElements.addAll(StoryCharacter.find(StoryCharacter.class, "world = ?", String.valueOf(getId())));
        allElements.addAll(StoryPlot.find(StoryPlot.class, "world = ?", String.valueOf(getId())));
        Collections.shuffle(allElements);
    }

    public void insertElement(@NonNull StoryElement element)
    {
        allElements.add(0, element);
    }

}
