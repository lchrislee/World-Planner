package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.World;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/27/17.
 */

public class WorldListAdapter extends RecyclerView.Adapter<WorldListAdapter.WorldViewHolder> {

    public interface WorldSwitch{
        void onWorldSwitch(int position);
    }

    class WorldViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        TextView description;
        Button change;

        WorldViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            change = (Button) itemView.findViewById(R.id.list_world_change);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onWorldSwitch((Integer)v.getTag());
                }
            });
        }
    }

    private Context context;
    private WorldSwitch listener;
    private ArrayList<World> worlds;

    public WorldListAdapter(Context context, WorldSwitch l) {
        this.context = context;
        listener = l;
        worlds = new ArrayList<>();
        int randomAmount = (int)(Math.random() * 10) + 2;
        for (int i = 0; i < randomAmount; ++i)
        {
            worlds.add(new World("Earth 616", "Some lengthy description of something or other that I honestly don't know anything about because it is too much of a hassle to come up with something long enough to extend past this stupid bare minimum barrier."));
        }
    }

    @Override
    public WorldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorldViewHolder(LayoutInflater.from(context).inflate(R.layout.list_world, parent, false));
    }

    @Override
    public void onBindViewHolder(WorldViewHolder holder, int position) {
        World w = worlds.get(position);

        holder.name.setText(w.getName());
        holder.description.setText(w.getDescription());
        holder.change.setTag(position);
    }

    @Override
    public int getItemCount() {
        return worlds.size();
    }
}
