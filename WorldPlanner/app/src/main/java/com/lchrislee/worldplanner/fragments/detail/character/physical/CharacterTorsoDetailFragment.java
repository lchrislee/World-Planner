package com.lchrislee.worldplanner.fragments.detail.character.physical;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.databinding.FragmentCharacterTorsoBinding;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.SupportCharacterUpdate;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterTorsoDetailFragment
        extends WorldPlannerBaseFragment
    implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";

    private StoryCharacter character;
    private FragmentCharacterTorsoBinding binding;

    public CharacterTorsoDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull CharacterTorsoDetailFragment newInstance(@NonNull StoryCharacter character)
    {
        CharacterTorsoDetailFragment fragment = new CharacterTorsoDetailFragment();
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
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_character_torso,
                container,
                false
        );
        binding.setCharacter(character);
        return binding.getRoot();
    }

    @Override
    public void updateCharacter() {
        if (binding != null) {
            character.setShoulders(binding.fragmentCharacterNotesShoulder.getText().toString());
            character.setChest(binding.fragmentCharacterNotesChest.getText().toString());
            character.setStomach(binding.fragmentCharacterNotesStomach.getText().toString());
            character.setBack(binding.fragmentCharacterNotesBack.getText().toString());
        }
    }
}
