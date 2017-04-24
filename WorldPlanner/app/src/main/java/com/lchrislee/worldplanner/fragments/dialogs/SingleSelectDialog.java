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

public abstract class SingleSelectDialog extends DialogFragment {

    public interface SingleSelectDialogListener
    {
        void onPositivePressed(int selected);
    }

    protected static final String TITLE = "TITLE";
    protected static final String LAYOUT = "LAYOUT";

    private String title;
    private int layoutId;
    private int selectedPosition;

    private SingleSelectDialogListener listener;

    protected SingleSelectListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        title = arguments.getString(TITLE);
        layoutId = arguments.getInt(LAYOUT);
        selectedPosition = -1;
        adapter = obtainAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (title != null) {
            builder.setTitle(title);
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(layoutId, null);

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.dialog_select_list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        builder.setView(v);
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                {
                    listener.onPositivePressed(selectedPosition);
                }
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SingleSelectDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void setListener(@NonNull SingleSelectDialogListener l)
    {
        listener = l;
    }

    protected abstract @NonNull SingleSelectListAdapter obtainAdapter();

    abstract class SingleSelectListAdapter extends RecyclerView.Adapter<SingleSelectListAdapter.SingleSelectViewHolder>
    {
        class SingleSelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public CheckedTextView name;
            SingleSelectViewHolder(View itemView) {
                super(itemView);
                name = (CheckedTextView) itemView.findViewById(android.R.id.text1);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                selectedPosition = getAdapterPosition();
                updateElements();
            }
        }

        private Context context;

        SingleSelectListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public SingleSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(android.R.layout.simple_list_item_single_choice, parent, false);
            return new SingleSelectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SingleSelectViewHolder holder, int position) {
            holder.name.setChecked(position == selectedPosition);
            additionalBinding(holder, position);
        }

        protected abstract void additionalBinding(SingleSelectViewHolder holder, int position);

        protected abstract void updateElements();
    }
}
