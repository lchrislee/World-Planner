package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

public interface StoryElement {

    @NonNull
    String getName();

    void setName(@NonNull String name);

    @NonNull
    String getDescription();

    void setDescription(@NonNull String description);

    @NonNull String getImage();
    void setImage(@NonNull String path); // Return success/fail. No image should return fail.
}
