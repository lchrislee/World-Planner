package com.lchrislee.worldplanner.managers;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.lchrislee.worldplanner.BuildConfig;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;

import java.io.File;
import java.util.Calendar;

import timber.log.Timber;

/**
 * Created by chrisl on 4/21/17.
 */

public class CameraManager extends WorldPlannerBaseManager {

    public static final int REQUEST_CAPTURE_IMAGE = 2017;
    private final String FILE_PROVIDER_PACKAGE = "com.lchrislee.worldplanner";
    private final String APP_TAG = "WorldPlanner";
    private String lastPath;

    private static CameraManager instance;

    private CameraManager()
    {

    }

    public static CameraManager getInstance()
    {
        if (instance == null)
        {
            instance = new CameraManager();
        }

        return instance;
    }

    public void requestImageCapture(@NonNull WorldPlannerBaseFragment context, int type)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = getPhotoFileUri(context, getNewFileName(type));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        if (cameraIntent.resolveActivity(context.getActivity().getPackageManager()) != null)
        {
            context.startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);
        }
    }

    public void deleteImage(@NonNull String path)
    {
        File f = new File(path);
        f.deleteOnExit();
    }

    public String getLastPath()
    {
        return lastPath;
    }

    private Uri getPhotoFileUri(@NonNull WorldPlannerBaseFragment context, @NonNull String fileName)
    {
        if (isExternalStorageAvailable())
        {
            Timber.d("External storage is available for images.");
            File mediaStorageDir = new File(
                    context.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
            {
                Timber.e("Failed to create directory to store images.");
            }

            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
            Uri uri = FileProvider.getUriForFile(context.getContext(), FILE_PROVIDER_PACKAGE, file);
            Timber.d("Uri is: %s", uri.getPath());
            StringBuilder builder = new StringBuilder("/storage/emulated/0/Android/data/");
            builder.append(FILE_PROVIDER_PACKAGE);
            if (BuildConfig.DEBUG)
            {
                builder.append(".debug");
            }
            builder.append("/files");
            builder.append(uri.getPath());
            lastPath = builder.toString();
            return uri;
        }
        return null;
    }

    private boolean isExternalStorageAvailable()
    {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private String getNewFileName(int type)
    {
        return type + "_" + Calendar.getInstance().getTime().toString();
    }
}
