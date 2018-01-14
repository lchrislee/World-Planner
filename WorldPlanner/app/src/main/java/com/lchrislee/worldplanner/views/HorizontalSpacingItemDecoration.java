package com.lchrislee.worldplanner.views;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Additional spacing intended for horizontally oriented RecyclerViews.
 */
public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration
{
    private int mHorizontalSpacing;

    public HorizontalSpacingItemDecoration (int horizontalSpacing)
    {
        this.mHorizontalSpacing = horizontalSpacing;
    }

    @Override
    public void getItemOffsets (@NonNull final Rect outRect,
                                @NonNull final View view,
                                @NonNull final RecyclerView parent,
                                @NonNull final RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mHorizontalSpacing;
        outRect.right = mHorizontalSpacing;
    }
}
