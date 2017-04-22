package com.lchrislee.worldplanner.fragments.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.WorldPlannerBaseListAdapter;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.dialogs.LocationAddPlotDialogFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;
import java.util.List;

public class LocationDetailFragment
        extends DetailFragment
        implements ToolbarSupportingFragment, LocationAddPlotDialogFragment.LocationAddPlotListener
{

    private StoryLocation location;

    private TextView listPrompt;
    private ImageView addPlot;
    private RecyclerView list;
    private StoryPlotInLocationListAdapter adapter;

    public LocationDetailFragment() {
        // Required
    }

    public static @NonNull LocationDetailFragment newInstance(@NonNull Serializable obj)
    {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.LOCATION);
        args.putSerializable(DetailFragment.DATA, obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryLocation location = new StoryLocation();
            location.setWorld(currentWorld);
            model = location;
            model.setName("");
            model.setDescription("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            adapter = new StoryPlotInLocationListAdapter(getContext());
            list = (RecyclerView) mainView.findViewById(R.id.fragment_detail_location_plots);
            list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
            list.setAdapter(adapter);

            listPrompt = (TextView) mainView.findViewById(R.id.fragment_detail_location_plot_prompt);
            addPlot = (ImageView) mainView.findViewById(R.id.fragment_detail_location_plot_add);
            addPlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationAddPlotDialogFragment fragment = LocationAddPlotDialogFragment.newInstance();
                    fragment.setListener(LocationDetailFragment.this);
                    fragment.show(getChildFragmentManager(), "Location add plot");
                }
            });

            updateViews();
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            location = (StoryLocation) model;
            if (adapter != null)
            {
                adapter.notifyDataSetChanged();
                list.scrollToPosition(adapter.getItemCount() - 1);
            }
        }
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            if (list != null)
            {
                if (isNew)
                {
                    list.setVisibility(View.GONE);
                    listPrompt.setVisibility(View.GONE);
                }
                else
                {
                    list.setVisibility(View.VISIBLE);
                    listPrompt.setVisibility(View.VISIBLE);
                }
                addPlot.setVisibility(View.GONE);
            }
        }
        else
        {
            if (addPlot != null) {
                addPlot.setVisibility(View.VISIBLE);
            }
        }

        super.updateViews();
    }

    @Override
    public void onPositiveClick(@NonNull List<Long> plots) {
        for (long l : plots) {
            DataManager.getInstance().setPlotLocation(l, location);
        }
        adapter.notifyDataSetChanged();
    }

    private class StoryPlotInLocationListAdapter extends WorldPlannerBaseListAdapter
    {
        private int lastPressed;
        private View.OnClickListener trueListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPressed = (int) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to remove this plot?");
                builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.getInstance().removePlotFromLocation(lastPressed, location);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        };

        StoryPlotInLocationListAdapter(@NonNull Context context) {
            super(context, R.layout.list_plot);
            setViewClickListener(trueListener);
        }

        @Override
        public int getItemCount() {
            return location.getPlotsCount();
        }

        @Nullable
        @Override
        protected StoryElement obtainElement(int position) {
            return location.getPlotAtIndex(position);
        }
    }

}
