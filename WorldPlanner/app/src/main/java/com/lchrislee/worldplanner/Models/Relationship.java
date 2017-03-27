package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class Relationship extends WorldPlannerBaseModel {
    private ArrayList<Relationable> relevantObjects;

    public Relationship(@NonNull String title, @NonNull String description) {
        super(title, description);
        relevantObjects = new ArrayList<>();
    }

    public void addRelevantObject(@NonNull Relationable obj)
    {
        relevantObjects.add(obj);
    }

    public void removeRelevantObject(@NonNull Relationable obj)
    {
        relevantObjects.remove(obj);
    }

    public @NonNull ArrayList<Relationable> getRelevantObjects()
    {
        return relevantObjects;
    }

    public @Nullable Relationable getRelevantObject(int index)
    {
        if (index < 0 || index >= relevantObjects.size())
        {
            return null;
        }
        return relevantObjects.get(index);
    }

    public static int RELATIONABLE_TYPE_COUNT = 4;

    public interface Relationable
    {
        @NonNull RelationableType getRelationableType();
        @NonNull String getRelationableString();
    }

    public enum RelationableType
    {
        Character,
        Location,
        Item,
        Plot,
        None
    }
}
