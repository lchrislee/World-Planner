package com.lchrislee.worldplanner.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.WorldEntityAdapter;
import com.lchrislee.worldplanner.models.WorldEntity;

import java.util.List;

/**
 * A custom view that encapsulates the set of views for each category of entity in a {@link com.lchrislee.worldplanner.models.World}.
 */
public class WorldEntityHolder extends FrameLayout implements WorldEntityAdapter.EntityItemClickDelegate
{
    private static final String LOG_TAG = WorldEntityHolder.class.getSimpleName();

    /**
     * This view does not know how to handle user interaction. Allow someone else to handle it.
     */
    public interface EntityInteractionDelegate
    {
        /**
         * Let the delegate decide how to add this type of entity.
         *
         * @param entityHolderId This view's id.
         */
        void onAddEntity(final int entityHolderId);

        /**
         * Let the delegate decide how to handle an entity being clicked.
         *
         * @param entityId The id of the clicked entity.
         */
        void onEntityClicked(final long entityId);
    }

    private RecyclerView entityList;

    private WorldEntityAdapter mAdapter;

    private EntityInteractionDelegate mDelegate;

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
        final int horizontalSpacing = initializeViews(attrs);
        mAdapter = new WorldEntityAdapter(getContext(), this);
        initializeList(horizontalSpacing);
    }

    /**
     * Inflate and create references to all internal views. Initial customization.
     *
     * @param attrs The attributes that dictate how to customize this view.
     * @return The horizontal spacing to give the internal list. Default to zero.
     */
    private int initializeViews (@Nullable final AttributeSet attrs) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_world_entities, this, true);
        entityList = findViewById(R.id.list_world_entity_list);

        final TypedArray viewAttributes = getContext().getTheme()
            .obtainStyledAttributes(attrs, R.styleable.WorldEntityHolder, 0, 0);

        // Title text.
        final TextView entityName = findViewById(R.id.list_world_entity_name);
        if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_title))
        {
            entityName.setText(viewAttributes.getString(R.styleable.WorldEntityHolder_title));
        }
        else if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_titleRes))
        {
            entityName.setText(viewAttributes.getResourceId(R.styleable.WorldEntityHolder_titleRes, 0));
        }

        // Add new entity icon.
        final ImageView addEntity = findViewById(R.id.list_world_entity_add);
        if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_srcRes))
        {
            addEntity.setImageDrawable(viewAttributes.getDrawable(R.styleable.WorldEntityHolder_srcRes));
            addEntity.setOnClickListener(this::onAddClicked);
        } else {
            addEntity.setVisibility(View.GONE);
        }

        // List spacing.
        if (viewAttributes.hasValue(R.styleable.WorldEntityHolder_spacing))
        {
            return viewAttributes.getInt(R.styleable.WorldEntityHolder_spacing, 0);
        }
        else
        {
            return getResources().getDimensionPixelSize(
                viewAttributes.getResourceId(R.styleable.WorldEntityHolder_spacingRes, 0));
        }
    }

    /**
     *
     * @param horizontalSpacing The horizontal spacing for the internal list.
     */
    private void initializeList(final int horizontalSpacing) {
        entityList.addItemDecoration(new HorizontalSpacingItemDecoration(horizontalSpacing));
        entityList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        entityList.setAdapter(mAdapter);
    }

    @Override
    public void onEntityClicked (final long entityId)
    {
        if (mDelegate != null) {
            mDelegate.onEntityClicked(entityId);
        }
    }

    /**
     * Assign some delegate to handle requests to add entities of this type.
     *
     * @param entityInteractionDelegate The delegate to handle adding entities.
     */
    public void setAddEntityDelegate(@NonNull final EntityInteractionDelegate entityInteractionDelegate) {
        mDelegate = entityInteractionDelegate;
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
     *
     * @param entities The latest list of entities to display.
     */
    public void useList (@NonNull final List<WorldEntity> entities) {
        mAdapter.use(entities);
    }
}
