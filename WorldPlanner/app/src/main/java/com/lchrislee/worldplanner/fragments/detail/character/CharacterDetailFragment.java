package com.lchrislee.worldplanner.fragments.detail.character;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.detail.DetailFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryWorld;

import java.io.Serializable;

public class CharacterDetailFragment extends DetailFragment{

    private EditText nickname;
    private EditText gender;
    private EditText age;

    private StoryCharacter trueModel;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull CharacterDetailFragment newInstance(Serializable object)
    {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DetailFragment.ENTITY_TYPE, DataManager.CHARACTER);
        bundle.putSerializable(DATA, object);
        bundle.putInt(LAYOUT, R.layout.fragment_detail_character);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (model == null) {
            DataManager dataManager = DataManager.getInstance();
            StoryWorld currentWorld = dataManager.getCurrentWorld();
            StoryCharacter character = new StoryCharacter();
            character.setWorld(currentWorld);
            model = character;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null) {
            nickname = (EditText) mainView.findViewById(R.id.fragment_detail_character_nickname);
            gender = (EditText) mainView.findViewById(R.id.fragment_detail_character_gender);
            age = (EditText) mainView.findViewById(R.id.fragment_detail_character_age);
        }
        return mainView;
    }

    @Override
    public void onResume() {
        if (model != null)
        {
            trueModel = (StoryCharacter) model;
            String textNickName= trueModel.getNickname();
            if (textNickName != null)
            {
                nickname.setText(textNickName);
            }
            String textGender = trueModel.getGender();
            if (textGender != null)
            {
                gender.setText(textGender);
            }

            age.setText(String.valueOf(trueModel.getAge()));
        }
        updateViews();
        super.onResume();
    }

    @Override
    protected void updateViews()
    {
        if (nickname != null) {
            Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.edit_text);
            if (isEditing) {
                nickname.setBackground(editBackground);
                gender.setBackground(editBackground);
                age.setBackground(editBackground);
            } else {
                int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
                ColorDrawable background = new ColorDrawable(transparent);
                nickname.setBackground(background);
                gender.setBackground(background);
                age.setBackground(background);
            }

            nickname.setFocusable(isEditing);
            nickname.setClickable(isEditing);
            nickname.setFocusableInTouchMode(isEditing);
            nickname.setLongClickable(isEditing);
            gender.setFocusable(isEditing);
            gender.setClickable(isEditing);
            gender.setFocusableInTouchMode(isEditing);
            gender.setLongClickable(isEditing);
            age.setFocusable(isEditing);
            age.setClickable(isEditing);
            age.setFocusableInTouchMode(isEditing);
            age.setLongClickable(isEditing);

            trueModel.setAge(Integer.parseInt(age.getText().toString()));
            trueModel.setNickname(nickname.getText().toString());
            trueModel.setGender(gender.getText().toString());

            super.updateViews();
        }
    }

}
