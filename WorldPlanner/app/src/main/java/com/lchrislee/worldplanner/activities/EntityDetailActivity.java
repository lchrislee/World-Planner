package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.CharacterDetail.CharacterTabFragment;
import com.lchrislee.worldplanner.fragments.EditableFragment;
import com.lchrislee.worldplanner.fragments.DetailFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.utility.ToolbarState;

public class EntityDetailActivity extends WorldPlannerBaseActivity {
    public static final int REQUEST_CODE_WORLD_DETAIL = 100;
    public static final int REQUEST_CODE_RELATIONABLE_DETAIL = 200;

    public static final int RESPONSE_CODE_DELETE = 404;

    public static final String NEW = "MODEL_DETAIL_ACTIVITY_NEW";
    public static final String TYPE = "MODEL_DETAIL_ACTIVITY_TYPE";

    private EditableFragment fragment;

    private WorldPlannerBaseModel modelToDisplay = null;

    private ToolbarState toolbarState;

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
        boolean isNewModel = i.getBooleanExtra(NEW, false);
        toolbarState = isNewModel ? ToolbarState.Save : ToolbarState.Edit_Share_Delete;

        if (typeToDisplay == ImportanceRelation.ImportantType.Character)
        {
            fragment = new CharacterTabFragment();
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
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        switch(toolbarState)
        {
            case Edit:
                break;
            case Edit_Delete:
                break;
            case Save:
                break;
            case Empty:
                break;
            case Edit_Share_Delete:
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_edit:
                fragment.iconAction();
                break;
            case R.id.menu_save:
                break;
            case R.id.menu_share:
                break;
            case R.id.menu_delete:
                setResult(RESPONSE_CODE_DELETE);
                finish();
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }
}
