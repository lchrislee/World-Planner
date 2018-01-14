package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * One collection of thought, characters, etc. for a story.
 */
public class World implements BaseModel
{
    private List<WorldEntity> mCharacters;

    private List<WorldEntity> mLocations;

    private List<WorldEntity> mItems;

    private String mName;

    private String mDescription;

    private long mId;

    public World () {
        mName = "";
        mDescription = "";
        mCharacters = new ArrayList<>();
        final int randomCharacters = (int) (Math.random() * 10 + 1);
        for (int i = 0; i < randomCharacters; ++i) {
            mCharacters.add(new Character());
        }
        mLocations = new ArrayList<>();
        final int randomLocations = (int) (Math.random() * 10 + 1);
        for (int i = 0; i < randomLocations; ++i) {
            mLocations.add(new Location());
        }
        mItems = new ArrayList<>();
        final int randomItems = (int) (Math.random() * 10 + 1);
        for (int i = 0; i < randomItems; ++i) {
            mItems.add(new Item());
        }
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

    @NonNull
    public List<WorldEntity> getCharacters ()
    {
        return mCharacters;
    }

    @NonNull
    public List<WorldEntity> getLocations ()
    {
        return mLocations;
    }

    @NonNull
    public List<WorldEntity> getItems ()
    {
        return mItems;
    }
}
