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
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.Item;
import com.lchrislee.worldplanner.models.Location;
import com.lchrislee.worldplanner.models.Plot;
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.views.SimpleDetailView;

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
        final SimpleDetailView details;

        EntityListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            gender_age = (TextView) itemView.findViewById(R.id.list_character_age_gender);
            occupation = (TextView) itemView.findViewById(R.id.list_character_occupation);
            details = (SimpleDetailView) itemView.findViewById(R.id.list_item_simple);
        }
    }

    private ArrayList<WorldPlannerBaseModel> data;

    private final Context context;

    private final ImportanceRelation.ImportantType typeDisplaying;

    public MasterPlannerObjectListAdapter(ImportanceRelation.ImportantType type, Context c) {
        typeDisplaying = type;
        context = c;
        data = new ArrayList<>();
        int randomAmount = (int)(Math.random() * 10) + 2;
        for (int i = 0; i < randomAmount; ++i)
        {
            switch(typeDisplaying)
            {
                case Character:
                    StoryCharacter storyCharacter = new StoryCharacter("Peter Parker", "Just a poor boy from a poor family");
                    storyCharacter.setAge(20);
                    storyCharacter.setGender("Male");
                    storyCharacter.setNickname("Spiderman");
                    storyCharacter.setOccupation("Superhero");
                    data.add(storyCharacter);
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
                    ((MasterActivity) context).startActivityForResult(i, ModelDetailActivity.REQUEST_CODE_RELATIONABLE_DETAIL);
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

        if (typeDisplaying == ImportanceRelation.ImportantType.Item)
        {
            holder.details.setName(obj.getName());
            holder.details.setDescription(obj.getDescription());
            return;
        }

        if (holder.description != null)
        {
            holder.description.setText(obj.getDescription());
        }

        if (typeDisplaying == ImportanceRelation.ImportantType.Character)
        {
            StoryCharacter proper = (StoryCharacter) obj;
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