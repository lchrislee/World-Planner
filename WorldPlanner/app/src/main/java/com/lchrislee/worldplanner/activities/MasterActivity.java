package com.lchrislee.worldplanner.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.lchrislee.worldplanner.adapters.MasterFragmentPagerAdapter;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.R;

import timber.log.Timber;

public class MasterActivity extends AppCompatActivity {

    private static final int WORLD_DETAIL_CODE = 100;
    public static final int RELATIONABLE_DETAIL_CODE = 200;

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
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        viewPager.setAdapter(new MasterFragmentPagerAdapter(getSupportFragmentManager()));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tablayout);
        tabLayout.setupWithViewPager(viewPager);

        // TODO: Replace this implementaion of FAB menu with custom one.
        // This is super deprecated and hard to use.
        View.OnClickListener toDetail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.tag("Main FAB").d(((FloatingActionButton) v).getLabelText());
                Intent i = new Intent(getApplicationContext(), ModelDetailActivity.class);
                i.putExtra(ModelDetailActivity.NEW, true);
                int requestCode;
                switch(v.getId())
                {
                    case R.id.activity_main_fab_character:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Character);
                        requestCode = RELATIONABLE_DETAIL_CODE;
                        break;
                    case R.id.activity_main_fab_location:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Location);
                        requestCode = RELATIONABLE_DETAIL_CODE;
                        break;
                    case R.id.activity_main_fab_item:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Item);
                        requestCode = RELATIONABLE_DETAIL_CODE;
                        break;
                    case R.id.activity_main_fab_plot:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.Plot);
                        requestCode = RELATIONABLE_DETAIL_CODE;
                        break;
                    default:
                        i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.None);
                        requestCode = WORLD_DETAIL_CODE;
                }
                startActivityForResult(i, requestCode);
            }
        };

        final FloatingActionButton character = (FloatingActionButton) findViewById(R.id.activity_main_fab_character);
        character.setOnClickListener(toDetail);
        final FloatingActionButton location = (FloatingActionButton) findViewById(R.id.activity_main_fab_location);
        location.setOnClickListener(toDetail);
        final FloatingActionButton item = (FloatingActionButton) findViewById(R.id.activity_main_fab_item);
        item.setOnClickListener(toDetail);
        final FloatingActionButton plot = (FloatingActionButton) findViewById(R.id.activity_main_fab_plot);
        plot.setOnClickListener(toDetail);
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
                Intent i = new Intent(getApplicationContext(), ModelDetailActivity.class);
                i.putExtra(ModelDetailActivity.TYPE, Relationship.RelationableType.None);
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
                if (resultCode == ModelDetailActivity.DELETE)
                {
                    Timber.tag("CRUD").d("Deleted current world.");
                }
                break;
        }
    }
}
