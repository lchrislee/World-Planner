package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.WorldEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter subclass to handle displaying {@link WorldEntity}.
 */
public class WorldEntityAdapter extends RecyclerView.Adapter<WorldEntityAdapter.WorldEntityViewHolder>
{
    private static final String LOG_TAG = WorldEntityAdapter.class.getSimpleName();

    /**
     * This adapter does not know how to handle when a list item inside is clicked. Let someone else
     * take care of it.
     */
    public interface EntityItemClickDelegate {

        /**
         * Handle when an entity is clicked.
         *
         * @param entityId The id of the entity.
         */
        void onEntityClicked(final long entityId);
    }

    private List<WorldEntity> entities;

    private Context mContext;

    private EntityItemClickDelegate mDelegate;

    public WorldEntityAdapter (@NonNull final Context context,
                               @NonNull final EntityItemClickDelegate delegate)
    {
        mContext = context;
        mDelegate = delegate;
        entities = new ArrayList<>();
    }

    @Override
    public WorldEntityViewHolder onCreateViewHolder (@NonNull final ViewGroup parent, final int viewType)
    {
        return new WorldEntityViewHolder(LayoutInflater.from(mContext)
            .inflate(R.layout.list_entity_small, parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull final WorldEntityViewHolder holder, final int position)
    {
        final int actualPosition = holder.getAdapterPosition();
        final WorldEntity entity = entities.get(actualPosition);
        // TODO 1/14/18: Figure out how to do visuals.
        holder.image.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        holder.name.setText(entity.abbreviatedName());
        holder.itemView.setTag(actualPosition);
        holder.itemView.setOnClickListener(view -> {
            final WorldEntity clickedEntity = entities.get((Integer) view.getTag());
            // TODO 1/14/18: Update this once persistence is completed.
            mDelegate.onEntityClicked(0);
        });
    }

    @Override
    public int getItemCount ()
    {
        return entities.size();
    }

    /**
     * Replace the existing list.
     *
     * @param newList The new list for displaying.
     */
    public void use(@NonNull final List<WorldEntity> newList) {
        entities = newList;
        notifyDataSetChanged();
    }

    class WorldEntityViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;

        WorldEntityViewHolder (@NonNull final View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.list_entity_image);
            name = itemView.findViewById(R.id.list_entity_name);
        }
    }
}
