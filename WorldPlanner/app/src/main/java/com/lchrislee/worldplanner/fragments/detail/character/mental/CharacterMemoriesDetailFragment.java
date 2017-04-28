package com.lchrislee.worldplanner.fragments.detail.character.mental;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.SupportCharacterUpdate;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterMemoriesDetailFragment
        extends WorldPlannerBaseFragment
        implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";

    public CharacterMemoriesDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull
    CharacterMemoriesDetailFragment newInstance(@NonNull StoryCharacter character)
    {
        CharacterMemoriesDetailFragment fragment = new CharacterMemoriesDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CHARACTER, character);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_memories, container, false);
    }

    @Override
    public void updateCharacter() {

    }
}
