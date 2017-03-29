package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryCharacter;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/28/17.
 */

public class RelationCharacterListAdapter extends RecyclerView.Adapter<RelationCharacterListAdapter.DefaultPlannerObjectViewHolder> {

    public interface DefaultPlannerObjectSelected{
        void onItemSelected(int position);
    }

    class DefaultPlannerObjectViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public DefaultPlannerObjectViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelected((Integer)v.getTag());
                }
            });
        }
    }

    private Context context;

    private ArrayList<StoryCharacter> characters;

    private DefaultPlannerObjectSelected listener;

    public RelationCharacterListAdapter(Context context, DefaultPlannerObjectSelected l) {
        this.context = context;
        listener = l;
        characters = new ArrayList<>();
        int randomAmount = (int)(Math.random() * 10) + 2;
        for (int i = 0; i < randomAmount; ++i)
        {
            characters.add(new StoryCharacter(String.valueOf(i), ""));
        }
    }

    @Override
    public DefaultPlannerObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultPlannerObjectViewHolder(LayoutInflater.from(context).inflate(R.layout.list_default, parent, false));
    }

    @Override
    public void onBindViewHolder(DefaultPlannerObjectViewHolder holder, int position) {
        holder.name.setText(characters.get(position).getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
}
