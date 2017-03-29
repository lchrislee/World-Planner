package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.fragments.AccountFragment;
import com.lchrislee.worldplanner.fragments.ChangeWorldFragment;
import com.lchrislee.worldplanner.fragments.CurrentWorldFragment;
import com.lchrislee.worldplanner.fragments.CurrentWorldTabFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class CurrentWorldActivity extends AppCompatActivity implements ChangeWorldFragment.FragmentSwap {

    private CurrentWorldFragment currentWorldFragment;
    private ChangeWorldFragment changeWorldFragment;
    private AccountFragment accountFragment;
    private ImageView headerWorldImage;
    private TextView headerWorldName;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_world);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("World Planner");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        currentWorldFragment = new CurrentWorldFragment();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.tag("Menu Click").d("title: %s on %s", item.getTitle(), CurrentWorldActivity.class.getSimpleName());

        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch(item.getItemId())
        {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case EntityDetailActivity.REQUEST_CODE_WORLD_DETAIL:
                if (resultCode == EntityDetailActivity.RESPONSE_CODE_DELETE)
                {
                    Timber.tag("CRUD").d("Deleted current world.");
                }
                break;
        }
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

                break;
            case R.id.menu_navigation_misc_account:
                if (accountFragment == null)
                {
                    accountFragment = new AccountFragment();
                }
                fragToShow = accountFragment;
                break;
            default:
                if (currentWorldFragment == null)
                {
                    currentWorldFragment = new CurrentWorldFragment();
                }
                fragToShow = currentWorldFragment;
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_world_current_frame, fragToShow)
                .commit();
        drawerLayout.closeDrawers();
    }

    @Override
    public void onWorldSwitch(int position) {
        // TODO: Update world displaying.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_world_current_frame, currentWorldFragment)
                .commit();
        navigationView.setCheckedItem(R.id.menu_navigation_world_current);
    }
}
