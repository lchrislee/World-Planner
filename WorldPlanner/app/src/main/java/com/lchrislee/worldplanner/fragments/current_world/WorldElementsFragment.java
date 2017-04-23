package com.lchrislee.worldplanner.fragments.current_world;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.adapters.WorldElementListAdapter;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.managers.DataManager;

import timber.log.Timber;

public class WorldElementsFragment extends WorldPlannerBaseFragment {
    private FloatingActionMenu floatingActionMenu;
    private WorldElementListAdapter adapter;
    private ImageView empty;

    public WorldElementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_world_current_tab, container, false);

        adapter = new WorldElementListAdapter(getActivity());

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.fragment_master_list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        empty = (ImageView) v.findViewById(R.id.fragment_master_empty);

        setupFAB(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
            int size = adapter.getItemCount();
            if (size == 0)
            {
                empty.setVisibility(View.VISIBLE);
            }
            else
            {
                empty.setVisibility(View.GONE);
            }
        }
    }

    private void setupFAB(@NonNull View v)
    {
        floatingActionMenu = (FloatingActionMenu) v.findViewById(R.id.fragment_master_fab_menu);

        // TODO: Replace this implementaion of FAB menu with custom one.
        // This is super deprecated and hard to use.
        View.OnClickListener toDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.tag("Main FAB").d(((FloatingActionButton) v).getLabelText());

                int requestCode;
                switch(v.getId())
                {
                    case R.id.fragment_master_fab_character:
                        requestCode = DataManager.CHARACTER;
                        break;
                    case R.id.fragment_master_fab_location:
                        requestCode = DataManager.LOCATION;
                        break;
                    case R.id.fragment_master_fab_item:
                        requestCode = DataManager.ITEM;
                        break;
                    case R.id.fragment_master_fab_group:
                        requestCode = DataManager.GROUP;
                        break;
                    default:
                        requestCode = DataManager.WORLD;
                }
                floatingActionMenu.close(true);

                Intent i = new Intent(getContext(), EntityDetailActivity.class);
                i.putExtra(EntityDetailActivity.TYPE, requestCode);
                startActivityForResult(i, requestCode);
            }
        };

        final FloatingActionButton character = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_character);
        character.setOnClickListener(toDetail);
        final FloatingActionButton location = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_location);
        location.setOnClickListener(toDetail);
        final FloatingActionButton item = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_item);
        item.setOnClickListener(toDetail);
        final FloatingActionButton group = (FloatingActionButton) v.findViewById(R.id.fragment_master_fab_group);
        group.setOnClickListener(toDetail);
    }

}