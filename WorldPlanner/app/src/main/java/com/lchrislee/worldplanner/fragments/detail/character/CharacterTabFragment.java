package com.lchrislee.worldplanner.fragments.detail.character;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryElement;

import java.io.Serializable;

public class CharacterTabFragment extends WorldPlannerBaseFragment implements ToolbarSupportingFragment {
    private static final String DATA = CharacterTabFragment.class.getSimpleName() + "_DATA";

    public interface CharacterDetailTabChange
    {
        void onCharacterTabSwitch();
    }

    private CharacterDetailFragment informationFragment;

    private CharacterDetailTabChange listener;
    private boolean isShowingDetails;
    private StoryCharacter model;

    public CharacterTabFragment() {
        isShowingDetails = true;
    }

    public static CharacterTabFragment newInstance(@NonNull Serializable object)
    {
        CharacterTabFragment fragment = new CharacterTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA, object);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = (StoryCharacter) getArguments().getSerializable(DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_character, container, false);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.fragment_tab_character_bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.menu_bottom_character_details:
                        if (informationFragment == null)
                        {
                            informationFragment = CharacterDetailFragment.newInstance(model);
                        }
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.fragment_tab_character_frame, informationFragment)
                                .addToBackStack(informationFragment.getClass().getSimpleName())
                                .commit();
                        isShowingDetails = true;
                        break;
                }
                if (listener != null)
                {
                    listener.onCharacterTabSwitch();
                }
                return true;
            }
        });

        informationFragment = CharacterDetailFragment.newInstance(model);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_tab_character_frame, informationFragment).commit();
        return v;
    }

    public boolean isShowingDetails() {
        return isShowingDetails;
    }

    public void setListener(CharacterDetailTabChange listener) {
        this.listener = listener;
    }

    @Override
    public long editAction() {
        return informationFragment.editAction();
    }

    @Nullable
    @Override
    public StoryElement getModel() {
        return model;
    }
}
