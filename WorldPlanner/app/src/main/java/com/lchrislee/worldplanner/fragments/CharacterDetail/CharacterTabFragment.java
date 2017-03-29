package com.lchrislee.worldplanner.fragments.CharacterDetail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.DetailFragment;
import com.lchrislee.worldplanner.fragments.EditableFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;

public class CharacterTabFragment extends WorldPlannerBaseFragment implements EditableFragment {

    CharacterDetailFragment informationFragment;
    CharacterRelationListFragment relationFragment;

    public CharacterTabFragment() {
        // Required empty public constructor
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
                            informationFragment = CharacterDetailFragment.newInstance(false);
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_tab_character_frame, informationFragment).commit();
                        break;
                    case R.id.menu_detail_character_relationship:
                        if (relationFragment == null) {
                            relationFragment = new CharacterRelationListFragment();
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_tab_character_frame, relationFragment).commit();
                        break;
                }
                return true;
            }
        });

        informationFragment = CharacterDetailFragment.newInstance(false);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_tab_character_frame, informationFragment).commit();

        return v;
    }

    @Override
    public boolean isEditing() {
        return informationFragment.isEditing();
    }

    @Override
    public void iconAction() {
        informationFragment.iconAction();
    }
}
