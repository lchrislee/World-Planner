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
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.ImportanceRelation;
import com.lchrislee.worldplanner.models.StoryItem;
import com.lchrislee.worldplanner.models.StoryLocation;
import com.lchrislee.worldplanner.models.StoryPlot;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryWorld;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.util.ArrayList;

import timber.log.Timber;

public class EntityDetailActivity extends WorldPlannerBaseActivity implements CharacterTabFragment.CharacterDetailTabChange{
    public static final int REQUEST_CODE_WORLD = 100;
    public static final int REQUEST_CODE_CHARACTER = 200;
    public static final int REQUEST_CODE_LOCATION = 300;
    public static final int REQUEST_CODE_ITEM = 400;
    public static final int REQUEST_CODE_PLOT = 500;

    public static final String INDEX = EntityDetailActivity.class.getSimpleName() + "_INDEX";
    public static final String TYPE = EntityDetailActivity.class.getSimpleName() + "_TYPE";

    private ToolbarSupportingFragment fragment;

    private ToolbarState toolbarState;
    private ToolbarState previousState;

    private int index;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        ImportanceRelation.ImportantType typeToDisplay;

        Intent i = getIntent();
        index = i.getIntExtra(INDEX, -1);
        requestCode = i.getIntExtra(TYPE, 100);
        Timber.d("index - " + index);
        Timber.tag(getClass().getSimpleName()).d("request code - " + requestCode);
        switch(requestCode)
        {
            case REQUEST_CODE_CHARACTER:
                typeToDisplay = ImportanceRelation.ImportantType.Character;
                break;
            case REQUEST_CODE_LOCATION:
                typeToDisplay = ImportanceRelation.ImportantType.Location;
                break;
            case REQUEST_CODE_ITEM:
                typeToDisplay = ImportanceRelation.ImportantType.Item;
                break;
            case REQUEST_CODE_PLOT:
                typeToDisplay = ImportanceRelation.ImportantType.Plot;
                break;
            default:
                typeToDisplay = ImportanceRelation.ImportantType.None;
                break;
        }

        boolean isNewModel = index == -1;
        toolbarState = isNewModel ? ToolbarState.Save : ToolbarState.Edit_Share_Delete;
        previousState = toolbarState;

        if (typeToDisplay == ImportanceRelation.ImportantType.Character)
        {
            if (isNewModel)
            {
                fragment = CharacterDetailFragment.newInstance(true, index);
            }
            else {
                fragment = CharacterTabFragment.newInstance(index);
                ((CharacterTabFragment) fragment).setListener(this);
            }
        }
        else
        {
            fragment = DetailFragment.newInstance(typeToDisplay, isNewModel, index);
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
                Timber.tag(getClass().getSimpleName()).d("onOptionsItemSelected save");
                fragment.editAction();
                if (index == -1)
                {
                    setResult(-1);
                    finish();
                    return true;
                }
                toolbarState = previousState;
                break;
            case R.id.menu_share:
                // TODO: Implement share by pulling from the Fragment.
                WorldPlannerBaseModel modelToShare = fragment.getModel();
                break;
            case R.id.menu_delete:
                // TODO: Implement deletion.
                WorldPlannerBaseModel modelToDelete = fragment.getModel();
                finish();
                break;
        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCharacterTabSwitch() {
        toolbarState = ((CharacterTabFragment) fragment).isShowingDetails() ? ToolbarState.Edit_Share_Delete : ToolbarState.Empty;
        supportInvalidateOptionsMenu();
    }
}
