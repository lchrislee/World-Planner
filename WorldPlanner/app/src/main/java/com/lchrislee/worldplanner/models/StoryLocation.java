package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    private List<StoryEvent> events;

    public StoryLocation() {
        name = "";
        description = "";
        imagePath = "";
        events = null;
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

    public int getEventsCount()
    {
        obtainEventsList();
        return events.size();
    }

    @Nullable
    public StoryEvent getEventAtIndex(int index)
    {
        if (index < 0)
        {
            return null;
        }
        obtainEventsList();
        if (index > events.size())
        {
            return null;
        }
        return events.get(index);
    }

    private void obtainEventsList()
    {
        long count = StoryEvent.count(StoryItem.StoryItemEffect.class, "location = ?", new String[] {String.valueOf(getId())});
        if (events == null || events.size() != count)
        {
            events = StoryEvent.find(StoryEvent.class, "location = ?", String.valueOf(getId()));
        }
    }

    public StoryEvent removeEvent(int index)
    {
        StoryEvent event = events.get(index);
        events.remove(index);
        return event;
    }

    public StoryWorld getWorld() {
        return world;
    }
}
