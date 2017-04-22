package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.BitmapManager;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryWorld;

public class WorldListAdapter extends RecyclerView.Adapter<WorldListAdapter.WorldViewHolder> {

    public interface WorldSwitch{
        void onWorldSwitch();
    }

    class WorldViewHolder extends RecyclerView.ViewHolder
    {
        final ImageView image;
        final TextView name;
        final TextView description;

        WorldViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long index = (long) v.getTag();
                    DataManager.getInstance().changeWorldToIndex(index);
                    listener.onWorldSwitch();
                }
            });
        }
    }

    private final Context context;
    private final WorldSwitch listener;

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
            String file = world.getImage();
            if (file.length() > 0) {
                Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                        context,
                        file,
                        BitmapManager.ResizeType.LIST_WORLD
                );
                holder.image.setImageBitmap(bitmap);
            }
            else
            {
                holder.image.setImageBitmap(null);
            }
            holder.itemView.setTag((long) position);
        }
    }

    @Override
    public int getItemCount() {
        return (int) DataManager.getInstance().getCountForWorlds();
    }
}
