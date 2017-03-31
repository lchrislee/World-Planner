package com.lchrislee.worldplanner.fragments.CharacterDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.RelationDetailActivity;
import com.lchrislee.worldplanner.adapters.CharacterRelationshipsListAdapter;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;

public class CharacterRelationListFragment extends WorldPlannerBaseFragment {

    private static final String INDEX = "CHARACTERRELATIONLISTFRAGMENT_INDEX";

    private CharacterRelationshipsListAdapter adapter;

    private long characterIndex;

    public CharacterRelationListFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static CharacterRelationListFragment newInstance(long index)
    {
        CharacterRelationListFragment fragment = new CharacterRelationListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characterIndex = getArguments().getInt(INDEX);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_character_relation_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_character_relation_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CharacterRelationshipsListAdapter(getContext(), characterIndex);
        recyclerView.setAdapter(adapter);

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fragment_character_relation_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), RelationDetailActivity.class);
                i.putExtra(RelationDetailActivity.OWNER_INDEX, characterIndex);
                startActivityForResult(i, RelationDetailActivity.REQUEST_CODE_NEW);
            }
        });
        return v;
    }

}
