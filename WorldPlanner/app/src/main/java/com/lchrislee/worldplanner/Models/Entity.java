package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Entity extends WorldPlannerBaseModel {
    public Entity(@NonNull String title, @NonNull String description) {
        super(title, description);
    }
}
