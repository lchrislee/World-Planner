package com.lchrislee.worldplanner.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.dialogs.ItemEffectDialogFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

public class ItemDetailFragment
        extends DetailFragment
        implements ToolbarSupportingFragment, ItemEffectDialogFragment.ItemEffectDialogListener
{

    private StoryItem item;

    private ItemEffectListAdapter adapter;
    private RecyclerView list;
    private ImageView addEffect;
    private TextView addEffectPrompt;

    public ItemDetailFragment() {
        // Required
    }

    public static @NonNull
    ItemDetailFragment newInstance(@Nullable Serializable obj)
    {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.ITEM);
        args.putSerializable(DetailFragment.DATA, obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryItem item = new StoryItem();
            item.setWorld(currentWorld);
            model = item;
            model.setName("");
            model.setDescription("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);

        if (mainView != null)
        {
            adapter = new ItemEffectListAdapter(getContext());
            list = (RecyclerView) mainView.findViewById(R.id.fragment_detail_item_list);
            list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
            list.setAdapter(adapter);

            addEffectPrompt = (TextView) mainView.findViewById(R.id.fragment_detail_item_effect_prompt);
            addEffect = (ImageView) mainView.findViewById(R.id.fragment_detail_item_effect_add);
            addEffect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemEffectDialogFragment dialogFragment = ItemEffectDialogFragment.newInstance(item, -1);
                    dialogFragment.setListener(ItemDetailFragment.this);
                    dialogFragment.show(getChildFragmentManager(), "ITEM_EFFECT");
                }
            });
        }

        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            item = (StoryItem) model;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                list.scrollToPosition(adapter.getItemCount() - 1);
            }
        }
        updateViews();
        super.onResume();
    }

    @Override
    protected void updateViews() {
        if (isEditing)
        {
            if (list != null)
            {
                if (isNew)
                {
                    list.setVisibility(View.GONE);
                }
                else
                {
                    list.setVisibility(View.VISIBLE);
                }
                addEffect.setVisibility(View.GONE);
                addEffectPrompt.setVisibility(View.GONE);
            }
        }
        else
        {
            if (addEffect != null) {
                addEffect.setVisibility(View.VISIBLE);
                addEffectPrompt.setVisibility(View.VISIBLE);
            }
        }
        super.updateViews();
    }

    @Override
    public void onUpdate()
    {
        adapter.notifyDataSetChanged();
    }

    private class ItemEffectListAdapter
            extends RecyclerView.Adapter<ItemEffectListAdapter.ItemEffectViewHolder>
    {
        class ItemEffectViewHolder extends RecyclerView.ViewHolder
        {
            public TextView name;
            public TextView description;
            ItemEffectViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(android.R.id.text1);
                description = (TextView) itemView.findViewById(android.R.id.text2);
            }
        }

        Context context;

        ItemEffectListAdapter(@NonNull Context context) {
            this.context = context;
        }

        @Override
        public ItemEffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemEffectDialogFragment dialogFragment = ItemEffectDialogFragment.newInstance(item, (int) v.getTag());
                    dialogFragment.setListener(ItemDetailFragment.this);
                    dialogFragment.show(getChildFragmentManager(), "ITEM_EFFECT");
                }
            });
            return new ItemEffectViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemEffectViewHolder holder, int position) {
            StoryItem.StoryItemEffect effect = item.getEffectAtIndex(position);
            if (effect == null)
            {
                return;
            }
            holder.name.setText(effect.getName());
            holder.description.setText(effect.getDescription());
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return item.getEffectsCount();
        }
    }
}
