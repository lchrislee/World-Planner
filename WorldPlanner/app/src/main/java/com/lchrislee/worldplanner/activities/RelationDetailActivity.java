package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.fragments.RelationDetailFragment;

public class RelationDetailActivity extends WorldPlannerBaseActivity {
    public static final int REQUEST_CODE_NEW = 100;
    public static final String RELATIONSHIP = "RELATIONDETAILACTIVITY_RELATIONSHIP";

    private RelationDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_relation);

        Intent i = getIntent();
        Relationship relationship = (Relationship) i.getSerializableExtra(RELATIONSHIP);
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
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        menu.findItem(R.id.menu_detail_share).setVisible(false);
        menu.findItem(R.id.menu_detail_edit).setIcon(android.R.drawable.ic_menu_save);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_detail_edit:
                break;
            case R.id.menu_detail_delete:
                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
