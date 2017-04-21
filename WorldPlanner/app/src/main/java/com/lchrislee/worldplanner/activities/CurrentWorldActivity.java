package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.CurrentWorld.ChangeWorldFragment;
import com.lchrislee.worldplanner.fragments.CurrentWorld.CurrentWorldFragment;
import com.lchrislee.worldplanner.fragments.CurrentWorld.CurrentWorldTabFragment;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.util.ArrayList;

public class CurrentWorldActivity extends WorldPlannerBaseActivity implements ChangeWorldFragment.FragmentSwap, CurrentWorldFragment.WorldTabChange {

    public static final String RESULT_CODE_NEW_WORLD_ID = "CurrentWorldActivity_RESULT_CODE_NEW_WORLD_ID";
    public static final String RESULT_CODE_NEW_PLOT = "CurrentWorldActivity_RESULT_CODE_NEW_PLOT";

    private CurrentWorldFragment currentWorldFragment;
    private ChangeWorldFragment changeWorldFragment;

    private ActionBar actionBar;

    private ToolbarState toolbarState = ToolbarState.Edit_Change;
    private ToolbarState previousState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_world);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("Current World");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        currentWorldFragment = new CurrentWorldFragment();
        currentWorldFragment.setTabChangeListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_world_current_frame, currentWorldFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        ArrayList<Integer> itemsToHide = toolbarState.getHiddenIds();
        for (int hide : itemsToHide)
        {
            menu.findItem(hide).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.menu_edit:
                previousState = toolbarState;
                toolbarState = ToolbarState.Save;
                if (currentWorldFragment != null) {
                    currentWorldFragment.iconAction();
                }
                break;
            case R.id.menu_world_change:
                if (changeWorldFragment == null)
                {
                    changeWorldFragment = new ChangeWorldFragment();
                    changeWorldFragment.setListener(this);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_world_current_frame, changeWorldFragment)
                        .addToBackStack(changeWorldFragment.getClass().getSimpleName())
                        .commit();
                toolbarState = ToolbarState.Empty;
                if (actionBar != null)
                {
                    actionBar.setTitle("Change worlds");
                }
                break;
            case R.id.menu_save:
                toolbarState = previousState;
                if (currentWorldFragment != null) {
                    currentWorldFragment.iconAction();
                }
                break;
            case R.id.menu_delete: // Will not happen.
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case DataManager.WORLD:
                    long newWorldIndex = data.getLongExtra(RESULT_CODE_NEW_WORLD_ID, -1);
                    DataManager.getInstance().changeWorldToIndex(newWorldIndex - 1);
                    onWorldSwitch();
                    break;
                case DataManager.PLOT:
                    toolbarState = ToolbarState.Save;
            }
        }

    }

    @Override
    public void onWorldSwitch() {
        if (currentWorldFragment == null)
        {
            currentWorldFragment = new CurrentWorldFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_world_current_frame, currentWorldFragment)
                .addToBackStack(currentWorldFragment.getClass().getSimpleName())
                .commit();
        toolbarState = ToolbarState.Edit_Change;
        actionBar.setTitle("Current World");
        supportInvalidateOptionsMenu();
        DataManager.getInstance().retainSelectedWorld(this);
    }

    @Override
    public void updateToolbarWorldTabChange(boolean editable) {
        toolbarState = editable ? ToolbarState.Edit_Change : ToolbarState.Empty;
        actionBar.setTitle("Current World");
        supportInvalidateOptionsMenu();
    }
}
