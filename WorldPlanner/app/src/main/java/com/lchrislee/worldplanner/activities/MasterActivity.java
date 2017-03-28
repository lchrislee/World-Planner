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
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.fragments.AccountFragment;
import com.lchrislee.worldplanner.fragments.ChangeWorldFragment;
import com.lchrislee.worldplanner.fragments.MasterTabFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class MasterActivity extends AppCompatActivity {

    private MasterTabFragment masterTabFragment;
    private ChangeWorldFragment changeWorldFragment;
    private AccountFragment accountFragment;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("World Planner");
            actionBar.setDisplayShowTitleEnabled(true);
        }

        masterTabFragment = new MasterTabFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_master_frame, masterTabFragment).addToBackStack(MasterTabFragment.class.getSimpleName()).commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_master_drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.activity_master_navigationview);
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
        getMenuInflater().inflate(R.menu.menu_master_tab, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.tag("Menu Click").d("title: %s on %s", item.getTitle(), MasterActivity.class.getSimpleName());

        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch(item.getItemId())
        {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_master_tab_world:
                Intent i = new Intent(getApplicationContext(), ModelDetailActivity.class);
                i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.None);
                startActivityForResult(i, ModelDetailActivity.REQUEST_CODE_WORLD_DETAIL);
                break;
            case R.id.menu_master_tab_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case ModelDetailActivity.REQUEST_CODE_WORLD_DETAIL:
                if (resultCode == ModelDetailActivity.RESPONSE_CODE_DELETE)
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
                if (masterTabFragment == null)
                {
                    masterTabFragment = new MasterTabFragment();
                }
                fragToShow = masterTabFragment;
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_master_frame, fragToShow)
                .commit();
        drawerLayout.closeDrawers();
    }

}
