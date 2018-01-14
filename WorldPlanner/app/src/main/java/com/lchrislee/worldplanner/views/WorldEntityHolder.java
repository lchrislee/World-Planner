package com.lchrislee.worldplanner.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;

/**
 * A custom view that encapsulates the set of views for each category of entity in a {@link com.lchrislee.worldplanner.models.World}.
 */
public class WorldEntityHolder extends FrameLayout
{
    private static final String LOG_TAG = WorldEntityHolder.class.getSimpleName();

    /**
     * This view does not know about how to creating new entities. Allow someone else to handle it.
     */
    public interface AddEntityDelegate {

        /**
         * Let the delegate decide how to add this type of entity.
         *
         * @param entityHolderId This view's id.
         */
        void onAddEntity(final int entityHolderId);
    }

    private RecyclerView entityList;

    private AddEntityDelegate mDelegate;

    public WorldEntityHolder (Context context)
    {
        this(context, null);
    }

    public WorldEntityHolder (Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public WorldEntityHolder (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    /**
     *
     * @param attrs The attributes added with
     */
    private void initialize(@Nullable final AttributeSet attrs) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_world_entities, this, true);
        entityList = findViewById(R.id.list_world_entity_list);

        final TypedArray viewAttributes = getContext().getTheme()
            .obtainStyledAttributes(attrs, R.styleable.WorldEntityHolder, 0, 0);

        final TextView entityName = findViewById(R.id.list_world_entity_name);
        if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_title))
        {
            entityName.setText(viewAttributes.getString(R.styleable.WorldEntityHolder_title));
        }
        else if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_titleRes))
        {
            entityName.setText(viewAttributes.getResourceId(R.styleable.WorldEntityHolder_titleRes, 0));
        }

        final ImageView addEntity = findViewById(R.id.list_world_entity_add);
        if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_srcRes))
        {
            addEntity.setImageDrawable(viewAttributes.getDrawable(R.styleable.WorldEntityHolder_srcRes));
            addEntity.setOnClickListener(this::onAddClicked);
        } else {
            addEntity.setVisibility(View.GONE);
        }
    }

    /**
     * Assign some delegate to handle requests to add entities of this type.
     *
     * @param addEntityDelegate The delegate to handle adding entities.
     */
    public void setAddEntityDelegate(@NonNull final AddEntityDelegate addEntityDelegate) {
        mDelegate = addEntityDelegate;
    }

    /**
     * Process how to react to a user wanting to add more of this entity type.
     *
     * @param viewClicked The view that launched this event. Unused but necessary for lambda.
     */
    @SuppressWarnings("unused")
    private void onAddClicked(@NonNull final View viewClicked) {
        if (mDelegate != null) {
            mDelegate.onAddEntity(getId());
        }
    }

    /**
     * Refresh current list of entities.
     */
    public void refreshList() {
        // TODO 1/14/18: Fill this in with the proper RecyclerView.Adapter calls.
    }
}
