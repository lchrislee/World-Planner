package com.lchrislee.worldplanner.fragments.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryGroup;

import java.io.Serializable;
import java.util.List;

public class GroupAddCharacterDialog extends MultipleSelectDialog {

    private StoryGroup master;

    public GroupAddCharacterDialog() {
    }

    public static @NonNull GroupAddCharacterDialog newInstance(@NonNull Serializable obj)
    {
        GroupAddCharacterDialog fragment = new GroupAddCharacterDialog();
        Bundle arguments = new Bundle();
        arguments.putSerializable(MASTER, obj);
        arguments.putSerializable(TITLE, "Add characters to this group.");
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        master = (StoryGroup) seekingElement;
    }

    @NonNull
    @Override
    protected MultipleSelectListAdapter getAdapter() {
        return new AddCharacterListAdapter(getContext());
    }

    private class AddCharacterListAdapter extends MultipleSelectListAdapter
    {
        private List<StoryCharacter> characters;

        AddCharacterListAdapter(@NonNull Context context) {
            super(context);
            characters = DataManager.getInstance().getAllCharactersNotInGroup(master);
        }

        @Override
        protected void additionalBinding(MultipleSelectViewHolder holder, int position) {
            // Nothing necessary
        }

        @NonNull
        @Override
        protected StoryElement getElementAtIndex(int position) {
            return characters.get(position);
        }

        @Override
        protected long getIdAtIndex(int position) {
            return characters.get(position).getId();
        }

        @Override
        public int getItemCount() {
            return characters.size();
        }
    }
}
