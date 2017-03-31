package com.lchrislee.worldplanner.fragments.CharacterDetail;


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
import com.lchrislee.worldplanner.fragments.DetailFragment;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterDetailFragment extends DetailFragment implements ToolbarSupportingFragment{

    private EditText nickname;
    private EditText gender;
    private EditText age;

    StoryCharacter trueModel;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull CharacterDetailFragment newInstance(boolean edit, int charIndex)
    {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(DetailFragment.EDIT, edit);
        bundle.putInt(DetailFragment.RELATION_TYPE, DataManager.CODE_CHARACTER);
        bundle.putInt(INDEX, charIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        if (mainView != null) {
            nickname = (EditText) mainView.findViewById(R.id.fragment_detail_character_nickname);
            gender = (EditText) mainView.findViewById(R.id.fragment_detail_character_gender);
            age = (EditText) mainView.findViewById(R.id.fragment_detail_character_age);
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
            swapEdit();
        }
        return mainView;
    }

    @Override
    protected void swapEdit()
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

            super.swapEdit();
        }
    }

}
