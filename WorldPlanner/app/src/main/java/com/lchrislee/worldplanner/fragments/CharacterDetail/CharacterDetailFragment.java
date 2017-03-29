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
import com.lchrislee.worldplanner.models.ImportanceRelation;

public class CharacterDetailFragment extends DetailFragment {

    private EditText nickname;
    private EditText gender;
    private EditText age;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }

    public static @NonNull CharacterDetailFragment newInstance(boolean edit)
    {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(DetailFragment.EDIT, edit);
        bundle.putSerializable(DetailFragment.RELATION_TYPE, ImportanceRelation.ImportantType.Character);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = super.onCreateView(inflater, container, savedInstanceState);
        nickname = (EditText) mainView.findViewById(R.id.fragment_detail_character_nickname);
        gender = (EditText) mainView.findViewById(R.id.fragment_detail_character_gender);
        age = (EditText) mainView.findViewById(R.id.fragment_detail_character_age);
        swapEdit();
        return mainView;
    }

    @Override
    protected void swapEdit()
    {
        if (isInLayout()) {
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
        }

        super.swapEdit();
    }

}
