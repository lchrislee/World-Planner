package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * An important item in the {@link World}.
 */
public class Item implements WorldEntity
{
    private String mName;

    private String mDescription;

    public Item ()
    {
        mName = "Unnamed Item";
        mDescription = "No value here.";
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
