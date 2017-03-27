package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.MasterTabFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class MasterActivity extends AppCompatActivity {

    private WorldPlannerBaseFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("World Planner");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        fragment = new MasterTabFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_master_frame, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.tag("Menu Click").d("title: %s on %s", item.getTitle(), MasterActivity.class.getSimpleName());
        switch(item.getItemId())
        {
            case R.id.menu_main_world:
                Intent i = new Intent(getApplicationContext(), ModelDetailActivity.class);
                i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.None);
                startActivityForResult(i, ModelDetailActivity.REQUEST_CODE_WORLD_DETAIL);
                break;
            case R.id.menu_main_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case ModelDetailActivity.REQUEST_CODE_WORLD_DETAIL:
                if (resultCode == ModelDetailActivity.RESPONSE_CODE_DELETE)
                {
                    Timber.tag("CRUD").d("Deleted current world.");
                }
                break;
        }
    }
}
