package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.CharacterRelationListAdapter;

public class CharacterRelationListFragment extends WorldPlannerBaseFragment
{


    public CharacterRelationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_character_relation_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_character_relation_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final CharacterRelationListAdapter adapter = new CharacterRelationListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        return v;
    }

}
