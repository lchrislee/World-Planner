package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryItem extends SugarRecord implements Serializable, StoryElement{

    private StoryWorld world;
    private String name;
    private String description;
    private String imagePath; // In SugarORM: image_path;

    @Ignore
    private List<StoryItemEffect> effects;

    public StoryItem() {
        imagePath = "";
        effects = null;
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

    @Override
    public boolean setImage(@NonNull String path) {
        imagePath = path;
        return true;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    @NonNull
    @Override
    public String getImage() {
        return imagePath;
    }

    public int getEffectsCount()
    {
        obtainEffectsList();
        return effects.size();
    }

    @Nullable
    public StoryItemEffect getEffectAtIndex(int index)
    {
        if (index < 0)
        {
            return null;
        }
        obtainEffectsList();
        if (index > effects.size())
        {
            return null;
        }
        return effects.get(index);
    }

    private void obtainEffectsList()
    {
        long count = StoryItemEffect.count(StoryItemEffect.class, "master_item = ?", new String[] {String.valueOf(getId())});
        if (effects == null || effects.size() != count)
        {
            effects = StoryItemEffect.find(StoryItemEffect.class, "master_item = ?", String.valueOf(getId()));
        }
    }

    public int addEffect(@NonNull StoryItemEffect effect)
    {
        effects.add(effect);
        return effects.size() - 1;
    }

    public static class StoryItemEffect extends SugarRecord implements StoryElement, Serializable
    {
        private String name;
        private String description;
        private StoryItem masterItem; // In SugarORM: master_item

        public StoryItemEffect() {
            name = "";
            description = "";
            masterItem = null;
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
            return "";
        }

        @Override
        public boolean setImage(@NonNull String path) {
            return false;
        }

        public void setMasterItem(StoryItem master) {
            this.masterItem = master;
        }
    }
}
