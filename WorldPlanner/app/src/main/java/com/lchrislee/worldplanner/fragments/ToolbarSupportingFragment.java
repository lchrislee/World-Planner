package com.lchrislee.worldplanner.fragments;

import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.StoryElement;

/**
 * Created by chrisl on 3/28/17.
 */

public interface ToolbarSupportingFragment {
    void editAction();
    @Nullable
    StoryElement getModel();
}
