package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * One collection of thought, characters, etc. for a story.
 */
public class Universe implements BaseModel
{
    private String mName;

    private String mDescription;

    public Universe() {
        mName = "";
        mDescription = "";
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
}
