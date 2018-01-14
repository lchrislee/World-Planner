package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * The foundational information for all model types.
 */
public interface BaseModel extends Serializable
{

    /**
     * The user-defined name of the entity.
     *
     * @return The name.
     */
    @NonNull String name();

    /**
     * Change the name of an entity.
     *
     * @param name The new name for the entity.
     */
    void updateName(@NonNull final String name);

    /**
     * The user-defined description of the entity.
     *
     * @return The description.
     */
    @NonNull String description();

    /**
     * Change the description of an entity.
     *
     * @param description The new description for the entity.
     */
    void updateDescription(@NonNull final String description);
}
