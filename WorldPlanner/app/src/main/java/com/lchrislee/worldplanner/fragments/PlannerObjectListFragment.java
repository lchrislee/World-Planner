package com.lchrislee.worldplanner.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.adapters.MasterPlannerObjectListAdapter;
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.R;

public class PlannerObjectListFragment extends WorldPlannerBaseFragment {
    private static final String TYPE_TO_DISPLAY = "ENTITY_LIST_FRAGMENT_TYPE_TO_DISPLAY";

    private MasterPlannerObjectListAdapter adapter;

    public PlannerObjectListFragment() {
        // Required empty public constructor
    }

    public static PlannerObjectListFragment newInstance(ImportanceRelation.ImportantType type) {
        PlannerObjectListFragment fragment = new PlannerObjectListFragment();
        Bundle args = new Bundle();
        args.putSerializable(TYPE_TO_DISPLAY, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        ImportanceRelation.ImportantType typeToDisplay = (ImportanceRelation.ImportantType) arguments.getSerializable(TYPE_TO_DISPLAY);
        adapter = new MasterPlannerObjectListAdapter(typeToDisplay, getContext());
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

}
