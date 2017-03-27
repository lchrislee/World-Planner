package com.lchrislee.worldplanner.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lchrislee.worldplanner.Fragments.EntityDetailFragment;
import com.lchrislee.worldplanner.Models.Entity;
import com.lchrislee.worldplanner.Models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class ModelDetailActivity extends AppCompatActivity {

    public static String NEW = "MODEL_DETAIL_ACTIVITY_NEW";
    public static String TYPE = "MODEL_DETAIL_ACTIVITY_TYPE";
    public static int DELETE = 404;

    EntityDetailFragment fragment;

    private Entity.EntityType typeToDisplay = Entity.EntityType.None;
    private WorldPlannerBaseModel modelToDisplay = null;
    private boolean isNewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);

        Intent i = getIntent();
        typeToDisplay = (Entity.EntityType) i.getSerializableExtra(TYPE);
        isNewModel = i.getBooleanExtra(NEW, false);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fragment = EntityDetailFragment.newInstance(typeToDisplay, isNewModel);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_entity_detail_fragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        menu.findItem(R.id.menu_detail_character_add).setVisible(typeToDisplay == Entity.EntityType.Character);
        if (isNewModel)
        {
            menu.findItem(R.id.menu_detail_edit).setIcon(android.R.drawable.ic_menu_save);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.tag("Model Detail").d(Entity.getTypeString(typeToDisplay) + " - Clicked: " + item.getTitle());
        switch(item.getItemId())
        {
            case R.id.menu_detail_character_add:
                break;
            case R.id.menu_detail_edit:
                if (fragment.isEditing())
                {
                    item.setIcon(android.R.drawable.ic_menu_save);
                }
                else
                {
                    item.setIcon(android.R.drawable.ic_menu_edit);
                }
                supportInvalidateOptionsMenu();
                fragment.iconAction();
                break;
            case R.id.menu_detail_share:
                break;
            case R.id.menu_detail_delete:
                setResult(DELETE);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
