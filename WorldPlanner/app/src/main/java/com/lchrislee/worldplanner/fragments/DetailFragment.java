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

import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

import timber.log.Timber;

public class DetailFragment extends WorldPlannerBaseFragment implements ToolbarSupportingFragment {

    protected static final String RELATION_TYPE = "DETAIL_FRAGMENT_RELATION_TYPE";
    protected static final String EDIT = "DETAIL_FRAGMENT__EDIT";
    protected static final String INDEX = "DETAIL_FRAGMENT_INDEX";

    protected View mainView;
    protected EditText name;
    protected EditText description;
    protected ImageView image;

    protected ImportanceRelation.ImportantType typeToDisplay;
    protected boolean isEditing;
    protected int index;

    protected WorldPlannerBaseModel model;

    public DetailFragment() {
        super();
        // Required empty public constructor
    }

    public static DetailFragment newInstance(ImportanceRelation.ImportantType type,
                                             boolean edit,
                                             int indexOfData
                                             ) {
        DetailFragment fragment = new DetailFragment();
        Bundle b = new Bundle();
        b.putSerializable(RELATION_TYPE, type);
        b.putBoolean(EDIT, edit);
        b.putInt(INDEX, indexOfData);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        typeToDisplay = (ImportanceRelation.ImportantType) arguments.getSerializable(RELATION_TYPE);
        isEditing = arguments.getBoolean(EDIT, false);
        index = arguments.getInt(INDEX, -1);
        model = DataManager.getInstance().getAtIndexWithType(index, typeToDisplay);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        if (model != null)
        {
            name.setText(model.getName());
            description.setText(model.getDescription());
        }
        else {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            switch (typeToDisplay) {
                case Character:
                    model = new StoryCharacter("", "");
                    break;
                case Location:
                    model = new StoryLocation("", "", currentWorld);
                    break;
                case Item:
                    model = new StoryItem("", "", currentWorld);
                    break;
                case Plot:
                    model = new StoryPlot("", "", currentWorld);
                    break;
                case None:
                    model = new StoryWorld("", "");
                    break;
            }
        }
        swapEdit();
        return mainView;
    }

    protected void swapEdit()
    {
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

        model.setName(name.getText().toString());
        model.setDescription(description.getText().toString());

        Timber.tag(getClass().getSimpleName()).d("isEdit - " + isEditing);
        if (!isEditing)
        {
            if (index == -1)
            {
                index = DataManager.getInstance().add(model, typeToDisplay);
                Timber.tag(getClass().getSimpleName()).d("saved to index- " + index);
            }
            else
            {
                boolean success = DataManager.getInstance().update(model, index, typeToDisplay);
                Timber.tag(getClass().getSimpleName()).d("updated success - " + success);
            }
        }

        mainView.requestLayout();
    }

    @Override
    public void editAction()
    {
        Timber.tag(getClass().getSimpleName()).d("editAction");
        isEditing = !isEditing;
        swapEdit();
    }

    @NonNull
    @Override
    public WorldPlannerBaseModel getModel() {
        return model;
    }

}
