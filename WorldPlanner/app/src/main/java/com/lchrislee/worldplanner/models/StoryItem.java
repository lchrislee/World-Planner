package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class StoryItem extends WorldPlannerBaseModel implements ImportanceRelation.Important {
    public StoryItem(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    @NonNull
    @Override
    public ImportanceRelation.ImportantType getImportanceType() {
        return ImportanceRelation.ImportantType.Item;
    }

}
