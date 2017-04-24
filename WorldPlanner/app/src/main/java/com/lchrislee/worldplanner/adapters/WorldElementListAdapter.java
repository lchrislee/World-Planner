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
import com.lchrislee.worldplanner.adapters.viewholders.DefaultEntityViewHolder;
import com.lchrislee.worldplanner.adapters.viewholders.GroupViewHolder;
import com.lchrislee.worldplanner.adapters.viewholders.ItemViewHolder;
import com.lchrislee.worldplanner.managers.BitmapManager;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.adapters.viewholders.WorldPlannerBaseViewHolder;
import com.lchrislee.worldplanner.models.StoryGroup;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;

import timber.log.Timber;

public class WorldElementListAdapter extends RecyclerView.Adapter<WorldPlannerBaseViewHolder> {

    private final Context context;
    private final int type;

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, EntityDetailActivity.class);
            i.putExtra(EntityDetailActivity.INDEX, (long) v.getTag());
            i.putExtra(EntityDetailActivity.TYPE, type);
            context.startActivity(i);
        }
    };

    public WorldElementListAdapter(@NonNull Context c, int type) {
        context = c;
        this.type = type;
    }

    @Override
    public WorldPlannerBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder View type: " + viewType);
        WorldPlannerBaseViewHolder holder;
        switch(viewType) {
            case DataManager.CHARACTER: {
                View v = LayoutInflater.from(context).inflate(R.layout.list_character, parent, false);
                v.setOnClickListener(clickListener);
                holder = new CharacterViewHolder(v);
            }
                break;
            case DataManager.LOCATION: {
                View v = LayoutInflater.from(context).inflate(R.layout.list_location, parent, false);
                v.setOnClickListener(clickListener);
                holder = new DefaultEntityViewHolder(v);
            }
                break;
            case DataManager.GROUP:{
                View v = LayoutInflater.from(context).inflate(R.layout.list_group, parent, false);
                v.setOnClickListener(clickListener);
                holder = new GroupViewHolder(v);
            }
            break;
            case DataManager.ITEM: {
                View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
                v.setOnClickListener(clickListener);
                holder = new ItemViewHolder(v);
            }
            break;
            default:
                holder = null;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(WorldPlannerBaseViewHolder holder, int position) {
        StoryElement obj = DataManager.getInstance().getElementAtIndex(type, position);
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

                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_CHARACTER);
                    trueHolder.image.setImageBitmap(bitmap);
                }
                else
                {
                    trueHolder.image.setImageBitmap(null);
                }
            }
                break;
            case DataManager.GROUP:{
                StoryGroup proper = (StoryGroup) obj;
                GroupViewHolder trueHolder = (GroupViewHolder) holder;
                trueHolder.name.setText(proper.getName());
                trueHolder.description.setText(proper.getDescription());
                String size = "Size: " + proper.getSize();
                trueHolder.size.setText(size);

                String imagePath = proper.getImage();
                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_DEFAULT);
                    trueHolder.image.setImageBitmap(bitmap);
                }
                else
                {
                    trueHolder.image.setImageBitmap(null);
                }
            }
            break;
            case DataManager.ITEM: {
                StoryItem proper = (StoryItem) obj;
                ItemViewHolder trueHolder = (ItemViewHolder) holder;
                trueHolder.name.setText(obj.getName());
                trueHolder.description.setText(obj.getDescription());

                String effectsText = "Number of effects: " + proper.getEffectsCount();
                trueHolder.effectsCount.setText(effectsText);

                String imagePath = proper.getImage();

                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_DEFAULT);
                    trueHolder.image.setImageBitmap(bitmap);
                }
                else
                {
                    trueHolder.image.setImageBitmap(null);
                }
            }
                break;
            case DataManager.LOCATION: {
                holder.name.setText(obj.getName());
                holder.description.setText(obj.getDescription());
                StoryLocation proper = (StoryLocation) obj;
                String imagePath = proper.getImage();
                DefaultEntityViewHolder trueHolder = (DefaultEntityViewHolder) holder;

                if (imagePath.length() > 0) {
                    Bitmap bitmap = BitmapManager.getInstance().loadBitmapFromFile(
                            context,
                            imagePath,
                            BitmapManager.ResizeType.LIST_LOCATION);
                    trueHolder.image.setImageBitmap(bitmap);
                }
                else
                {
                    trueHolder.image.setImageBitmap(null);
                }
            }
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getCountForElementsOfType(type);
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

}