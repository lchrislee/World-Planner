package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class ImportanceRelation extends WorldPlannerBaseModel {
    private ArrayList<Important> importantObjects;

    public ImportanceRelation() {
        super("ImportanceRelation", "");
        importantObjects = new ArrayList<>();
    }

    public void addObject(@NonNull Important obj)
    {
        importantObjects.add(obj);
    }

    public void removeObject(@NonNull Important obj)
    {
        importantObjects.remove(obj);
    }

    public @NonNull ArrayList<Important> getObjects()
    {
        return importantObjects;
    }

    public static final int IMPORTANT_TYPE_COUNT = 4;

    public interface Important
    {
        @NonNull
        ImportantType getImportanceType();
    }

    public enum ImportantType
    {
        Character,
        Location,
        Item,
        Plot,
        None
    }

    public static @Nullable String getImportantTypeString(@NonNull ImportantType type)
    {
        switch(type)
        {
            case Character:
                return "Character";
            case Location:
                return "StoryLocation";
            case Item:
                return "StoryItem";
            case Plot:
                return "StoryPlot";
            default:
                return null;
        }
    }
}
