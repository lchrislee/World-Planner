package com.lchrislee.worldplanner.fragments;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

import timber.log.Timber;

public class DetailFragment extends WorldPlannerBaseFragment implements ToolbarSupportingFragment {

    protected static final String RELATION_TYPE = "DETAIL_FRAGMENT_RELATION_TYPE";
    protected static final String EDIT = "DETAIL_FRAGMENT_FRAGMENT_EDIT";

    protected View mainView;
    protected EditText name;
    protected EditText description;
    protected ImageView image;

    protected ImportanceRelation.ImportantType typeToDisplay;
    protected boolean isEditing;

    public DetailFragment() {
        super();
        // Required empty public constructor
    }

    public static DetailFragment newInstance(ImportanceRelation.ImportantType type, boolean edit) {
        DetailFragment fragment = new DetailFragment();
        Bundle b = new Bundle();
        b.putSerializable(RELATION_TYPE, type);
        b.putBoolean(EDIT, edit);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        typeToDisplay = (ImportanceRelation.ImportantType) arguments.getSerializable(RELATION_TYPE);
        isEditing = arguments.getBoolean(EDIT, false);
        Timber.tag("DetailFragment").d("onCreate: " + typeToDisplay.name());
        Timber.d("OnCreate: isEditing - " + isEditing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.tag("DetailFragment").d("onCreateView start");
        switch (typeToDisplay)
        {
            case Character: // Propogate to CharacterDetailFragment.
                mainView = inflater.inflate(R.layout.fragment_detail_character, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
                break;
            case Location:
            case Item:
                mainView = inflater.inflate(R.layout.fragment_detail_default, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
                break;
            case Plot:
                mainView = inflater.inflate(R.layout.fragment_detail_plot, container, false);
                break;
            default:
                mainView = inflater.inflate(R.layout.fragment_detail_world, container, false);
                break;
        }
        name = (EditText) mainView.findViewById(R.id.fragment_detail_name);
        description = (EditText) mainView.findViewById(R.id.fragment_detail_description);

        swapEdit();
        Timber.tag("DetailFragment").d("onCreateView end");
        return mainView;
    }

    protected void swapEdit()
    {
        Timber.tag("DetailFragment").d("swapEdit start");
        if (isEditing)
        {
            Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.edit_text);
            name.setBackground(editBackground);
            description.setBackground(editBackground);
        }
        else
        {
            int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
            ColorDrawable background = new ColorDrawable(transparent);
            name.setBackground(background);
            description.setBackground(background);
        }

        name.setFocusable(isEditing);
        name.setClickable(isEditing);
        name.setFocusableInTouchMode(isEditing);
        name.setLongClickable(isEditing);
        description.setFocusable(isEditing);
        description.setClickable(isEditing);
        description.setFocusableInTouchMode(isEditing);
        description.setLongClickable(isEditing);

        mainView.requestLayout();
    }

    @Override
    public void editAction()
    {
        isEditing = !isEditing;
        swapEdit();
    }

    @NonNull
    @Override
    public WorldPlannerBaseModel getModel() {
        return new StoryCharacter("Blank", "FIX ME");
    }

}
