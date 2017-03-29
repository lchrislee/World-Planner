package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.CharacterDetailFragment;
import com.lchrislee.worldplanner.fragments.EditableFragment;
import com.lchrislee.worldplanner.fragments.DetailFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;

public class EntityDetailActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_WORLD_DETAIL = 100;
    public static final int REQUEST_CODE_RELATIONABLE_DETAIL = 200;

    public static final int RESPONSE_CODE_DELETE = 404;

    public static final String NEW = "MODEL_DETAIL_ACTIVITY_NEW";
    public static final String TYPE = "MODEL_DETAIL_ACTIVITY_TYPE";

    private EditableFragment fragment;

    private WorldPlannerBaseModel modelToDisplay = null;
    private boolean isNewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        Intent i = getIntent();
        ImportanceRelation.ImportantType typeToDisplay = (ImportanceRelation.ImportantType) i.getSerializableExtra(TYPE);
        if (typeToDisplay == null)
        {
            typeToDisplay = ImportanceRelation.ImportantType.None;
        }
        isNewModel = i.getBooleanExtra(NEW, false);

        if (typeToDisplay == ImportanceRelation.ImportantType.Character)
        {
            fragment = new CharacterDetailFragment();
        }
        else
        {
            fragment = DetailFragment.newInstance(typeToDisplay, isNewModel);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.activity_entity_detail_fragment, (WorldPlannerBaseFragment)fragment).commit();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            String title = ImportanceRelation.getImportantTypeString(typeToDisplay);
            if (title == null)
            {
                title = "World";
            }
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
