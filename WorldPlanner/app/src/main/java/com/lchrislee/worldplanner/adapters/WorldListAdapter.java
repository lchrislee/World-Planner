package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryWorld;

import timber.log.Timber;

/**
 * Created by chrisl on 3/27/17.
 */

public class WorldListAdapter extends RecyclerView.Adapter<WorldListAdapter.WorldViewHolder> {

    public interface WorldSwitch{
        void onWorldSwitch(long position);
    }

    class WorldViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView description;

        WorldViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long index = (Integer) v.getTag();
                    Timber.tag(getClass().getSimpleName()).d("Changing world to %d", index + 1);
                    DataManager.getInstance().changeWorldToIndex(index);
                    listener.onWorldSwitch(index);
                }
            });
        }
    }

    private Context context;
    private WorldSwitch listener;

    public WorldListAdapter(Context context, WorldSwitch l) {
        this.context = context;
        listener = l;
    }

    @Override
    public WorldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorldViewHolder(LayoutInflater.from(context).inflate(R.layout.list_world, parent, false));
    }

    @Override
    public void onBindViewHolder(WorldViewHolder holder, int position) {
        StoryWorld world = DataManager.getInstance().getWorldAtIndex(position);

        if (world != null) {
            holder.name.setText(world.getName());
            holder.description.setText(world.getDescription());
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        int worldCount = (int) DataManager.getInstance().getCountForWorlds();
        Timber.tag(getClass().getSimpleName()).d("There are %d worlds", worldCount);

        return worldCount;
    }
}
