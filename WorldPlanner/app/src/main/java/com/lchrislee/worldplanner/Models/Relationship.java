package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class Relationship extends WorldPlannerBaseModel {
    private ArrayList<Entity> relevantEntitites;

    public Relationship(@NonNull String title, @NonNull String description) {
        super(title, description);
        relevantEntitites = new ArrayList<>();
    }

    public void addEntity(@NonNull Entity ent)
    {
        relevantEntitites.add(ent);
    }

    public void removeEntity(@NonNull Entity ent)
    {
        relevantEntitites.remove(ent);
    }

    public @NonNull ArrayList<Entity> getRelevantEntities()
    {
        return relevantEntitites;
    }

    public Entity getEntity(int index)
    {
        if (index < 0 || index >= relevantEntitites.size())
        {
            return null;
        }
        return relevantEntitites.get(index);
    }
}
