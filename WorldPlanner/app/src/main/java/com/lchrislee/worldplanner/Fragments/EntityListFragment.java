package com.lchrislee.worldplanner.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.Adapters.EntityListAdapter;
import com.lchrislee.worldplanner.Models.Entity;
import com.lchrislee.worldplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntityListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntityListFragment extends WorldPlannerBaseFragment {
    private static final String TYPE_TO_DISPLAY = "ENTITY_LIST_FRAGMENT_TYPE_TO_DISPLAY";

    private RecyclerView list;
    private EntityListAdapter adapter;

    private Entity.EntityType typeToDisplay;

    public EntityListFragment() {
        // Required empty public constructor
    }

    public static EntityListFragment newInstance(Entity.EntityType type) {
        EntityListFragment fragment = new EntityListFragment();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_TO_DISPLAY, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        typeToDisplay = (Entity.EntityType) arguments.getSerializable(TYPE_TO_DISPLAY);
        adapter = new EntityListAdapter(typeToDisplay, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entity_list, container, false);
        list = (RecyclerView) v.findViewById(R.id.fragment_entity_list_data);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);
        return v;
    }

}
