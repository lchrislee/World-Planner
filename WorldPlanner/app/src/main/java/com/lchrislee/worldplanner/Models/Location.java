package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Location extends Entity {
    public Location(@NonNull String title, @NonNull String description) {
        super(title, description, EntityType.Location);
    }
}
