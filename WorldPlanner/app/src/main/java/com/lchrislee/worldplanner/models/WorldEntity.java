package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Fundamental models that can exist within a {@link World}.
 */
public interface WorldEntity extends BaseModel
{

    /**
     * Get a shortened name for the WorldEntity.
     *
     * @return The abbreviated name.
     */
    @NonNull String abbreviatedName();
}
