package com.lchrislee.worldplanner.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryPlot;

import java.util.ArrayList;
import java.util.List;

public class LocationAddPlotDialogFragment extends DialogFragment {

    public interface LocationAddPlotListener {
        void onPositiveClick(@NonNull List<Long> plots);
    }

    private ArrayList<Long> selected;

    private LocationAddPlotListener listener;

    public LocationAddPlotDialogFragment() {
        // Required.
    }

    public static @NonNull LocationAddPlotDialogFragment newInstance()
    {
        LocationAddPlotDialogFragment fragment = new LocationAddPlotDialogFragment();
        Bundle argument = new Bundle();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selected = new ArrayList<>();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add plot points to this location.");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_location_plot, null);

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.dialog_location_plot_list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(new AddPlotListAdapter(getContext()));

        builder.setView(v);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                {
                    listener.onPositiveClick(selected);
                }
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocationAddPlotDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setListener(@NonNull LocationAddPlotListener l)
    {
        listener = l;
    }

    private class AddPlotListAdapter extends RecyclerView.Adapter<AddPlotListAdapter.PlotViewHolder>
    {
        private Context context;
        private List<StoryPlot> plots;

        class PlotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            final CheckedTextView name;
            PlotViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                name = (CheckedTextView) itemView.findViewById(android.R.id.text1);
            }

            @Override
            public void onClick(View v) {
                name.setChecked(!name.isChecked());
                if (name.isChecked())
                {
                    selected.add(plots.get(getAdapterPosition()).getId());
                }
                else
                {
                    selected.remove(plots.get(getAdapterPosition()).getId());
                }
            }
        }

        AddPlotListAdapter(@NonNull Context context) {
            this.context = context;
            plots = DataManager.getInstance().getAllPlotsInWorld();
        }

        @Override
        public void onBindViewHolder(PlotViewHolder holder, int position) {
            holder.name.setText(plots.get(position).getName());
        }

        @Override
        public PlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            return new PlotViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return plots.size();
        }
    }
}
