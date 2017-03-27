package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Plot extends Entity {
    public Plot(@NonNull String title, @NonNull String description) {
        super(title, description, EntityType.Plot);
    }
}
