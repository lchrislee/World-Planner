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
import com.lchrislee.worldplanner.models.StoryElement;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleSelectDialog extends DialogFragment {

    protected static final String MASTER = "MASTER";
    protected static final String TITLE = "TITLE";

    public interface MultipleSelectAddEventListener {
        void onPositiveClick(@NonNull List<Long> selected);
    }

    protected ArrayList<Long> selected;

    protected StoryElement seekingElement;

    protected MultipleSelectAddEventListener listener;

    protected String title;

    public MultipleSelectDialog() {
        // Required
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selected = new ArrayList<>();
        seekingElement = (StoryElement) getArguments().getSerializable(MASTER);
        title = getArguments().getString(TITLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_select, null);

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.dialog_select_list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        list.setAdapter(getAdapter());

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
                MultipleSelectDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setListener(@NonNull MultipleSelectAddEventListener l)
    {
        listener = l;
    }

    protected abstract @NonNull MultipleSelectListAdapter getAdapter();

    abstract class MultipleSelectListAdapter extends RecyclerView.Adapter<MultipleSelectListAdapter.MultipleSelectViewHolder>
    {
        class MultipleSelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            final CheckedTextView name;
            MultipleSelectViewHolder(View itemView) {
                super(itemView);
                name = (CheckedTextView) itemView.findViewById(android.R.id.text1);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                name.setChecked(!name.isChecked());
                if (name.isChecked())
                {
                    selected.add(getIdAtIndex(getAdapterPosition()));
                }
                else
                {
                    selected.remove(getIdAtIndex(getAdapterPosition()));
                }
            }
        }

        protected Context context;

        MultipleSelectListAdapter(@NonNull Context context) {
            this.context = context;
        }

        @Override
        public MultipleSelectListAdapter.MultipleSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
            return getViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MultipleSelectListAdapter.MultipleSelectViewHolder holder, int position) {
            holder.name.setText(getElementAtIndex(position).getName());
            additionalBinding(holder, position);
        }

        @NonNull
        MultipleSelectViewHolder getViewHolder(@NonNull View v) {
            return new MultipleSelectListAdapter.MultipleSelectViewHolder(v);
        }

        protected abstract void additionalBinding(MultipleSelectListAdapter.MultipleSelectViewHolder holder, int position);

        protected abstract @NonNull StoryElement getElementAtIndex(int position);

        protected abstract long getIdAtIndex(int position);

    }
}
