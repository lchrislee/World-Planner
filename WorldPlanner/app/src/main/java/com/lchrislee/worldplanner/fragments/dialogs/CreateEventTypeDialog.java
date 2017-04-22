package com.lchrislee.worldplanner.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryEvent;
import com.skydoves.colorpickerview.ColorPickerView;

import timber.log.Timber;

public class CreateEventTypeDialog extends DialogFragment {

    public interface EventTypeDialogListener
    {
        void onUpdate();
    }

    private static final String INDEX_EVENT = "CreateEventTypeDialog.INDEX_EVENT";

    private EventTypeDialogListener listener;

    private StoryEvent.StoryEventType eventType;

    private EditText name;
    private EditText description;

    private ColorPickerView colorPickerView;

    public CreateEventTypeDialog() {
        // Required.
    }

    public static @NonNull
    CreateEventTypeDialog newInstance(int index)
    {
        CreateEventTypeDialog fragment = new CreateEventTypeDialog();
        Bundle args = new Bundle();
        args.putInt(INDEX_EVENT, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int index = args.getInt(INDEX_EVENT);
        eventType = DataManager.getInstance().getEventTypeAtIndex(index);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(eventType == null ? "Create Event Type" : "Edit Event Type");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_event_type, null);
        name = (EditText) v.findViewById(R.id.dialog_event_type_name);
        description = (EditText) v.findViewById(R.id.dialog_event_type_description);
        colorPickerView = (ColorPickerView) v.findViewById(R.id.dialog_event_type_color_picker);

        if (eventType != null)
        {
            name.setText(eventType.getName());
            description.setText(eventType.getDescription());
        }

        builder.setView(v);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isNew = eventType == null;
                Timber.d("isNew: %b", isNew);
                if (isNew)
                {
                    eventType = new StoryEvent.StoryEventType();
                    eventType.setWorld(DataManager.getInstance().getCurrentWorld());
                }

                eventType.setName(name.getText().toString());
                eventType.setDescription(description.getText().toString());
                if (colorPickerView.getColor() != 0)
                {
                    eventType.setColor(colorPickerView.getColor());
                }

                if (isNew)
                {
                    DataManager.getInstance().add(eventType);
                }
                else
                {
                    DataManager.getInstance().update(eventType);
                }
                listener.onUpdate();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CreateEventTypeDialog.this.getDialog().cancel();
            }
        });

        if (eventType != null)
        {
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataManager.getInstance().delete(eventType);
                    listener.onUpdate();
                }
            });
        }

        return builder.create();
    }

    public void setListener(@NonNull EventTypeDialogListener l)
    {
        listener = l;
    }
}
