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
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.views.SimpleDetailView;

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

    private int REQUEST_CODE_TO_SEND;
    private final ImportanceRelation.ImportantType typeDisplaying;

    public EntityListAdapter(ImportanceRelation.ImportantType type, Context c) {
        typeDisplaying = type;
        context = c;
    }

    @Override
    public EntityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch(typeDisplaying)
        {
            case Character:
                layout = R.layout.list_character;
                REQUEST_CODE_TO_SEND = EntityDetailActivity.REQUEST_CODE_CHARACTER;
                break;
            case Location:
                layout = R.layout.list_location;
                REQUEST_CODE_TO_SEND = EntityDetailActivity.REQUEST_CODE_LOCATION;
                break;
            case Item:
                layout = R.layout.list_item;
                REQUEST_CODE_TO_SEND = EntityDetailActivity.REQUEST_CODE_ITEM;
                break;
            default: // StoryPlot
                layout = R.layout.list_plot;
                REQUEST_CODE_TO_SEND = EntityDetailActivity.REQUEST_CODE_PLOT;
                break;
        }

        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, REQUEST_CODE_TO_SEND);
                i.putExtra(EntityDetailActivity.INDEX, (Integer) v.getTag());

                ((CurrentWorldActivity) context).startActivityForResult(i, REQUEST_CODE_TO_SEND);
            }
        });
        return new EntityListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntityListViewHolder holder, int position) {
        WorldPlannerBaseModel obj;

        final DataManager manager = DataManager.getInstance();
        obj = manager.getAtIndexWithType(position, typeDisplaying);

        holder.itemView.setTag(position);

        if (typeDisplaying == ImportanceRelation.ImportantType.Item && obj != null)
        {
            holder.details.setName(obj.getName());
            holder.details.setDescription(obj.getDescription());
            return;
        }

        if (holder.description != null && obj != null)
        {
            holder.description.setText(obj.getDescription());
        }

        if (typeDisplaying == ImportanceRelation.ImportantType.Character && obj != null)
        {
            StoryCharacter proper = (StoryCharacter) obj;
            holder.name.setText(proper.getNickname());
            holder.trueName.setText(proper.getName());
            holder.gender_age.setText("Age " + proper.getAge() + ", " + proper.getGender());
            if (holder.description != null) {
                String occupation = proper.getOccupation();
                holder.description.setText(occupation == null ? "" : occupation);
            }
        }
        else if (obj != null)
        {
            holder.name.setText(obj.getName());
        }
    }

    @Override
    public int getItemCount() {
        int size = DataManager.getInstance().getCountForType(typeDisplaying);
        Timber.tag(getClass().getSimpleName()).d("size is: " + size + " for type - " + typeDisplaying.name());
        return size;
    }

}