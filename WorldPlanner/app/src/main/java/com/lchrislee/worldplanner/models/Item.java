package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Item extends WorldPlannerBaseModel implements ImportanceRelation.Important {
    public Item(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    @NonNull
    @Override
    public ImportanceRelation.ImportantType getImportanceType() {
        return ImportanceRelation.ImportantType.Item;
    }

    @NonNull
    @Override
    public String getImportantTypeString() {
        return "Item";
    }
}
