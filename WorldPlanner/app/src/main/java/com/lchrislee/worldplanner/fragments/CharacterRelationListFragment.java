package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.CharacterRelationListAdapter;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.views.CharacterRelationDialog;

public class CharacterRelationListFragment extends WorldPlannerBaseFragment implements CharacterRelationDialog.CharacterRelationDialogListener
{

    CharacterRelationListAdapter adapter;

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
        adapter = new CharacterRelationListAdapter(getContext());
        recyclerView.setAdapter(adapter);

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fragment_character_relation_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterRelationDialog dialog = CharacterRelationDialog.newInstance(null);
                dialog.setCharacterRelationDialogListener(CharacterRelationListFragment.this);
                dialog.show(getChildFragmentManager(), "CharacterRelationDialog");
            }
        });
        return v;
    }

    @Override
    public void onDialogPositiveClick(Relationship relationship) {
        //TODO: User clicked create.
    }

    @Override
    public void onDialogPositiveClick(Relationship relationship, int index) {
        //TODO: Edited a relationship.
    }

}
