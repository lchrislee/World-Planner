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
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;

import timber.log.Timber;

public class DetailFragment extends WorldPlannerBaseFragment implements ToolbarSupportingFragment {

    protected static final String RELATION_TYPE = "DETAIL_FRAGMENT_RELATION_TYPE";
    protected static final String EDIT = "DETAIL_FRAGMENT_EDIT";
    protected static final String INDEX = "DETAIL_FRAGMENT_INDEX";

    protected View mainView;
    protected EditText name;
    protected EditText description;
    protected ImageView image;

    protected int typeToDisplay;
    protected boolean isEditing;
    protected long index;

    protected StoryElement model;

    public DetailFragment() {
        super();
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int type,
                                             boolean edit,
                                             long indexOfData
                                             ) {
        DetailFragment fragment = new DetailFragment();
        Bundle b = new Bundle();
        b.putInt(RELATION_TYPE, type);
        b.putBoolean(EDIT, edit);
        b.putLong(INDEX, indexOfData);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        typeToDisplay = arguments.getInt(RELATION_TYPE);
        isEditing = arguments.getBoolean(EDIT, false);
        index = arguments.getLong(INDEX, -1);
        switch(typeToDisplay)
        {
            case DataManager.CODE_CHARACTER:
                model = DataManager.getInstance().getCharacterAtIndex(index);
                break;
            case DataManager.CODE_LOCATION:
                model = DataManager.getInstance().getLocationAtIndex(index);
                break;
            case DataManager.CODE_ITEM:
                model = DataManager.getInstance().getItemAtIndex(index);
                break;
            case DataManager.CODE_PLOT:
                model = DataManager.getInstance().getPlotAtIndex(index);
                break;
            case DataManager.CODE_WORLD:
                model = DataManager.getInstance().getWorldAtIndex(index);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (typeToDisplay)
        {
            case DataManager.CODE_CHARACTER: // Propogate to CharacterDetailFragment.
                mainView = inflater.inflate(R.layout.fragment_detail_character, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
                break;
            case DataManager.CODE_ITEM:
            case DataManager.CODE_LOCATION:
                mainView = inflater.inflate(R.layout.fragment_detail_default, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
                break;
            case DataManager.CODE_PLOT:
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
                case DataManager.CODE_CHARACTER:
                    StoryCharacter character = new StoryCharacter();
                    character.setName("");
                    character.setDescription("");
                    character.setWorld(currentWorld);
                    model = character;
                    break;
                case DataManager.CODE_LOCATION:
                    StoryLocation location = new StoryLocation();
                    location.setWorld(currentWorld);
                    location.setName("");
                    location.setDescription("");
                    model = location;
                    break;
                case DataManager.CODE_ITEM:
                    StoryItem item = new StoryItem();
                    item.setWorld(currentWorld);
                    item.setName("");
                    item.setDescription("");
                    model = item;
                    break;
                case DataManager.CODE_PLOT:
                    StoryPlot plot = new StoryPlot();
                    plot.setWorld(currentWorld);
                    plot.setName("");
                    plot.setDescription("");
                    model = plot;
                    break;
                case DataManager.CODE_WORLD:
                    StoryWorld world = new StoryWorld();
                    world.setName("");
                    world.setDescription("");
                    model = world;
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

        mainView.requestLayout();
    }

    @Override
    public void editAction()
    {
        isEditing = !isEditing;
        swapEdit();
        if (!isEditing)
        {
            if (index == -1)
            {
                index = DataManager.getInstance().add(model);
            }
            else
            {
                DataManager.getInstance().update(model, index);
            }
        }
    }

    @NonNull
    @Override
    public StoryElement getModel() {
        return model;
    }

}
