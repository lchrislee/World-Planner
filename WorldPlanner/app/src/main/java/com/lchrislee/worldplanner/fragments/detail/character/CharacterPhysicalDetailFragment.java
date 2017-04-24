package com.lchrislee.worldplanner.fragments.detail.character;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterPhysicalDetailFragment extends WorldPlannerBaseFragment {

    private static final String CHARACTER = "CHARACTER";

    private StoryCharacter character;

    public CharacterPhysicalDetailFragment() {

    }

    public static @NonNull
    CharacterPhysicalDetailFragment newInstance(@NonNull StoryCharacter c)
    {
        CharacterPhysicalDetailFragment fragment = new CharacterPhysicalDetailFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(CHARACTER, c);
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        character = (StoryCharacter) getArguments().getSerializable(CHARACTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_character_physical, container, false);
        return v;
    }
}
