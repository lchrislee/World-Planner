package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.ModelDetailActivity;
import com.lchrislee.worldplanner.models.ImportanceRelation;

public class CharacterDetailFragment extends WorldPlannerBaseFragment implements EditableFragment {

    PlannerObjectDetailFragment informationFragment;
    CharacterRelationListFragment relationFragment;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_character_master, container, false);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.fragment_detail_master_bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.menu_detail_character_information:
                        if (informationFragment == null)
                        {
                            informationFragment = PlannerObjectDetailFragment.newInstance(ImportanceRelation.ImportantType.Character, false);
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_detail_master_frame, informationFragment).commit();
                        break;
                    case R.id.menu_detail_character_relationship:
                        if (relationFragment == null) {
                            relationFragment = new CharacterRelationListFragment();
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_detail_master_frame, relationFragment).commit();
                        break;
                }
                return true;
            }
        });

        informationFragment = PlannerObjectDetailFragment.newInstance(ImportanceRelation.ImportantType.Character, false);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_detail_master_frame, informationFragment).commit();

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
