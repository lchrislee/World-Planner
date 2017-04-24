package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.detail.LocationDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.EventDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.character.CharacterBasicDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.character.CharacterTabFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.fragments.detail.GroupDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.ItemDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.WorldDetailFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.io.Serializable;

import timber.log.Timber;

public class EntityDetailActivity
        extends WorldPlannerBaseActivity
        implements CharacterTabFragment.CharacterDetailTabChange{

    public static final String INDEX = EntityDetailActivity.class.getSimpleName() + "_INDEX";
    public static final String TYPE = EntityDetailActivity.class.getSimpleName() + "_TYPE";

    private boolean isNewModel;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_entity_detail);
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        requestCode = i.getIntExtra(TYPE, 100);
        Timber.d("request code - " + requestCode);
        Serializable model;
        long index = i.getLongExtra(INDEX, -1);

        if (requestCode == DataManager.EVENT)
        {
            model = DataManager.getInstance().getEventAtIndex(index);
        }
        else if (requestCode == DataManager.WORLD)
        {
            model = DataManager.getInstance().getWorldAtIndex(index);
        }
        else
        {
            model = (Serializable) DataManager.getInstance().getElementAtIndex(requestCode, index);
        }

        isNewModel = model == null;
        toolbarState = isNewModel ? ToolbarState.Save : ToolbarState.Edit_Delete;
        previousState = toolbarState;

        switch(requestCode)
        {
            case DataManager.CHARACTER:
                if (model == null) {
                    fragment = CharacterBasicDetailFragment.newInstance(null);
                }
                else
                {
                    fragment = CharacterTabFragment.newInstance(model);
                    ((CharacterTabFragment) fragment).setListener(this);
                }
                break;
            case DataManager.GROUP:
                fragment = GroupDetailFragment.newInstance(model);
                break;
            case DataManager.ITEM:
                fragment = ItemDetailFragment.newInstance(model);
                break;
            case DataManager.LOCATION:
                fragment = LocationDetailFragment.newInstance(model);
                break;
            case DataManager.EVENT:
                fragment = EventDetailFragment.newInstance(model);
                break;
            case DataManager.WORLD:
                fragment = WorldDetailFragment.newInstance();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_entity_detail_fragment, (WorldPlannerBaseFragment)fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateToolbarTitle();
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
                    if (requestCode == DataManager.EVENT) {
                        i.putExtra(CurrentWorldActivity.RESULT_CODE_NEW_EVENT, id);
                    }
                    else {
                        i.putExtra(CurrentWorldActivity.RESULT_CODE_NEW_WORLD_ID, id);
                    }
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
        toolbarState = ((CharacterTabFragment) fragment).isShowingDetails() ?
                ToolbarState.Edit_Delete
                : ToolbarState.Empty;
        supportInvalidateOptionsMenu();
    }

    private void updateToolbarTitle()
    {
        actionBar.setTitle(getString(R.string.toolbar_detail, fragment.getTitle()));
    }

}
