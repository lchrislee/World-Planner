package com.lchrislee.worldplanner.fragments.detail.character.physical;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.databinding.FragmentCharacterLegBinding;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.SupportCharacterUpdate;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterLegDetailFragment
        extends WorldPlannerBaseFragment
        implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";

    private StoryCharacter character;

    private FragmentCharacterLegBinding binding;

    public CharacterLegDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull
    CharacterLegDetailFragment newInstance(@NonNull StoryCharacter character)
    {
        CharacterLegDetailFragment fragment = new CharacterLegDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CHARACTER, character);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        character = (StoryCharacter) arguments.getSerializable(CHARACTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_leg, container, false);
        return binding.getRoot();
    }

    @Override
    public void updateCharacter() {
        if (binding != null) {
            character.setUpperLeftLeg(binding.fragmentCharacterNotesLegUleft.getText().toString());
            character.setLowerLeftLeg(binding.fragmentCharacterNotesLegLleft.getText().toString());
            character.setFootLeft(binding.fragmentCharacterNotesFootLeft.getText().toString());
            character.setUpperRightLeg(binding.fragmentCharacterNotesLegUright.getText().toString());
            character.setLowerRightLeg(binding.fragmentCharacterNotesLegLright.getText().toString());
            character.setFootRight(binding.fragmentCharacterNotesFootRight.getText().toString());
        }
    }
}
