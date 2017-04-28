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

public class CharacterTabFragment
        extends WorldPlannerBaseFragment
        implements ToolbarSupportingFragment, OnTabSelected
{
    private static final String DATA = CharacterTabFragment.class.getSimpleName() + "_DATA";

    @Override
    public void onTabSelected() {
        if (listener != null)
        {
            listener.onCharacterTabSwitch();
        }
    }

    public interface CharacterDetailTabChange
    {
        void onCharacterTabSwitch();
    }

    private CharacterBasicDetailFragment basicDetailFragment;
    private CharacterPhysicalDetailFragment physicalDetailFragment;
    private CharacterMentalDetailFragment mentalDetailFragment;

    private CharacterDetailTabChange listener;
    private StoryCharacter model;

    private boolean isEditing;

    public CharacterTabFragment() {
        isEditing = false;
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
                        if (basicDetailFragment == null)
                        {
                            basicDetailFragment = CharacterBasicDetailFragment.newInstance(model);
                        }
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.fragment_tab_character_frame, basicDetailFragment)
                                .addToBackStack(basicDetailFragment.getClass().getSimpleName())
                                .commit();
                        break;
                    case R.id.menu_bottom_character_physical:
                        if (physicalDetailFragment == null)
                        {
                            physicalDetailFragment = CharacterPhysicalDetailFragment.newInstance(model);
                            physicalDetailFragment.setAdapterListener(CharacterTabFragment.this);
                        }
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.fragment_tab_character_frame, physicalDetailFragment)
                                .addToBackStack(physicalDetailFragment.getClass().getSimpleName())
                                .commit();
                        break;
                    case R.id.menu_bottom_character_mental:
                        if (mentalDetailFragment == null)
                        {
                            mentalDetailFragment = CharacterMentalDetailFragment.newInstance(model);
                            mentalDetailFragment.setAdapterListener(CharacterTabFragment.this);
                        }
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.fragment_tab_character_frame, mentalDetailFragment)
                                .addToBackStack(mentalDetailFragment.getClass().getSimpleName())
                                .commit();
                        break;
                }
                if (listener != null)
                {
                    listener.onCharacterTabSwitch();
                }
                return true;
            }
        });

        basicDetailFragment = CharacterBasicDetailFragment.newInstance(model);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_tab_character_frame, basicDetailFragment).commit();
        return v;
    }

    public boolean isShowingDetails() {
        return true;
    }

    public void setListener(CharacterDetailTabChange listener) {
        this.listener = listener;
    }

    @Override
    public long editAction() {
        if (isEditing)
        {
            physicalDetailFragment.updateCharacter();
        }
        isEditing = !isEditing;
        return basicDetailFragment.editAction();
    }

    @Nullable
    @Override
    public StoryElement getModel() {
        return model;
    }

    @NonNull
    @Override
    public String getTitle() {
        return "Character";
    }
}
