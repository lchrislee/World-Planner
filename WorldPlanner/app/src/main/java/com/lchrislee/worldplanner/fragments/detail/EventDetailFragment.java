package com.lchrislee.worldplanner.fragments.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.dialogs.SingleSelectEventTypeDialog;
import com.lchrislee.worldplanner.fragments.dialogs.SingleSelectDialog;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryEvent;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

public class EventDetailFragment extends DetailFragment implements SingleSelectDialog.SingleSelectDialogListener {

    private StoryEvent event;
    private TextView locationText;
    private TextView typeName;
    private TextView typeDescription;
    private Button changeEventType;

    public EventDetailFragment() {

    }

    public static @NonNull
    EventDetailFragment newInstance(@Nullable Serializable obj)
    {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.EVENT);
        args.putSerializable(DetailFragment.DATA, obj);
        args.putSerializable(LAYOUT, R.layout.fragment_detail_event);
        args.putString(TITLE, "Event");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryEvent item = new StoryEvent();
            item.setWorld(currentWorld);
            model = item;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            locationText = (TextView) mainView.findViewById(R.id.fragment_detail_event_location);
            typeName = (TextView) mainView.findViewById(R.id.fragment_detail_event_type_name);
            typeDescription = (TextView) mainView.findViewById(R.id.fragment_detail_event_type_description);
            changeEventType = (Button) mainView.findViewById(R.id.fragment_detail_event_type_change);
            changeEventType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingleSelectEventTypeDialog fragment = SingleSelectEventTypeDialog.newInstance("Select an event type");
                    fragment.setListener(EventDetailFragment.this);
                    fragment.show(getChildFragmentManager(), "Select Event Type");
                }
            });
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            event = (StoryEvent) model;
            updateTypeView(event.getType());
            if (locationText != null)
            {
                StoryLocation location = event.getLocation();
                if (location == null)
                {
                    locationText.setText("undisclosed location");
                }
                else
                {
                    locationText.setText(location.getName());
                }
            }
        }
        updateViews();
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            if (changeEventType != null) {
                changeEventType.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if (changeEventType != null) {
                changeEventType.setVisibility(View.GONE);
            }
        }
        super.updateViews();
    }

    @Override
    public void onPositivePressed(int selected) {
        StoryEvent.StoryEventType type = DataManager.getInstance().getEventTypeAtIndex(selected);
        event.setType(type);
        if (!isNew) {
            DataManager.getInstance().update(event);
        }

        updateTypeView(type);
    }

    private void updateTypeView(@Nullable StoryEvent.StoryEventType type)
    {
        if (type != null)
        {
            typeName.setText(type.getName());
            typeDescription.setText(type.getDescription());
            typeDescription.setVisibility(View.VISIBLE);
        }
        else
        {
            typeName.setText("Undeclared");
            typeDescription.setVisibility(View.GONE);
        }
    }
}
