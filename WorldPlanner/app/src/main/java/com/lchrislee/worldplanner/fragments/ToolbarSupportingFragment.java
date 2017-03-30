package com.lchrislee.worldplanner.fragments;

import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

/**
 * Created by chrisl on 3/28/17.
 */

public interface ToolbarSupportingFragment {
    void editAction();
    @Nullable WorldPlannerBaseModel getModel();
}
