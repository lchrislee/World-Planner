package com.lchrislee.worldplanner.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lchrislee.worldplanner.Adapters.EntityFragmentPagerAdapter;
import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class MasterActivity extends AppCompatActivity {

    private static final int WORLD_DETAIL_CODE = 100;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle("World Planner");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        viewPager.setAdapter(new EntityFragmentPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.activity_main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.tag("Menu Click").d("title: %s on %s", item.getTitle(), MasterActivity.class.getSimpleName());
        switch(item.getItemId())
        {
            case R.id.menu_main_world:
                Intent i = new Intent(getApplicationContext(), WorldDetailActivity.class);
                startActivityForResult(i, WORLD_DETAIL_CODE);
                break;
            case R.id.menu_main_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case WORLD_DETAIL_CODE:
                if (resultCode == WorldDetailActivity.DELETE)
                {
                    // TODO: Modify the activity because everything got wiped!
                    Timber.tag("CRUD").d("Deleted current world.");
                }
                break;
        }
    }
}
