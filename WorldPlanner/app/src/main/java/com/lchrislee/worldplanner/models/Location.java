package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Location extends WorldPlannerBaseModel implements ImportanceRelation.Important {
    public Location(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    @NonNull
    @Override
    public ImportanceRelation.ImportantType getImportanceType() {
        return ImportanceRelation.ImportantType.Location;
    }
}
