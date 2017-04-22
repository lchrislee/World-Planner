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
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.orm.SugarRecord;

import java.io.Serializable;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public abstract class DetailFragment
        extends WorldPlannerBaseFragment
        implements ToolbarSupportingFragment
{

    protected static final String ENTITY_TYPE = "DETAIL_FRAGMENT_ENTITY_TYPE";
    protected static final String DATA = "DETAIL_FRAGMENT_DATA";
    protected static final String LAYOUT = "DETAIL_FRAGMENT_LAYOUT";

    private View mainView;
    private EditText name;
    private EditText description;
    private ImageView image;

    private int layoutId;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        typeToDisplay = arguments.getInt(ENTITY_TYPE);
        model = (StoryElement) arguments.getSerializable(DATA);
        isNew = model == null;
        isEditing = isNew;
        layoutId = arguments.getInt(LAYOUT);
        setupImageListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(layoutId, container, false);

        name = (EditText) mainView.findViewById(R.id.fragment_detail_name);
        description = (EditText) mainView.findViewById(R.id.fragment_detail_description);
        image = (ImageView) mainView.findViewById(R.id.fragment_detail_image);

        if (model != null)
        {
            name.setText(model.getName());
            description.setText(model.getDescription());
        }
        else {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            switch (typeToDisplay) {
                case DataManager.PLOT:
                    StoryPlot plot = new StoryPlot();
                    plot.setWorld(currentWorld);
                    model = plot;
                    model.setName("");
                    model.setDescription("");
                    break;
            }
        }
        updateViews();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
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

    protected void updateViews()
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
                else
                {
                    image.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_camera));
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
                else if (model.getImage().length() == 0)
                {
                    image.setImageDrawable(null);
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
        updateViews();
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

        return ((SugarRecord) model).getId();
    }

    public void stopEditing()
    {
        isEditing = false;
        updateViews();
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
                else
                {
                    requestCameraPermissions();
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
                updateViews();
                CameraManager.getInstance().requestImageCapture(DetailFragment.this, typeToDisplay);
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
