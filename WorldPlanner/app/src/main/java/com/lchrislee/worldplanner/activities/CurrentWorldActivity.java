package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.ChangeWorldFragment;
import com.lchrislee.worldplanner.fragments.current_world.CurrentWorldFragment;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.utility.ToolbarState;

public class CurrentWorldActivity
        extends WorldPlannerBaseActivity
        implements ChangeWorldFragment.FragmentSwap, CurrentWorldFragment.WorldTabChange {

    public static final String RESULT_CODE_NEW_WORLD_ID = "CurrentWorldActivity_RESULT_CODE_NEW_WORLD_ID";
    public static final String RESULT_CODE_NEW_EVENT = "CurrentWorldActivity_RESULT_CODE_NEW_PLOT";

    private CurrentWorldFragment currentWorldFragment;
    private ChangeWorldFragment changeWorldFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_current_world);
        super.onCreate(savedInstanceState);
        toolbarState = ToolbarState.Edit_Change;

        if (actionBar != null)
        {
            actionBar.setTitle(R.string.toolbar_current_world);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        currentWorldFragment = new CurrentWorldFragment();
        currentWorldFragment.setTabChangeListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_world_current_frame, currentWorldFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        supportInvalidateOptionsMenu();
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
                        .commit();
                toolbarState = ToolbarState.Empty;
                if (actionBar != null)
                {
                    actionBar.setTitle(R.string.toolbar_change_world);
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
                case DataManager.EVENT:
                    toolbarState = ToolbarState.Save;
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (currentWorldFragment.isVisible()) {
            actionBar.setTitle(R.string.toolbar_current_world);
            toolbarState = ToolbarState.Edit_Change;
            supportInvalidateOptionsMenu();
        }
        else
        {
            actionBar.setTitle(R.string.toolbar_change_world);
            toolbarState = ToolbarState.Empty;
            supportInvalidateOptionsMenu();
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
                .commit();
        toolbarState = ToolbarState.Edit_Change;
        actionBar.setTitle(R.string.toolbar_current_world);
        supportInvalidateOptionsMenu();
        DataManager.getInstance().retainSelectedWorld(this);
    }

    @Override
    public void updateToolbarWorldTabChange(boolean editable) {
        toolbarState = editable ? ToolbarState.Edit_Change : ToolbarState.Empty;
        actionBar.setTitle(R.string.toolbar_current_world);
        supportInvalidateOptionsMenu();
    }
}
