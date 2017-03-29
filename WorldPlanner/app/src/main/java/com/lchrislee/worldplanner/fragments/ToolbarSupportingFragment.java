package com.lchrislee.worldplanner.fragments;

import android.support.annotation.NonNull;

import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

/**
 * Created by chrisl on 3/28/17.
 */

public interface ToolbarSupportingFragment {
    void editAction();
    @NonNull WorldPlannerBaseModel getModel();
}
