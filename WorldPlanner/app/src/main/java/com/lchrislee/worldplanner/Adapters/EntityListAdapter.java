package com.lchrislee.worldplanner.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.Models.Character;
import com.lchrislee.worldplanner.Models.Entity;
import com.lchrislee.worldplanner.Models.Item;
import com.lchrislee.worldplanner.Models.Location;
import com.lchrislee.worldplanner.Models.Plot;
import com.lchrislee.worldplanner.R;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/27/17.
 */

public class EntityListAdapter extends RecyclerView.Adapter<EntityListAdapter.EntityListViewHolder> {

    class EntityListViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView description;
        public ImageView image;

        public TextView gender_age;
        public TextView occupation;

        EntityListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            gender_age = (TextView) itemView.findViewById(R.id.list_character_age_gender);
            occupation = (TextView) itemView.findViewById(R.id.list_character_occupation);
        }
    }

    ArrayList<Entity> data;

    private Context context;

    private Entity.EntityType typeDisplaying;

    public EntityListAdapter(Entity.EntityType type, Context c) {
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
        return new EntityListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntityListViewHolder holder, int position) {
        Entity ent = data.get(position);

        if (holder.description != null)
        {
            holder.description.setText(ent.getDescription());
        }

        if (typeDisplaying == Entity.EntityType.Character)
        {
            Character proper = (Character) ent;
            holder.name.setText(proper.getNickname() + " (" + proper.getName() + ")");
            holder.gender_age.setText("Age " + proper.getAge() + ", " + proper.getGender());
            holder.occupation.setText(proper.getOccupation());
        }
        else
        {
            holder.name.setText(ent.getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}