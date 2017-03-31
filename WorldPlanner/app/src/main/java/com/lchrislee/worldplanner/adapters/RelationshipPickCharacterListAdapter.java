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

import java.util.List;

/**
 * Created by chrisl on 3/28/17.
 */

public class RelationshipPickCharacterListAdapter extends RecyclerView.Adapter<RelationshipPickCharacterListAdapter.DefaultPlannerObjectViewHolder> {

    public interface DefaultPlannerObjectSelected{
        void onItemSelected(long position);
    }

    class DefaultPlannerObjectViewHolder extends RecyclerView.ViewHolder{

        final ImageView image;
        final TextView name;

        DefaultPlannerObjectViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelected((long)v.getTag());
                }
            });
        }
    }

    private final Context context;

    private final DefaultPlannerObjectSelected listener;
    private final List<StoryCharacter> charactersToShow;

    public RelationshipPickCharacterListAdapter(Context context, DefaultPlannerObjectSelected l, List<StoryCharacter> show) {
        this.context = context;
        listener = l;
        charactersToShow = show;
    }

    @Override
    public DefaultPlannerObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultPlannerObjectViewHolder(LayoutInflater.from(context).inflate(R.layout.list_default, parent, false));
    }

    @Override
    public void onBindViewHolder(DefaultPlannerObjectViewHolder holder, int position) {
        StoryCharacter character = charactersToShow.get(position);
        holder.name.setText(character.getName());
        holder.itemView.setTag((long) position);
    }

    @Override
    public int getItemCount() {
        return charactersToShow.size();
    }
}
