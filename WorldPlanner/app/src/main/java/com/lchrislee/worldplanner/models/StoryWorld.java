package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryWorld implements Serializable, StoryElement{

    private String name;
    private String description;

    private ArrayList<StoryCharacter> allCharacters;
    private ArrayList<StoryLocation> allLocations;
    private ArrayList<StoryItem> allItems;
    private ArrayList<StoryPlot> allPlots;
    private ArrayList<StoryRelationship> allRelationships;

    public StoryWorld() {
        allCharacters = new ArrayList<>();
        allLocations = new ArrayList<>();
        allItems = new ArrayList<>();
        allPlots = new ArrayList<>();
        allRelationships = new ArrayList<>();
        name = "";
        description = "";
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public int addCharacter(@NonNull StoryCharacter character)
    {
        allCharacters.add(character);
        return allCharacters.size() - 1;
    }

    public int addLocation(@NonNull StoryLocation loc)
    {
        allLocations.add(loc);
        return allLocations.size() - 1;
    }

    public int addItem(@NonNull StoryItem item)
    {
        allItems.add(item);
        return allItems.size() - 1;
    }

    public int addPlot(@NonNull StoryPlot plot)
    {
        allPlots.add(plot);
        return allPlots.size() - 1;
    }

    public boolean setCharacter(int index, @NonNull StoryCharacter character)
    {
        if (index > allCharacters.size()) {
            return false;
        }
        allCharacters.set(index, character);
        return true;
    }

    public boolean setLocation(int index, @NonNull StoryLocation location)
    {
        if (index > allLocations.size()) {
            return false;
        }
        allLocations.set(index, location);
        return true;
    }

    public boolean setItem(int index, @NonNull StoryItem item)
    {
        if (index > allItems.size()) {
            return false;
        }
        allItems.set(index, item);
        return true;
    }

    public boolean setPlot(int index, @NonNull StoryPlot plot)
    {
        if (index > allPlots.size()) {
            return false;
        }
        allPlots.set(index, plot);
        return true;
    }

    public int getCountCharacters()
    {
        return allCharacters.size();
    }

    public int getCountLocations()
    {
        return allLocations.size();
    }

    public int getCountItems()
    {
        return allItems.size();
    }

    public int getCountPlots()
    {
        return allPlots.size();
    }

    @Nullable
    public StoryCharacter getCharacterAtIndex(int index)
    {
        if (index < allCharacters.size())
        {
            return allCharacters.get(index);
        }
        return null;
    }

    @Nullable
    public StoryLocation getLocationAtIndex(int index)
    {
        if (index < allLocations.size())
        {
            return allLocations.get(index);
        }
        return null;
    }

    @Nullable
    public StoryItem getItemAtIndex(int index)
    {
        if (index < allItems.size())
        {
            return allItems.get(index);
        }
        return null;
    }

    @Nullable
    public StoryPlot getPlotAtIndex(int index)
    {
        if (index < allPlots.size())
        {
            return allPlots.get(index);
        }
        return null;
    }

    private ArrayList<StoryRelationship> relationshipsForLastCharacter = new ArrayList<>();
    private int lastCharacterIndex = -1;

    private void getRelationshipsForCharacter(int characterIndex)
    {
        relationshipsForLastCharacter.clear();

        StoryCharacter character = allCharacters.get(characterIndex);
        for (StoryRelationship rel : allRelationships)
        {
            if (rel.getSecondStoryCharacter() == character
                    || rel.getFirstStoryCharacter() == character)
            {
                relationshipsForLastCharacter.add(rel);
            }
        }
    }

    @Nullable
    public StoryRelationship getRelationshipForCharacterAtIndex(int characterIndex, int relationshipIndex)
    {
        if (characterIndex >= allCharacters.size() || relationshipIndex >= allRelationships.size())
        {
            return null;
        }

        if (lastCharacterIndex != characterIndex)
        {
            getRelationshipsForCharacter(characterIndex);
        }

        return relationshipsForLastCharacter.get(relationshipIndex);
    }

    public int getRelationshipCountForCharacter(int characterIndex)
    {
        if (characterIndex >= allCharacters.size())
        {
            return 0;
        }

        if (lastCharacterIndex != characterIndex)
        {
            getRelationshipsForCharacter(characterIndex);
        }

        return relationshipsForLastCharacter.size();
    }

    public int addRelationship(@NonNull StoryRelationship relationship)
    {
        allRelationships.add(relationship);
        relationshipsForLastCharacter.add(relationship);
        return relationshipsForLastCharacter.size() - 1;
    }

    public boolean setRelationship(int characterIndex, int relationIndex, @NonNull StoryRelationship relationship)
    {
        lastCharacterIndex = -1;
        int count = 0;
        StoryCharacter character = getCharacterAtIndex(characterIndex);
        for (int i = 0; i < allRelationships.size(); ++i)
        {
            StoryRelationship rel = allRelationships.get(i);
            if (rel.getFirstStoryCharacter() == character
                || rel.getSecondStoryCharacter() == character)
            {
                if (count == relationIndex)
                {
                    allRelationships.set(i, relationship);
                    return true;
                }
                ++count;
            }
        }
        return false;
    }

    @NonNull
    public ArrayList<StoryCharacter> getCharactersExcept(int index)
    {
        ArrayList<StoryCharacter> returnList = new ArrayList<>();
        for (int i = 0; i < allCharacters.size(); ++i)
        {
            if (index == i)
            {
                continue;
            }
            returnList.add(allCharacters.get(i));
        }
        return returnList;
    }

}
