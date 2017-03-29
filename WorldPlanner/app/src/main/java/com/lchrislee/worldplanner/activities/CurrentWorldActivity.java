package com.lchrislee.worldplanner.activities;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.fragments.AccountFragment;
import com.lchrislee.worldplanner.fragments.ChangeWorldFragment;
import com.lchrislee.worldplanner.fragments.CurrentWorld.CurrentWorldFragment;
import com.lchrislee.worldplanner.fragments.CurrentWorld.CurrentWorldTabFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.utility.ToolbarState;

import java.util.ArrayList;

import timber.log.Timber;

public class CurrentWorldActivity extends WorldPlannerBaseActivity implements ChangeWorldFragment.FragmentSwap, CurrentWorldFragment.WorldTabChange {

    private CurrentWorldFragment currentWorldFragment;
    private ChangeWorldFragment changeWorldFragment;
    private AccountFragment accountFragment;
    private ImageView headerWorldImage;
    private TextView headerWorldName;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ToolbarState toolbarState = ToolbarState.Edit;
    private ToolbarState previousState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_world);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("StoryWorld Planner");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        currentWorldFragment = new CurrentWorldFragment();
        currentWorldFragment.setTabChangeListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_world_current_frame, currentWorldFragment).addToBackStack(CurrentWorldTabFragment.class.getSimpleName()).commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_world_current_drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        navigationView = (NavigationView) findViewById(R.id.activity_world_current_navigationview);

        View header = navigationView.inflateHeaderView(R.layout.layout_navigation_world_current_header);
        headerWorldImage = (ImageView) header.findViewById(R.id.layout_navigation_world_current_image);
        headerWorldName = (TextView) header.findViewById(R.id.layout_navigation_world_current_name);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        ArrayList<Integer> itemsToHide = toolbarState.getHiddenIds();
        for (int hide : itemsToHide)
        {
            menu.findItem(hide).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.tag(CurrentWorldActivity.class.getSimpleName()).d("Menu click - %s", item.getTitle());

        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch(item.getItemId())
        {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_edit:
                previousState = toolbarState;
                toolbarState = ToolbarState.Save;
                Timber.d("currentWorldFragment is not null: " + (currentWorldFragment == null));
                if (currentWorldFragment != null) {
                    currentWorldFragment.iconAction();
                }
                break;
            case R.id.menu_save:
                toolbarState = previousState;
                if (currentWorldFragment != null) {
                    currentWorldFragment.iconAction();
                }
                break;
            case R.id.menu_share: // Will not happen.
                break;
            case R.id.menu_delete: // Will not happen.
                break;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private void selectDrawerItem(@NonNull MenuItem item)
    {
        Timber.tag("Drawer").d("Selected item: " + item.getTitle());
        WorldPlannerBaseFragment fragToShow;
        switch(item.getItemId())
        {
            case R.id.menu_navigation_world_change:
                if (changeWorldFragment == null)
                {
                    changeWorldFragment = new ChangeWorldFragment();
                    changeWorldFragment.setListener(this);
                }
                fragToShow = changeWorldFragment;
                toolbarState = ToolbarState.Empty;
                break;
            case R.id.menu_navigation_misc_account:
                if (accountFragment == null)
                {
                    accountFragment = new AccountFragment();
                }
                fragToShow = accountFragment;
                toolbarState = ToolbarState.Empty;
                break;
            default:
                toolbarState = ToolbarState.Edit;
                if (currentWorldFragment == null)
                {
                    currentWorldFragment = new CurrentWorldFragment();
                    currentWorldFragment.setTabChangeListener(this);
                }

                fragToShow = currentWorldFragment;
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_world_current_frame, fragToShow)
                .addToBackStack(fragToShow.getClass().getSimpleName())
                .commit();
        drawerLayout.closeDrawers();
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onWorldSwitch(int position) {
        // TODO: Update world displaying.
        if (currentWorldFragment == null)
        {
            currentWorldFragment = new CurrentWorldFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_world_current_frame, currentWorldFragment)
                .addToBackStack(currentWorldFragment.getClass().getSimpleName())
                .commit();
        navigationView.setCheckedItem(R.id.menu_navigation_world_current);
        toolbarState = ToolbarState.Edit;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onWorldTabChanged() {
        toolbarState = currentWorldFragment.isShowingWorld() ? ToolbarState.Edit : ToolbarState.Empty;
        supportInvalidateOptionsMenu();
    }
}