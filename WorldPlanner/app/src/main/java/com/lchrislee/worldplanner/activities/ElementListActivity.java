package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.os.Bundle;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.ElementListFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.utility.ToolbarState;

public class ElementListActivity extends WorldPlannerBaseActivity {

    public static final String TYPE = "TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_element_list);
        super.onCreate(savedInstanceState);

        final Intent i = getIntent();
        final int typeToDisplay = i.getIntExtra(TYPE, -1);
        toolbarState = ToolbarState.Empty;

        if (actionBar != null)
        {
            switch(typeToDisplay)
            {
                case DataManager.CHARACTER:
                    actionBar.setTitle(R.string.toolbar_list_characters);
                    break;
                case DataManager.LOCATION:
                    actionBar.setTitle(R.string.toolbar_list_locations);
                    break;
                case DataManager.ITEM:
                    actionBar.setTitle(R.string.toolbar_list_items);
                    break;
                case DataManager.GROUP:
                    actionBar.setTitle(R.string.toolbar_list_groups);
                    break;
            }
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        final ElementListFragment fragment = ElementListFragment.newInstance(typeToDisplay);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_element_list_frame, fragment).commit();
    }
}
