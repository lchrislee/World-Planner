package com.lchrislee.worldplanner.fragments.CharacterDetail;


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
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

public class CharacterTabFragment extends WorldPlannerBaseFragment implements ToolbarSupportingFragment {
    public static final String INDEX = CharacterTabFragment.class.getSimpleName() + "_INDEX";

    public interface CharacterDetailTabChange
    {
        void onCharacterTabSwitch();
    }

    CharacterDetailFragment informationFragment;
    CharacterRelationListFragment relationFragment;

    private CharacterDetailTabChange listener;
    private boolean isShowingDetails;
    private int index;

    public CharacterTabFragment() {
        isShowingDetails = true;
    }

    public static CharacterTabFragment newInstance(int charIndex)
    {
        CharacterTabFragment fragment = new CharacterTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, charIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt(INDEX, -1);
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
                    case R.id.menu_detail_character_information:
                        if (informationFragment == null)
                        {
                            informationFragment = CharacterDetailFragment.newInstance(false, index);
                        }
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.fragment_tab_character_frame, informationFragment)
                                .addToBackStack(informationFragment.getClass().getSimpleName())
                                .commit();
                        isShowingDetails = true;
                        break;
                    case R.id.menu_detail_character_relationship:
                        if (relationFragment == null) {
                            relationFragment = new CharacterRelationListFragment();
                        }
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.fragment_tab_character_frame, relationFragment)
                                .addToBackStack(relationFragment.getClass().getSimpleName())
                                .commit();
                        isShowingDetails = false;
                        break;
                }
                if (listener != null)
                {
                    listener.onCharacterTabSwitch();
                }
                return true;
            }
        });

        informationFragment = CharacterDetailFragment.newInstance(false, index);
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
    public void editAction() {
        informationFragment.editAction();
    }

    @NonNull
    @Override
    public WorldPlannerBaseModel getModel() {
        return new StoryCharacter("Character to Share", "Just a placeholder until data and model implemented.");
    }
}