package com.lchrislee.worldplanner.fragments.detail.character.physical;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.databinding.FragmentCharacterHeadBinding;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.SupportCharacterUpdate;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterHeadDetailFragment
        extends WorldPlannerBaseFragment
        implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";
    private StoryCharacter character;
    private FragmentCharacterHeadBinding binding;

    public CharacterHeadDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull
    CharacterHeadDetailFragment newInstance(@NonNull StoryCharacter character)
    {
        CharacterHeadDetailFragment fragment = new CharacterHeadDetailFragment();
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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_head, container, false);
        binding.setCharacter(character);
        return binding.getRoot();
    }

    @Override
    public void updateCharacter() {
        if (binding != null) {
            character.setHair(binding.fragmentCharacterNotesHair.getText().toString());
            character.setEars(binding.fragmentCharacterNotesEar.getText().toString());
            character.setEyes(binding.fragmentCharacterNotesEye.getText().toString());
            character.setNose(binding.fragmentCharacterNotesNose.getText().toString());
            character.setMouth(binding.fragmentCharacterNotesMouth.getText().toString());
            character.setNeck(binding.fragmentCharacterNotesNeck.getText().toString());
        }
    }
}
