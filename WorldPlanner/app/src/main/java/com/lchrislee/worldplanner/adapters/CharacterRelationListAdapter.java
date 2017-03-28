package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.Character;
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
        Button delete;

        CharacterRelationViewHolder(View itemView) {
            super(itemView);
            details = (SimpleDetailView) itemView.findViewById(R.id.list_character_relation_simpledetailview);
            edit = (Button) itemView.findViewById(R.id.list_character_relation_edit);
            delete = (Button) itemView.findViewById(R.id.list_character_relation_delete);
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
            Character c = new Character("1C" + i, "");
            Character d = new Character("2C" + i, "");
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
        holder.details.setName(r.getSecondCharacter().getName());
        holder.details.setDescription(r.getDescription());
    }

    @Override
    public int getItemCount() {
        return relationships.size();
    }

}
