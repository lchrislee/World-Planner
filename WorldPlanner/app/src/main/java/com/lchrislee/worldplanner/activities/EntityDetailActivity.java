package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.CharacterDetail.CharacterDetailFragment;
import com.lchrislee.worldplanner.fragments.CharacterDetail.CharacterTabFragment;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.DetailFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.io.Serializable;
import java.util.ArrayList;

import timber.log.Timber;

public class EntityDetailActivity extends WorldPlannerBaseActivity implements CharacterTabFragment.CharacterDetailTabChange{

    public static final String INDEX = EntityDetailActivity.class.getSimpleName() + "_INDEX";
    public static final String TYPE = EntityDetailActivity.class.getSimpleName() + "_TYPE";

    private ToolbarSupportingFragment fragment;

    private ToolbarState toolbarState;
    private ToolbarState previousState;

    private boolean isNewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        Intent i = getIntent();
        int requestCode = i.getIntExtra(TYPE, 100);
        Timber.d("request code - " + requestCode);
        Serializable model;
        long index = i.getLongExtra(INDEX, -1);

        if (requestCode == DataManager.NOT_WORLD)
        {
            model = (Serializable) DataManager.getInstance().getElementAtIndex(index);
            requestCode = DataManager.getInstance().getElementTypeAtIndex((int) index);
        }
        else
        {
            model = DataManager.getInstance().getWorldAtIndex(index);
        }

        isNewModel = model == null;
        toolbarState = isNewModel ? ToolbarState.Save : ToolbarState.Edit_Delete;
        previousState = toolbarState;

        if (requestCode == DataManager.CHARACTER)
        {
            if (isNewModel)
            {
                fragment = CharacterDetailFragment.newInstance(null);
            }
            else {
                fragment = CharacterTabFragment.newInstance(model);
                ((CharacterTabFragment) fragment).setListener(this);
            }
        }
        else
        {
            fragment = DetailFragment.newInstance(requestCode, model);
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        ArrayList<Integer> hiddenIds = toolbarState.getHiddenIds();
        for (int hideID : hiddenIds)
        {
            menu.findItem(hideID).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_edit:
                fragment.editAction();
                previousState = toolbarState;
                toolbarState = ToolbarState.Save;
                break;
            case R.id.menu_save:
                long id = fragment.editAction();
                if (isNewModel)
                {
                    Intent i = new Intent();
                    i.putExtra(CurrentWorldActivity.RESULT_CODE_NEW_WORLD_ID, id);
                    setResult(RESULT_OK, i);
                    finish();
                    return true;
                }
                toolbarState = previousState;
                break;
            case R.id.menu_delete:
                StoryElement model = fragment.getModel();
                if (model != null) {
                    DataManager.getInstance().delete(model);
                }
                finish();
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCharacterTabSwitch() {
        toolbarState = ((CharacterTabFragment) fragment).isShowingDetails() ? ToolbarState.Edit_Delete : ToolbarState.Empty;
        supportInvalidateOptionsMenu();
    }
}
