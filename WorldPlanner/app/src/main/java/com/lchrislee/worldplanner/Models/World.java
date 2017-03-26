package com.lchrislee.worldplanner.Models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class World extends WorldPlannerBaseModel {

    private Relationship protagonists;
    private Relationship antagonists;

    public World(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    public void addProtagonist(@NonNull Character pro) {
        if (protagonists != null) {
            protagonists.addEntity(pro);
        }
    }

    public void addAntagonist(@NonNull Character ant) {
        if (antagonists != null) {
            antagonists.addEntity(ant);
        }
    }
}
