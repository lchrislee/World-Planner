package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.detail.CharacterDetail.RelationDetailFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.util.ArrayList;

public class RelationDetailActivity extends WorldPlannerBaseActivity {
    public static final int REQUEST_CODE_NEW = 100;
    public static final String REL_INDEX = "RELATIONDETAILACTIVITY_REL_ INDEX";
    public static final String OWNER_INDEX = "RELATIONDETAILACTIVITY_CHAR_INDEX";

    private RelationDetailFragment fragment;

    private ToolbarState toolbarState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_relation);

        Intent i = getIntent();
        int relationshipIndex = i.getIntExtra(REL_INDEX, -1);
        int ownerIndex = i.getIntExtra(OWNER_INDEX, -1);

        toolbarState = relationshipIndex == -1 ? ToolbarState.Save : ToolbarState.Edit_Delete;

        fragment = RelationDetailFragment.newInstance(relationshipIndex, ownerIndex);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_relation_detail_frame, fragment).commit();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        ArrayList<Integer> hiddenIds = toolbarState.getHiddenIds();
        for (int id : hiddenIds)
        {
            menu.findItem(id).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_edit:
                toolbarState = ToolbarState.Save;
                fragment.editAction();
                break;
            case R.id.menu_save:
                toolbarState = ToolbarState.Edit_Delete;
                fragment.editAction();
                finish();
                break;
            case R.id.menu_delete:
                DataManager.getInstance().delete(fragment.getModel());
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }
}
