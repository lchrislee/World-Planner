package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * A user created character.
 */
public class Character implements WorldEntity
{
    private String mFirstName;

    private String mLastName;

    private String mDescription;

    public Character ()
    {
        mFirstName = "Unnamed";
        mLastName = "Character";
        mDescription = "An empty character.";
    }

    @NonNull
    @Override
    public String name ()
    {
        return mFirstName + " " + mLastName;
    }

    @Override
    public void updateName (@NonNull final String name)
    {
        if (name.contains(" "))
        {
            final int spaceIndex = name.indexOf(" ");
            mFirstName = name.substring(0, spaceIndex);
            mLastName = name.substring(spaceIndex);
        } else {
            mFirstName = name;
        }
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
        return mFirstName.charAt(0) + ". " + mLastName;
    }
}
