package com.lchrislee.worldplanner.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;

import com.lchrislee.worldplanner.R;

import timber.log.Timber;

/**
 * Created by chrisl on 4/21/17.
 */

public class BitmapManager extends WorldPlannerBaseManager {

    public enum ResizeType
    {
        RAW,
        LIST_CHARACTER,
        LIST_LOCATION,
        LIST_ITEM,
        DETAIL
    }

    private static BitmapManager instance;

    private BitmapManager() {

    }

    public static BitmapManager getInstance()
    {
        if (instance == null)
        {
            instance = new BitmapManager();
        }

        return instance;
    }

    @Nullable
    public Bitmap loadBitmapFromFile(@NonNull Context context,
                                     @NonNull String fileName,
                                     @NonNull ResizeType type)
    {
        Bitmap raw = BitmapFactory.decodeFile(fileName);

        int scaleHeight;
        int scaleWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                context.getResources().getDisplayMetrics().widthPixels,
                context.getResources().getDisplayMetrics()
        );
        int heightID = 0;
        switch(type)
        {
            case RAW:
                return raw;
            case LIST_CHARACTER:
                heightID = R.dimen.height_list_character;
                break;
            case LIST_LOCATION:
                heightID = R.dimen.height_list_location;
                break;
            case LIST_ITEM:
                scaleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        context.getResources().getDimension(R.dimen.width_simple_image),
                        context.getResources().getDisplayMetrics());
                heightID = R.dimen.height_simple_image;
                break;
            case DETAIL:
                heightID = R.dimen.height_detail_image;
                break;
        }

        scaleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                context.getResources().getDimension(heightID),
                context.getResources().getDisplayMetrics());

        int offsetX = 0, offsetY = 0;
        if (scaleHeight < raw.getHeight())
        {
            offsetY = (raw.getHeight() - scaleHeight) / 2;
        }
        else
        {
            scaleHeight = raw.getHeight();
        }

        if (scaleWidth < raw.getWidth())
        {
            offsetX = (raw.getWidth() - scaleWidth) / 2;
        }
        else
        {
            scaleWidth = raw.getWidth();
        }

        return Bitmap.createBitmap(raw, offsetX, offsetY, scaleWidth, scaleHeight);
    }
}
