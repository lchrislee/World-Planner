package com.lchrislee.worldplanner.fragments.detail.character.physical;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.databinding.FragmentCharacterArmBinding;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.SupportCharacterUpdate;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterArmDetailFragment
        extends WorldPlannerBaseFragment
        implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";

    private StoryCharacter character;
    private FragmentCharacterArmBinding binding;

    public CharacterArmDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull
    CharacterArmDetailFragment newInstance(@NonNull StoryCharacter character)
    {
        CharacterArmDetailFragment fragment = new CharacterArmDetailFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_arm, container, false);
        binding.setCharacter(character);
        return binding.getRoot();
    }

    @Override
    public void updateCharacter() {
        if (binding != null) {
            character.setUpperLeftArm(binding.fragmentCharacterNotesArmUleft.getText().toString());
            character.setLowerLeftArm(binding.fragmentCharacterNotesArmLleft.getText().toString());
            character.setHandLeft(binding.fragmentCharacterNotesHandLeft.getText().toString());
            character.setUpperRightArm(binding.fragmentCharacterNotesArmUright.getText().toString());
            character.setLowerRightArm(binding.fragmentCharacterNotesArmLright.getText().toString());
            character.setHandRight(binding.fragmentCharacterNotesHandRight.getText().toString());
        }
    }
}
