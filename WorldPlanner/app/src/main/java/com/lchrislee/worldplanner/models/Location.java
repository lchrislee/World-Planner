package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * An important location in a {@link World}.
 */
public class Location implements WorldEntity
{
    private String mName;

    private String mDescription;

    public Location ()
    {
        mName = "Unnamed Location";
        mDescription = "Unknown place";
    }

    @NonNull
    @Override
    public String name ()
    {
        return mName;
    }

    @Override
    public void updateName (@NonNull final String name)
    {
        mName = name;
    }

    @NonNull
    @Override
    public String description ()
    {
        return mDescription;
    }

    @Override
    public void updateDescription (@NonNull final String description)
    {
        mDescription = description;
    }

    @NonNull
    @Override
    public String abbreviatedName ()
    {
        return mName.length() < 13 ? mName : (mName.substring(0, 10) + "...");
    }
}
