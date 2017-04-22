package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;

public class StoryPlotListAdapter extends WorldPlannerBaseListAdapter
{
    private boolean isDetailable;
    private View.OnClickListener trueListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isDetailable) {
                Intent i = new Intent(context, EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.INDEX, (long) v.getTag());
                i.putExtra(EntityDetailActivity.TYPE, DataManager.PLOT);
                context.startActivity(i);
            }
        }
    };

    public StoryPlotListAdapter(@NonNull Context context) {
        super(context, R.layout.list_plot);
        isDetailable = true;
        setViewClickListener(trueListener);
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getCountForPlots();
    }

    public void setDetailable(boolean newDetail)
    {
        isDetailable = newDetail;
    }

    @Nullable
    @Override
    protected StoryElement obtainElement(int position)
    {
        return DataManager.getInstance().getPlotAtIndex(position);
    }
}
