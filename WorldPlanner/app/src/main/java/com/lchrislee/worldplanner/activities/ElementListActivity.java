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
                    actionBar.setTitle("Characters");
                    break;
                case DataManager.LOCATION:
                    actionBar.setTitle("Locations");
                    break;
                case DataManager.ITEM:
                    actionBar.setTitle("Items");
                    break;
                case DataManager.GROUP:
                    actionBar.setTitle("Groups");
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
