package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.PlannerObjectDetailFragment;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;

public class ModelDetailActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_WORLD_DETAIL = 100;
    public static final int REQUEST_CODE_RELATIONABLE_DETAIL = 200;

    public static final int RESPONSE_CODE_DELETE = 404;

    public static final String NEW = "MODEL_DETAIL_ACTIVITY_NEW";
    public static final String TYPE = "MODEL_DETAIL_ACTIVITY_TYPE";

    private PlannerObjectDetailFragment fragment;

    private Relationship.RelationableType typeToDisplay = Relationship.RelationableType.None;
    private WorldPlannerBaseModel modelToDisplay = null;
    private boolean isNewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);

        Intent i = getIntent();
        typeToDisplay = (Relationship.RelationableType) i.getSerializableExtra(TYPE);
        isNewModel = i.getBooleanExtra(NEW, false);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Details");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        fragment = PlannerObjectDetailFragment.newInstance(typeToDisplay, isNewModel);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_entity_detail_fragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        menu.findItem(R.id.menu_detail_character_add).setVisible(typeToDisplay == Relationship.RelationableType.Character);
        if (isNewModel)
        {
            menu.findItem(R.id.menu_detail_edit).setIcon(android.R.drawable.ic_menu_save);
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
                setResult(RESPONSE_CODE_DELETE);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}