package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Entity extends WorldPlannerBaseModel {
    public static int MAX_COUNT = 4;

    public EntityType type;

    public Entity(@NonNull String title, @NonNull String description, EntityType t) {
        super(title, description);
        type = t;
    }

    public static String getTypeString(EntityType t)
    {
        switch (t)
        {
            case Character:
                return "Character";
            case Location:
                return "Location";
            case Item:
                return "Item";
            case Plot:
                return "Plot";
            default:
                return null;
        }
    }

    public enum EntityType
    {
        Character,
        Location,
        Item,
        Plot,
        None
    }
}
