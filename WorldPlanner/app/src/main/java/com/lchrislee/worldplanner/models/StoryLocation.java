package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.managers.DataManager;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryLocation extends SugarRecord implements Serializable, StoryElement {

    private StoryWorld world;
    private String name;
    private String description;
    private String imagePath; // In SugarORM, image_path

    @Ignore
    private List<StoryPlot> plots;

    public StoryLocation() {
        name = "";
        description = "";
        imagePath = "";
        plots = null;
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

    public void setWorld(StoryWorld world) {
        this.world = world;
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

    public int getPlotsCount()
    {
        obtainPlotsList();
        return plots.size();
    }

    @Nullable
    public StoryPlot getPlotAtIndex(int index)
    {
        if (index < 0)
        {
            return null;
        }
        obtainPlotsList();
        if (index > plots.size())
        {
            return null;
        }
        return plots.get(index);
    }

    private void obtainPlotsList()
    {
        long count = StoryPlot.count(StoryItem.StoryItemEffect.class, "location = ?", new String[] {String.valueOf(getId())});
        if (plots == null || plots.size() != count)
        {
            plots = StoryPlot.find(StoryPlot.class, "location = ?", String.valueOf(getId()));
        }
    }

    public StoryPlot removePlot(int index)
    {
        StoryPlot plot = plots.get(index);
        plots.remove(index);
        return plot;
    }
}
