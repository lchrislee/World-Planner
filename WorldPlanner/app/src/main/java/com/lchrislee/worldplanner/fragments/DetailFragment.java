package com.lchrislee.worldplanner.fragments;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.R;

public class DetailFragment extends WorldPlannerBaseFragment implements EditableFragment{

    private static final String RELATION_TYPE = "ENTITY_LIST_FRAGMENT_RELATION_TYPE";
    private static final String EDIT = "ENTITY_LIST_FRAGMENT_EDIT";

    private EditText name;
    private EditText nickname;
    private EditText description;
    private EditText gender;
    private EditText age;

    private ImportanceRelation.ImportantType typeToDisplay;
    private boolean isEditing;

    public DetailFragment() {
        super();
        // Required empty public constructor
    }

    public static DetailFragment newInstance(ImportanceRelation.ImportantType type)
    {
        return newInstance(type, false);
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
        isEditing = arguments.getBoolean(EDIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView;
        switch (typeToDisplay)
        {
            case Character:
                mainView = inflater.inflate(R.layout.fragment_detail_character_information, container, false);
                name = (EditText) mainView.findViewById(R.id.fragment_detail_character_name);
                description = (EditText) mainView.findViewById(R.id.fragment_detail_character_description);
                nickname = (EditText) mainView.findViewById(R.id.fragment_detail_character_nickname);
                gender = (EditText) mainView.findViewById(R.id.fragment_detail_character_gender);
                age = (EditText) mainView.findViewById(R.id.fragment_detail_character_age);
                break;
            case Location:
            case Item:
                mainView = inflater.inflate(R.layout.fragment_detail_default, container, false);
                name = (EditText) mainView.findViewById(R.id.fragment_detail_name);
                description = (EditText) mainView.findViewById(R.id.fragment_detail_description);
                break;
            case Plot:
                mainView = inflater.inflate(R.layout.fragment_detail_plot, container, false);
                name = (EditText) mainView.findViewById(R.id.fragment_detail_plot_name);
                description = (EditText) mainView.findViewById(R.id.fragment_detail_plot_description);
                break;
            default:
                mainView = inflater.inflate(R.layout.fragment_detail_world, container, false);
                name = (EditText) mainView.findViewById(R.id.fragment_detail_world_name);
                description = (EditText) mainView.findViewById(R.id.fragment_detail_world_description);
                break;
        }

        swapEdit();
        return mainView;
    }

    private void swapEdit()
    {
        if (isEditing)
        {
            Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.edit_text);
            name.setBackground(editBackground);
            description.setBackground(editBackground);

            if (typeToDisplay == ImportanceRelation.ImportantType.Character) {
                nickname.setBackground(editBackground);
                gender.setBackground(editBackground);
                age.setBackground(editBackground);
            }
        }
        else
        {
            int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
            ColorDrawable background = new ColorDrawable(transparent);
            name.setBackground(background);
            description.setBackground(background);

            if (typeToDisplay == ImportanceRelation.ImportantType.Character) {
                nickname.setBackground(background);
                gender.setBackground(background);
                age.setBackground(background);
            }
        }

        name.setFocusable(isEditing);
        name.setClickable(isEditing);
        name.setFocusableInTouchMode(isEditing);
        name.setLongClickable(isEditing);
        name.invalidate();
        name.requestLayout();
        description.setFocusable(isEditing);
        description.setClickable(isEditing);
        description.setFocusableInTouchMode(isEditing);
        description.setLongClickable(isEditing);
        description.invalidate();
        description.requestLayout();

        if (typeToDisplay == ImportanceRelation.ImportantType.Character)
        {
            nickname.setFocusable(isEditing);
            nickname.setClickable(isEditing);
            nickname.setFocusableInTouchMode(isEditing);
            nickname.setLongClickable(isEditing);
            nickname.invalidate();
            nickname.requestLayout();
            gender.setFocusable(isEditing);
            gender.setClickable(isEditing);
            gender.setFocusableInTouchMode(isEditing);
            gender.setLongClickable(isEditing);
            gender.invalidate();
            gender.requestLayout();
            age.setFocusable(isEditing);
            age.setClickable(isEditing);
            age.setFocusableInTouchMode(isEditing);
            age.setLongClickable(isEditing);
            age.invalidate();
            age.requestLayout();
        }
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void iconAction()
    {
        if (isEditing)
        {
            saveMode();
        }
        else
        {
            editMode();
        }
    }

    private void saveMode()
    {
        isEditing = false;
        swapEdit();
    }

    private void editMode()
    {
        isEditing = true;
        swapEdit();
    }
}
