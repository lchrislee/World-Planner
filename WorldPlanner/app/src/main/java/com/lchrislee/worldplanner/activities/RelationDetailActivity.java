package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.fragments.CharacterDetail.RelationDetailFragment;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.util.ArrayList;

public class RelationDetailActivity extends WorldPlannerBaseActivity {
    public static final int REQUEST_CODE_NEW = 100;
    public static final String RELATIONSHIP = "RELATIONDETAILACTIVITY_RELATIONSHIP";

    private RelationDetailFragment fragment;

    private ToolbarState toolbarState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_relation);

        Intent i = getIntent();
        Relationship relationship = (Relationship) i.getSerializableExtra(RELATIONSHIP);

        toolbarState = relationship == null ? ToolbarState.Save : ToolbarState.Edit_Delete;

        fragment = RelationDetailFragment.newInstance(relationship);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_relation_detail_frame, fragment).commit();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("Relationship");
            actionBar.setDisplayShowTitleEnabled(true);
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
                break;
            case R.id.menu_share: // Will not happen.
                break;
            case R.id.menu_delete:
                // TODO: Delete relation.
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }
}
