package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.RelationDetailActivity;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.views.SimpleDetailView;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/27/17.
 */

public class CharacterRelationListAdapter extends RecyclerView.Adapter<CharacterRelationListAdapter.CharacterRelationViewHolder> {

    class CharacterRelationViewHolder extends RecyclerView.ViewHolder
    {
        SimpleDetailView details;
        Button edit;

        CharacterRelationViewHolder(View itemView) {
            super(itemView);
            details = (SimpleDetailView) itemView.findViewById(R.id.list_character_relation_simpledetailview);
            edit = (Button) itemView.findViewById(R.id.list_character_relation_more);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), RelationDetailActivity.class);
                    i.putExtra(RelationDetailActivity.RELATIONSHIP, relationships.get((Integer) v.getTag()));
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    private Context context;

    private ArrayList<Relationship> relationships;

    public CharacterRelationListAdapter(Context context) {
        this.context = context;
        relationships = new ArrayList<>();
        int randomNum = (int)(Math.random() * 10) + 2;
        for (int i = 0; i < randomNum; ++i)
        {
            StoryCharacter c = new StoryCharacter("1C" + i, "");
            StoryCharacter d = new StoryCharacter("2C" + i, "");
            relationships.add(new Relationship("Relationship between " + c.getName() + " and " + d.getName(), c, d));
        }
    }

    @Override
    public CharacterRelationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CharacterRelationViewHolder(LayoutInflater.from(context).inflate(R.layout.list_character_relation, parent, false));
    }

    @Override
    public void onBindViewHolder(CharacterRelationViewHolder holder, int position) {
        Relationship r = relationships.get(position);
        holder.details.setName(r.getSecondStoryCharacter().getName());
        holder.details.setDescription(r.getDescription());
        holder.edit.setTag(position);
    }

    @Override
    public int getItemCount() {
        return relationships.size();
    }

}
