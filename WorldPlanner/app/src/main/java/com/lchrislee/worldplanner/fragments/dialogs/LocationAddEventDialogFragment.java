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
import com.lchrislee.worldplanner.models.StoryEvent;

import java.util.ArrayList;
import java.util.List;

public class LocationAddEventDialogFragment extends DialogFragment {

    public interface LocationAddEventListener {
        void onPositiveClick(@NonNull List<Long> events);
    }

    private ArrayList<Long> selected;

    private LocationAddEventListener listener;

    public LocationAddEventDialogFragment() {
        // Required.
    }

    public static @NonNull
    LocationAddEventDialogFragment newInstance()
    {
        LocationAddEventDialogFragment fragment = new LocationAddEventDialogFragment();
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
        builder.setTitle("Add plot events to this location.");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_location_event, null);

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.dialog_location_event_list);
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
                LocationAddEventDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setListener(@NonNull LocationAddEventListener l)
    {
        listener = l;
    }

    private class AddPlotListAdapter extends RecyclerView.Adapter<AddPlotListAdapter.EventViewHolder>
    {
        private Context context;
        private List<StoryEvent> events;

        class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            final CheckedTextView name;
            EventViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                name = (CheckedTextView) itemView.findViewById(android.R.id.text1);
            }

            @Override
            public void onClick(View v) {
                name.setChecked(!name.isChecked());
                if (name.isChecked())
                {
                    selected.add(events.get(getAdapterPosition()).getId());
                }
                else
                {
                    selected.remove(events.get(getAdapterPosition()).getId());
                }
            }
        }

        AddPlotListAdapter(@NonNull Context context) {
            this.context = context;
            events = DataManager.getInstance().getAllEventsInWorld();
        }

        @Override
        public void onBindViewHolder(EventViewHolder holder, int position) {
            holder.name.setText(events.get(position).getName());
        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            return new EventViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }
}
