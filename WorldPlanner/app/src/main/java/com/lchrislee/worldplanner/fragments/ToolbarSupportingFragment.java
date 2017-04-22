package com.lchrislee.worldplanner.fragments;

import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.StoryElement;

public interface ToolbarSupportingFragment {
    long editAction();
    @Nullable
    StoryElement getModel();
}
