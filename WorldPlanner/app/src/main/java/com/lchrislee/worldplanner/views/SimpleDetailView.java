package com.lchrislee.worldplanner.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

/**
 * Created by chrisl on 3/27/17.
 */

public class SimpleDetailView extends RelativeLayout {

    private ImageView image;
    private TextView name;
    private TextView description;

    public SimpleDetailView(Context context) {
        super(context);
        init();
    }

    public SimpleDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SimpleDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_simple_detail, this, true);

        image = (ImageView) findViewById(R.id.layout_simple_detail_image);
        name = (TextView) findViewById(R.id.layout_simple_detail_name);
        description = (TextView) findViewById(R.id.layout_simple_detail_description);
    }

    public void setName(@NonNull String n)
    {
        name.setText(n);
        invalidate();
        requestLayout();
    }

    public void setDescription(@NonNull String d)
    {
        description.setText(d);
        invalidate();
        requestLayout();
    }

    public void setImage(Drawable d)
    {
        image.setImageDrawable(d);
        invalidate();
        requestLayout();
    }

    public void setImage(Bitmap b)
    {
        image.setImageBitmap(b);
        invalidate();
        requestLayout();
    }

}
