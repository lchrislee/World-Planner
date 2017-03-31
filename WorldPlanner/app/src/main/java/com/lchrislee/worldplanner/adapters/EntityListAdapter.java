package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.activities.CurrentWorldActivity;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.views.SimpleDetailView;

import java.io.Serializable;

import timber.log.Timber;

/**
 * Created by chrisl on 3/27/17.
 */

public class EntityListAdapter extends RecyclerView.Adapter<EntityListAdapter.EntityListViewHolder> {

    class EntityListViewHolder extends RecyclerView.ViewHolder
    {
        final TextView name;
        final TextView description;
        final ImageView image;

        final TextView trueName;
        final TextView gender_age;
        final SimpleDetailView details;

        EntityListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_entity_name);
            description = (TextView) itemView.findViewById(R.id.list_entity_description);
            image = (ImageView) itemView.findViewById(R.id.list_entity_image);
            gender_age = (TextView) itemView.findViewById(R.id.list_character_age_gender);
            trueName = (TextView) itemView.findViewById(R.id.list_character_true_name);
            details = (SimpleDetailView) itemView.findViewById(R.id.list_item_simple);
        }
    }

    private final Context context;

    private final int typeDisplaying;

    public EntityListAdapter(int type, Context c) {
        typeDisplaying = type;
        context = c;
    }

    @Override
    public EntityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch(typeDisplaying)
        {
            case DataManager.CODE_CHARACTER:
                layout = R.layout.list_character;
                break;
            case DataManager.CODE_LOCATION:
                layout = R.layout.list_location;
                break;
            case DataManager.CODE_ITEM:
                layout = R.layout.list_item;
                break;
            default: // StoryPlot
                layout = R.layout.list_plot;
                break;
        }

        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, typeDisplaying);
                i.putExtra(EntityDetailActivity.INDEX, (Integer) v.getTag());

                ((CurrentWorldActivity) context).startActivityForResult(i, typeDisplaying);
            }
        });
        return new EntityListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntityListViewHolder holder, int position) {
        StoryElement obj = null;

        switch(typeDisplaying)
        {
            case DataManager.CODE_CHARACTER:
                obj = DataManager.getInstance().getCharacterAtIndex(position);
                break;
            case DataManager.CODE_LOCATION:
                obj = DataManager.getInstance().getLocationAtIndex(position);
                break;
            case DataManager.CODE_ITEM:
                obj = DataManager.getInstance().getItemAtIndex(position);
                break;
            case DataManager.CODE_PLOT:
                obj = DataManager.getInstance().getPlotAtIndex(position);
                break;
            case DataManager.CODE_WORLD:
                obj = DataManager.getInstance().getWorldAtIndex(position);
                break;
        }

        holder.itemView.setTag(position);

        if (typeDisplaying == DataManager.CODE_ITEM && obj != null)
        {
            holder.details.setName(obj.getName());
            holder.details.setDescription(obj.getDescription());
            return;
        }

        if (holder.description != null && obj != null)
        {
            holder.description.setText(obj.getDescription());
        }

        if (typeDisplaying == DataManager.CODE_CHARACTER && obj != null)
        {
            StoryCharacter proper = (StoryCharacter) obj;
            holder.name.setText(proper.getNickname());
            holder.trueName.setText(proper.getName());
            holder.gender_age.setText("Age " + proper.getAge() + ", " + proper.getGender());
            if (holder.description != null) {
                holder.description.setText(proper.getDescription());
            }
        }
        else if (obj != null)
        {
            holder.name.setText(obj.getName());
        }
    }

    @Override
    public int getItemCount() {
        long size = 0;
        switch(typeDisplaying)
        {
            case DataManager.CODE_CHARACTER:
                size = DataManager.getInstance().getCountForCharacters();
                break;
            case DataManager.CODE_LOCATION:
                size = DataManager.getInstance().getCountForLocations();
                break;
            case DataManager.CODE_ITEM:
                size = DataManager.getInstance().getCountForItems();
                break;
            case DataManager.CODE_PLOT:
                size = DataManager.getInstance().getCountForPlots();
                break;
            case DataManager.CODE_WORLD:
                size = DataManager.getInstance().getCountForWorlds();
                break;
        }
        return (int) size;
    }

}