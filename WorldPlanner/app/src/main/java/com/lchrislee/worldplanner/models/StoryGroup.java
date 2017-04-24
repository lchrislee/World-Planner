package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

public class StoryGroup extends SugarRecord implements Serializable, StoryElement {

    private String name;
    private String description;
    private String imagePath; // In SugarORM: image_path;
    private int size;

    private StoryWorld world;

    @Ignore
    private List<CharacterInGroup> charactersInGroup;

    public StoryGroup() {
        this.name = "";
        this.description = "";
        this.imagePath = "";
        charactersInGroup = null;
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

    @NonNull
    @Override
    public String getImage() {
        return imagePath;
    }

    @Override
    public void setImage(@NonNull String path) {
        imagePath = path;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public int getCharactersCount()
    {
        generateCharactersInGroup();
        return charactersInGroup.size();
    }

    public @Nullable
    CharacterInGroup getCharacterInGroup(int index)
    {
        if (index < 0)
        {
            return null;
        }
        generateCharactersInGroup();
        if (index > charactersInGroup.size())
        {
            return null;
        }
        return charactersInGroup.get(index);
    }

    public @NonNull
    CharacterInGroup removeCharacter(int index)
    {
        CharacterInGroup cin = charactersInGroup.get(index);
        charactersInGroup.remove(index);
        return cin;
    }

    private void generateCharactersInGroup()
    {
        long count = StoryGroup.CharacterInGroup.count(StoryGroup.CharacterInGroup.class, "master_group = ?", new String[] {String.valueOf(getId())});
        if (charactersInGroup == null || charactersInGroup.size() != count)
        {
            charactersInGroup = StoryGroup.CharacterInGroup.find(StoryGroup.CharacterInGroup.class, "master_group = ?", String.valueOf(getId()));
        }
    }

    public static class CharacterInGroup
            extends SugarRecord
            implements StoryElement, Serializable
    {
        private StoryGroup masterGroup; // In SugarORM: master_group
        private StoryCharacter character;

        private String name;
        private String description;

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

        @NonNull
        @Override
        public String getImage() {
            return "";
        }

        @Override
        public void setImage(@NonNull String path) {
            // EMPTY
        }

        public StoryGroup getMasterGroup() {
            return masterGroup;
        }

        public void setMasterGroup(StoryGroup group) {
            this.masterGroup = group;
        }

        public StoryCharacter getCharacter() {
            return character;
        }

        public void setCharacter(StoryCharacter character) {
            this.character = character;
        }
    }
}
