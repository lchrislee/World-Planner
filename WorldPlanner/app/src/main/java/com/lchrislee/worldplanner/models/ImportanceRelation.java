package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

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
        @NonNull String getImportantTypeString();
    }

    public enum ImportantType
    {
        Character,
        Location,
        Item,
        Plot,
        None
    }
}
