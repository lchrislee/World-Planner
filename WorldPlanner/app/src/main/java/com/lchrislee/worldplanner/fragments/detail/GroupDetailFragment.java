package com.lchrislee.worldplanner.fragments.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.WorldPlannerBaseListAdapter;
import com.lchrislee.worldplanner.adapters.viewholders.EventViewHolder;
import com.lchrislee.worldplanner.fragments.dialogs.GroupAddCharacterDialog;
import com.lchrislee.worldplanner.fragments.dialogs.MultipleSelectDialog;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryGroup;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;
import java.util.List;

public class GroupDetailFragment
        extends DetailFragment
        implements MultipleSelectDialog.MultipleSelectAddEventListener
{

    private EditText size;

    private StoryGroup group;

    private TextView eventsListPrompt;
    private RecyclerView eventsList;
    private CharacterInGroupListAdapter adapter;

    public GroupDetailFragment() {
        // Required
    }

    public static @NonNull GroupDetailFragment newInstance(@Nullable Serializable obj)
    {
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ENTITY_TYPE, DataManager.GROUP);
        args.putSerializable(DetailFragment.DATA, obj);
        args.putInt(LAYOUT, R.layout.fragment_detail_group);
        args.putString(TITLE, "Group");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryGroup group = new StoryGroup();
            group.setWorld(currentWorld);
            model = group;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null)
        {
            size = (EditText) mainView.findViewById(R.id.fragment_detail_group_size);

            adapter = new CharacterInGroupListAdapter(getContext());
            eventsList = (RecyclerView) mainView.findViewById(R.id.fragment_detail_group_characters);
            eventsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            eventsList.setAdapter(adapter);

            eventsListPrompt = (TextView) mainView.findViewById(R.id.fragment_detail_group_characters_prompt);
            eventsListPrompt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupAddCharacterDialog fragment = GroupAddCharacterDialog.newInstance(group);
                    fragment.setListener(GroupDetailFragment.this);
                    fragment.show(getChildFragmentManager(), "Character add event");
                }
            });

        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null) {
            group = (StoryGroup) model;
            size.setText(String.valueOf(group.getSize()));

            if (adapter != null)
            {
                adapter.notifyDataSetChanged();
                eventsList.scrollToPosition(0);
            }
        }
        updateViews();
        super.onResume();
    }

    @Override
    protected void updateViews()
    {
        if (size != null)
        {
            if (isEditing)
            {
                Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.editbox_background_normal);
                size.setBackground(editBackground);

                eventsList.setVisibility(View.GONE);
                eventsListPrompt.setVisibility(View.GONE);
            }
            else
            {
                int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
                ColorDrawable background = new ColorDrawable(transparent);
                size.setBackground(background);

                eventsList.setVisibility(View.VISIBLE);
                eventsListPrompt.setVisibility(View.VISIBLE);
            }

            size.setFocusable(isEditing);
            size.setClickable(isEditing);
            size.setFocusableInTouchMode(isEditing);
            size.setLongClickable(isEditing);

            String sizeText = size.getText().toString();
            if (sizeText.length() == 0)
            {
                group.setSize(0);
            }
            else
            {
                group.setSize(Integer.parseInt(sizeText));
            }

            super.updateViews();
        }

    }

    @Override
    public void onPositiveClick(@NonNull List<Long> selected) {
        for (long l : selected)
        {
            DataManager.getInstance().addCharacterToGroup(l, group);
        }
        adapter.notifyDataSetChanged();
    }

    private class CharacterInGroupListAdapter
            extends WorldPlannerBaseListAdapter<EventViewHolder>
            implements View.OnClickListener
    {
        private int lastPressed;
        CharacterInGroupListAdapter(@NonNull Context context) {
            super(context, R.layout.list_event);
            setViewClickListener(this);
        }

        @Nullable
        @Override
        protected StoryElement obtainElement(int position) {
            return group.getCharacterInGroup(position);
        }

        @NonNull
        @Override
        protected EventViewHolder generateViewHolder(View v) {
            return new EventViewHolder(v);
        }

        @Override
        protected void additionalBindViewHolder(EventViewHolder holder, @NonNull StoryElement element) {
            holder.description.setVisibility(View.GONE);
            holder.location.setVisibility(View.GONE);
            StoryGroup.CharacterInGroup cin = (StoryGroup.CharacterInGroup) element;
            holder.name.setText(cin.getCharacter().getName());
        }

        @Override
        public int getItemCount() {
            return group.getCharactersCount();
        }

        @Override
        public void onClick(View v) {
            lastPressed = (int) v.getTag();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Remove character from this group?");
            builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataManager.getInstance().removeCharacterFromGroup(lastPressed, group);
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }
    }
}
