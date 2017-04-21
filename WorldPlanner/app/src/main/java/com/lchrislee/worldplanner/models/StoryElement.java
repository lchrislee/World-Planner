package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/30/17.
 */

public interface StoryElement {

    @NonNull
    String getName();

    void setName(@NonNull String name);

    @NonNull
    String getDescription();

    void setDescription(@NonNull String description);

    @NonNull String getImage();
    boolean setImage(@NonNull String path); // Return success/fail. No image should return fail.
}
