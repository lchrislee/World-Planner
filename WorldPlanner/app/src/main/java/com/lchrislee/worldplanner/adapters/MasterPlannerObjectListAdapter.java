package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.activities.MasterActivity;
import com.lchrislee.worldplanner.activities.ModelDetailActivity;
import com.lchrislee.worldplanner.models.Character;
import com.lchrislee.worldplanner.models.Item;
import com.lchrislee.worldplanner.models.Location;
import com.lchrislee.worldplanner.models.Plot;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/27/17.
 */

public class MasterPlannerObjectListAdapter extends RecyclerView.Adapter<MasterPlannerObjectListAdapter.EntityListViewHolder> {

    class EntityListViewHolder extends RecyclerView.ViewHolder
    {
        final TextView name;
        final TextView description;
        final ImageView image;

        final TextView gender_age;
        final TextView occupation;

        EntityListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            gender_age = (TextView) itemView.findViewById(R.id.list_character_age_gender);
            occupation = (TextView) itemView.findViewById(R.id.list_character_occupation);
        }
    }

    private ArrayList<WorldPlannerBaseModel> data;

    private final Context context;

    private final Relationship.RelationableType typeDisplaying;

    public MasterPlannerObjectListAdapter(Relationship.RelationableType type, Context c) {
        typeDisplaying = type;
        context = c;
        data = new ArrayList<>();
        for (int i = 0; i < Math.random() * 10 + 2; ++i)
        {
            switch(typeDisplaying)
            {
                case Character:
                    Character character = new Character("Peter Parker", "Just a poor boy from a poor family");
                    character.setAge(20);
                    character.setGender("Male");
                    character.setNickname("Spiderman");
                    character.setOccupation("Superhero");
                    data.add(character);
                    break;
                case Location:
                    Location loc = new Location("Parker Household", "You won't see this, probably");
                    data.add(loc);
                    break;
                case Item:
                    Item item = new Item("Web Canister", "Peter Parker's Source of webbing, easily swappable.");
                    data.add(item);
                    break;
                default:
                    Plot plot = new Plot("Uncle Ben Dies", "Peter learns the meaning of Uncle Ben's message on responsibility");
                    data.add(plot);
                    break;
            }
        }
    }

    @Override
    public EntityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch(typeDisplaying)
        {
            case Character:
                layout = R.layout.list_character;
                break;
            case Location:
                layout = R.layout.list_location;
                break;
            case Item:
                layout = R.layout.list_item;
                break;
            default: // Plot
                layout = R.layout.list_plot;
        }

        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ModelDetailActivity.class);
                i.putExtra(ModelDetailActivity.TYPE, typeDisplaying);

                if (context instanceof MasterActivity) {
                    ((MasterActivity) context).startActivityForResult(i, MasterActivity.RELATIONABLE_DETAIL_CODE);
                }
                else
                {
                    context.startActivity(i);
                }
            }
        });
        return new EntityListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntityListViewHolder holder, int position) {
        WorldPlannerBaseModel obj = data.get(position);

        holder.itemView.setTag(obj);
        if (holder.description != null)
        {
            holder.description.setText(obj.getDescription());
        }

        if (typeDisplaying == Relationship.RelationableType.Character)
        {
            Character proper = (Character) obj;
            holder.name.setText(proper.getNickname() + " (" + proper.getName() + ")");
            holder.gender_age.setText("Age " + proper.getAge() + ", " + proper.getGender());
            holder.occupation.setText(proper.getOccupation());
        }
        else
        {
            holder.name.setText(obj.getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}