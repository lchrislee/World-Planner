package com.lchrislee.worldplanner.fragments.detail;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryGroup;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

public class GroupDetailFragment extends DetailFragment {

    private EditText size;

    private StoryGroup group;

    public GroupDetailFragment() {
        // Required
    }

    public static @NonNull GroupDetailFragment newInstance(@Nullable Serializable obj)
    {
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.GROUP);
        args.putSerializable(DetailFragment.DATA, obj);
        args.putInt(LAYOUT, R.layout.fragment_detail_group);
        args.putString(TITLE, "Group");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryGroup group = new StoryGroup();
            group.setWorld(currentWorld);
            model = group;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            size = (EditText) mainView.findViewById(R.id.fragment_detail_group_size);
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null) {
            group = (StoryGroup) model;
            size.setText(String.valueOf(group.getSize()));
        }
        updateViews();
        super.onResume();
    }

    @Override
    protected void updateViews()
    {
        if (size != null)
        {
            if (isEditing)
            {
                Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.editbox_background_normal);
                size.setBackground(editBackground);
            }
            else
            {
                int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
                ColorDrawable background = new ColorDrawable(transparent);
                size.setBackground(background);
            }

            size.setFocusable(isEditing);
            size.setClickable(isEditing);
            size.setFocusableInTouchMode(isEditing);
            size.setLongClickable(isEditing);

            String sizeText = size.getText().toString();
            if (sizeText.length() == 0)
            {
                group.setSize(0);
            }
            else
            {
                group.setSize(Integer.parseInt(sizeText));
            }

            super.updateViews();
        }

    }
}
