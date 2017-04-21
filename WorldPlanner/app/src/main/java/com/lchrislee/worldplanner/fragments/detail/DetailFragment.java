package com.lchrislee.worldplanner.fragments.detail;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.managers.BitmapManager;
import com.lchrislee.worldplanner.managers.CameraManager;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class DetailFragment extends WorldPlannerBaseFragment implements ToolbarSupportingFragment {

    protected static final String RELATION_TYPE = "DETAIL_FRAGMENT_RELATION_TYPE";
    protected static final String DATA = "DETAIL_FRAGMENT_DATA";

    private View mainView;
    private EditText name;
    private EditText description;
    private ImageView image;

    private int typeToDisplay;
    private boolean haveCameraPermissions = false;
    protected boolean isNew;
    protected boolean isEditing;
    protected StoryElement model;
    protected View.OnClickListener imageClickListener;

    public DetailFragment() {
        super();
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int type,
                                             @Nullable Serializable object
                                             ) {
        DetailFragment fragment = new DetailFragment();
        Bundle b = new Bundle();
        b.putInt(RELATION_TYPE, type);
        b.putSerializable(DATA, object);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        typeToDisplay = arguments.getInt(RELATION_TYPE);
        model = (StoryElement) arguments.getSerializable(DATA);
        isNew = model == null;
        isEditing = isNew;
        setupImageListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (typeToDisplay)
        {
            case DataManager.CHARACTER: // Propogate to CharacterDetailFragment.
                mainView = inflater.inflate(R.layout.fragment_detail_character, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
                break;
            case DataManager.ITEM:
            case DataManager.LOCATION:
                mainView = inflater.inflate(R.layout.fragment_detail_default, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
                break;
            case DataManager.PLOT:
                mainView = inflater.inflate(R.layout.fragment_detail_plot, container, false);
                break;
            default: // World
                mainView = inflater.inflate(R.layout.fragment_detail_world, container, false);
                image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);
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
                case DataManager.CHARACTER:
                    StoryCharacter character = new StoryCharacter();
                    character.setWorld(currentWorld);
                    model = character;
                    break;
                case DataManager.LOCATION:
                    StoryLocation location = new StoryLocation();
                    location.setWorld(currentWorld);
                    model = location;
                    break;
                case DataManager.ITEM:
                    StoryItem item = new StoryItem();
                    item.setWorld(currentWorld);
                    model = item;
                    break;
                case DataManager.PLOT:
                    StoryPlot plot = new StoryPlot();
                    plot.setWorld(currentWorld);
                    model = plot;
                    break;
                case DataManager.WORLD:
                    model = new StoryWorld();
                    break;
            }
            model.setName("");
            model.setDescription("");
        }
        swapEdit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isNew) {
                requestCameraPermissions();
            }
        }
        else
        {
            haveCameraPermissions = true;
        }

        return mainView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == CameraManager.REQUEST_CAPTURE_IMAGE)
            {
                String photoPath = CameraManager.getInstance().getLastPath();
                if (model.getImage().length() > 0)
                {
                    CameraManager.getInstance().deleteImage(model.getImage());
                }
                model.setImage(photoPath);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (image != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestCameraPermissions();
            }

            String path = model.getImage();
            if (path.length() > 0)
            {
                Timber.d("image path is: %s", path);
                image.setImageBitmap(BitmapManager.getInstance().loadBitmapFromFile(getContext(), path, BitmapManager.ResizeType.DETAIL));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image.setForeground(null);
                }
            }
        }
    }

    protected void swapEdit()
    {
        if (isEditing)
        {
            Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.edit_text);
            name.setBackground(editBackground);
            description.setBackground(editBackground);
            if (image != null)
            {
                Timber.d("Can click image");
                image.setOnClickListener(imageClickListener);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image.setForeground(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_camera));
                }
            }
        }
        else
        {
            int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
            ColorDrawable background = new ColorDrawable(transparent);
            name.setBackground(background);
            description.setBackground(background);
            if (image != null)
            {
                Timber.d("Can NOT click image");
                image.setOnClickListener(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    image.setForeground(null);
                }
            }
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
    public long editAction()
    {
        isEditing = !isEditing;
        swapEdit();
        if (!isEditing)
        {
            if (isNew)
            {
                isNew = false;
                return DataManager.getInstance().add(model);
            }
            else
            {
                DataManager.getInstance().update(model);
            }
        }

        switch(typeToDisplay)
        {
            case DataManager.CHARACTER:
                return ((StoryCharacter) model).getId();
            case DataManager.LOCATION:
                return ((StoryLocation) model).getId();
            case DataManager.ITEM:
                return ((StoryItem) model).getId();
            case DataManager.PLOT:
                return ((StoryPlot) model).getId();
            case DataManager.WORLD:
                return ((StoryWorld) model).getId();
        }
        return -1;
    }

    @NonNull
    @Override
    public StoryElement getModel() {
        return model;
    }

    protected void setupImageListener()
    {
        imageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveCameraPermissions) {
                    CameraManager.getInstance().requestImageCapture(DetailFragment.this, typeToDisplay);
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CameraManager.REQUEST_CAPTURE_IMAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED)
            {
                haveCameraPermissions = true;
            }
        }
    }

    protected void requestCameraPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(
                        new String[]{
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                            },
                        CameraManager.REQUEST_CAPTURE_IMAGE);
            }
            else
            {
                haveCameraPermissions = true;
            }
        }
    }
}
