package com.lchrislee.worldplanner.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.Models.Entity;
import com.lchrislee.worldplanner.R;

public class EntityDetailActivity extends AppCompatActivity {

    public static String TYPE = "ENTITY_DETAIL_ACTIVITY_TYPE";

    private Toolbar toolbar;

    private Entity.EntityType typeToDisplay = Entity.EntityType.None;
    private Entity entityToDisplay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        Intent i = getIntent();
        typeToDisplay = (Entity.EntityType) i.getSerializableExtra(TYPE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            if (entityToDisplay != null)
            {
                actionBar.setTitle(entityToDisplay.getName());
            }
            else
            {
                String title = "Untitled " + Entity.getTypeString(typeToDisplay);
                actionBar.setTitle(title);
            }
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (typeToDisplay != Entity.EntityType.None)
        {
            getMenuInflater().inflate(R.menu.menu_detail, menu);
            menu.findItem(R.id.menu_detail_character_add).setVisible(typeToDisplay == Entity.EntityType.Character);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_detail_character_add:
                break;
            case R.id.menu_detail_edit:
                break;
            case R.id.menu_detail_share:
                break;
            case R.id.menu_detail_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
