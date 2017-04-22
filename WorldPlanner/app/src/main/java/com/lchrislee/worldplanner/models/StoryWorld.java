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
    private String imagePath; // In SugarORM: image_path

    @Ignore
    private List<StoryElement> allElements;

    @Ignore
    private
    List<StoryPlot> allPlots;

    public StoryWorld() {
        name = "";
        description = "";
        allPlots = null;
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
    public boolean setImage(@NonNull String path) {
        imagePath = path;
        return true;
    }

    @Nullable
    public StoryPlot getPlotAtIndex(long index)
    {
        generatePlots();
        if (allPlots.size() == 0)
        {
            return null;
        }
        return allPlots.get((int) index);
    }

    public int getPlotCount()
    {
        generatePlots();
        return allPlots.size();
    }

    public @NonNull ArrayList<String> getAllPlotNames()
    {
        generatePlots();
        ArrayList<String> output = new ArrayList<>();
        for (StoryPlot p : allPlots)
        {
            output.add(p.getName());
        }
        return output;
    }

    private void generatePlots()
    {
        if (allPlots == null || allPlots.size() != StoryPlot.count(StoryPlot.class)) {
            allPlots = StoryPlot.find(StoryPlot.class, "world = ?", String.valueOf(getId()));
        }
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
