package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * One collection of thought, characters, etc. for a story.
 */
public class World implements BaseModel, Serializable
{
    private String mName;

    private String mDescription;

    private long mId;

    public World () {
        mName = "world";
        mDescription = "World!";
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

    public long id ()
    {
        return mId;
    }

    public void setId (final long mId)
    {
        this.mId = mId;
    }
}
