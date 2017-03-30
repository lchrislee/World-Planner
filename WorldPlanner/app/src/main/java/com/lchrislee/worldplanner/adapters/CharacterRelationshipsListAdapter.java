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
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryRelationship;
import com.lchrislee.worldplanner.views.SimpleDetailView;

/**
 * Created by chrisl on 3/27/17.
 */

public class CharacterRelationshipsListAdapter extends RecyclerView.Adapter<CharacterRelationshipsListAdapter.CharacterRelationViewHolder> {

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
                    i.putExtra(RelationDetailActivity.REL_INDEX, (Integer) v.getTag());
                    i.putExtra(RelationDetailActivity.OWNER_INDEX, characterIndex);
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    private Context context;
    private int characterIndex;

    public CharacterRelationshipsListAdapter(Context context, int index) {
        this.context = context;
        characterIndex = index;
    }

    @Override
    public CharacterRelationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CharacterRelationViewHolder(LayoutInflater.from(context).inflate(R.layout.list_character_relation, parent, false));
    }

    @Override
    public void onBindViewHolder(CharacterRelationViewHolder holder, int position) {
        StoryRelationship relationship = DataManager.getInstance().getRelationshipForCharacterAtIndex(characterIndex, position);
        if (relationship != null) {
            holder.details.setName(relationship.getSecondStoryCharacter().getName());
            holder.details.setDescription(relationship.getDescription());
            holder.edit.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getRelationshipCountForCharacter(characterIndex);
    }

}
