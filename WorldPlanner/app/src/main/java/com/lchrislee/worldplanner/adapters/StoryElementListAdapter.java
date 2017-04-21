package com.lchrislee.worldplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.viewholders.CharacterViewHolder;
import com.lchrislee.worldplanner.adapters.viewholders.ImageEntityViewHolder;
import com.lchrislee.worldplanner.adapters.viewholders.ItemViewHolder;
import com.lchrislee.worldplanner.managers.BitmapManager;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.adapters.viewholders.DefaultEntityViewHolder;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;

import timber.log.Timber;

/**
 * Created by chrisl on 3/27/17.
 */

public class StoryElementListAdapter extends RecyclerView.Adapter<DefaultEntityViewHolder> {

    private final Context context;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EntityDetailActivity.class);
            i.putExtra(EntityDetailActivity.INDEX, (long) v.getTag());
            i.putExtra(EntityDetailActivity.TYPE, DataManager.NOT_WORLD);
            context.startActivity(i);
        }
    };

    public StoryElementListAdapter(@NonNull Context c) {
        context = c;
    }

    @Override
    public DefaultEntityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder View type: " + viewType);
        DefaultEntityViewHolder holder;
        switch(viewType) {
            case DataManager.CHARACTER: {
                View v = LayoutInflater.from(context).inflate(R.layout.list_character, parent, false);
                v.setOnClickListener(clickListener);
                holder = new CharacterViewHolder(v);
            }
                break;
            case DataManager.ITEM: {
                View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
                v.setOnClickListener(clickListener);
                holder = new ItemViewHolder(v);
            }
                break;
            case DataManager.LOCATION: {
                View v = LayoutInflater.from(context).inflate(R.layout.list_location, parent, false);
                v.setOnClickListener(clickListener);
                holder = new ImageEntityViewHolder(v);
            }
                break;
            default:
                holder = null;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(DefaultEntityViewHolder holder, int position) {
        StoryElement obj = DataManager.getInstance().getElementAtIndex(position);
        if (obj == null)
        {
            return;
        }
        holder.itemView.setTag((long) position);

        switch(holder.getItemViewType())
        {
            case DataManager.CHARACTER: {
                CharacterViewHolder trueHolder = (CharacterViewHolder) holder;
                StoryCharacter proper = (StoryCharacter) obj;
                holder.description.setText(proper.getDescription());

                String nickname = proper.getNickname();
                if (nickname == null || nickname.length() == 0)
                {
                    holder.name.setText(proper.getName());
                    trueHolder.trueName.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.name.setText(nickname);
                    trueHolder.trueName.setText(proper.getName());
                }

                StringBuilder gender_age = new StringBuilder("Age ");
                gender_age.append(proper.getAge());
                if (proper.getGender() != null && proper.getGender().length() > 0) {
                    gender_age.append(", ").append(proper.getGender());
                }

                trueHolder.gender_age.setText(gender_age.toString());
                String imagePath = proper.getImage();
                Timber.d("Image path for Character: %s", imagePath);
                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_CHARACTER);
                    trueHolder.image.setImageBitmap(bitmap);
                }
            }
                break;
            case DataManager.ITEM: {
                ItemViewHolder trueHolder = (ItemViewHolder) holder;
                trueHolder.details.setName(obj.getName());
                trueHolder.details.setDescription(obj.getDescription());
                StoryItem proper = (StoryItem) obj;
                String imagePath = proper.getImage();
                Timber.d("Image path for Character: %s", imagePath);
                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_ITEM);
                    trueHolder.details.setImage(bitmap);
                }
            }
                break;
            case DataManager.LOCATION: {
                holder.name.setText(obj.getName());
                holder.description.setText(obj.getDescription());
                StoryLocation proper = (StoryLocation) obj;
                String imagePath = proper.getImage();
                ImageEntityViewHolder trueHolder = (ImageEntityViewHolder) holder;
                Timber.d("Image path for Character: %s", imagePath);
                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_LOCATION);
                    trueHolder.image.setImageBitmap(bitmap);
                }
            }
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        Timber.d("Count for world: " + DataManager.getInstance().getCountForAllWorldElements());
        return DataManager.getInstance().getCountForAllWorldElements();
    }

    @Override
    public int getItemViewType(int position) {
        Timber.d("Item view type: " + DataManager.getInstance().getElementTypeAtIndex(position) + " for position: " + position);
        return DataManager.getInstance().getElementTypeAtIndex(position);
    }

}