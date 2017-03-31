package com.lchrislee.worldplanner.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.adapters.EntityListAdapter;
import com.lchrislee.worldplanner.R;

public class EntityListFragment extends WorldPlannerBaseFragment {
    private static final String TYPE_TO_DISPLAY = "ENTITY_LIST_FRAGMENT_TYPE_TO_DISPLAY";

    private EntityListAdapter adapter;

    public EntityListFragment() {
        // Required empty public constructor
    }

    public static EntityListFragment newInstance(int type) {
        EntityListFragment fragment = new EntityListFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_TO_DISPLAY, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        int typeToDisplay =  arguments.getInt(TYPE_TO_DISPLAY);
        adapter = new EntityListAdapter(typeToDisplay, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_default, container, false);
        final RecyclerView list = (RecyclerView) v.findViewById(R.id.fragment_list_data);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
