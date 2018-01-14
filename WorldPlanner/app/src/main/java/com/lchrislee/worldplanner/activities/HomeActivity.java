package com.lchrislee.worldplanner.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.HomeWorldFragmentAdapter;
import com.lchrislee.worldplanner.fragments.HomeWorldFragment;
import com.lchrislee.worldplanner.fragments.HomeWorldFragment_;
import com.lchrislee.worldplanner.models.World;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * The main place for users. The user can view all their worlds and all entities in their worlds.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity
    extends BaseFragmentActivity
{
    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    @ViewById NavigationView homeNavigation;

    @ViewById DrawerLayout homeDrawer;

    @ViewById Toolbar toolbar;

    @ViewById ViewPager homeViewPager;

    @ViewById TabLayout homeTabs;

    private ImageView headerImage;

    private TextView headerTitle;

    private ActionBarDrawerToggle mDrawerToggle;

    private List<HomeWorldFragment> fragments = new ArrayList<>();

    private List<World> worlds = new ArrayList<>();

    /**
     * Perform additional actions that require references to the injected views from the layout file.
     * Only actions that are time sensitive should be done here.
     *
     * For example, Toolbars must be assigned to the ActionBar before
     * {@link #onCreateOptionsMenu(Menu)} is called to actually inflate menu items.
     *
     * @param savedInstanceState Any persisted state from a configuration change.
     */
    @Override
    protected void onPostCreate (@Nullable final Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        initializeToolbar();
    }

    /**
     * Perform any additional programmatic setup for the Toolbar.
     */
    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_title);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull final MenuItem item)
    {
        Log.d(LOG_TAG, "Selected menu item: " + item.getTitle());
        switch(item.getItemId()) {
            case android.R.id.home:
                homeDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.home_navigation_jump:
                Toast.makeText(this, "JUMP", Toast.LENGTH_SHORT).show();
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.home_navigation_new_world:
                Toast.makeText(this, "NEW WORLD", Toast.LENGTH_SHORT).show();
                homeDrawer.closeDrawer(GravityCompat.START);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onConfigurationChanged (@NonNull final Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @AfterViews
    protected void viewDidLoad () {
        initializeTabs();
        initializeNavigation();
    }

    /**
     * Perform any additional programmatic setup for the Navigation Drawer.
     */
    private void initializeNavigation () {
        // Synchronize Navigation Drawer state with Toolbar to coordinate hamburger menu icon.
        mDrawerToggle = new ActionBarDrawerToggle(this, homeDrawer, toolbar, 0, 0);
        mDrawerToggle.syncState();
        homeDrawer.addDrawerListener(mDrawerToggle);

        // Add click listeners and decorations to the main Navigation Drawer view.
        homeNavigation.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        final NavigationMenuView homeNavigationMenu = (NavigationMenuView) homeNavigation.getChildAt(0);
        homeNavigationMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Inflate Navigation Drawer header view and gain references to state dependent views.
        final View headerView = homeNavigation.inflateHeaderView(R.layout.layout_home_navigation_header);
        headerImage = headerView.findViewById(R.id.homeNavigationHeaderImage);
        headerTitle = headerView.findViewById(R.id.homeNavigationHeaderTitle);
        switchToWorld(0, worlds.get(0).id());
    }

    /**
     * Perform any additional programmatic setup for the View Pager.
     * Temporarily creates an array of {@link World} objects since I do not persist items currently.
     */
    private void initializeTabs() {
        // TODO 1/14/18: Update this when a persisted World model exists.
        for (int i = 0; i < 10; ++i) {
            final World currentWorld = new World();
            currentWorld.updateName("World " + i);
            if (i % 2 == 0)
            {
                currentWorld.updateDescription("This is world: " + i + "\nI hope you like it here.\nIt's such a nice place.");
            } else {
                currentWorld.updateDescription("This is world: " + i);
            }
            currentWorld.setId(i);
            fragments.add(HomeWorldFragment_.builder().world(currentWorld).build());
            worlds.add(currentWorld);
        }

        homeViewPager.setAdapter(
            new HomeWorldFragmentAdapter(getSupportFragmentManager(),fragments, worlds));

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled (final int position,
                                        final float positionOffset,
                                        final int positionOffsetPixels) { /* No actions taken. */ }

            @Override
            public void onPageSelected (final int position)
            {
                // TODO 1/14/18: Update this when a persisted World model exists.
                Toast.makeText(HomeActivity.this,
                    "Selected world: " + worlds.get(position).id(),
                    Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "Selected world tab: " + position);
                switchToWorld(position, worlds.get(position).id());
            }

            @Override
            public void onPageScrollStateChanged (final int state) { /* No actions taken. */ }
        });
        homeTabs.setupWithViewPager(homeViewPager, true);
    }

    @Click({R.id.homeSettings})
    protected void onHomeSettingsClicked() {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        homeDrawer.closeDrawer(GravityCompat.START);
    }

    @Click({R.id.homeFeedback})
    protected void onHomeFeedbackClickeD() {
        Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
        homeDrawer.closeDrawer(GravityCompat.START);
    }

    @Click({R.id.homeAbout})
    protected void onHomeAboutClicked() {
        Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        homeDrawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Change focus to a specific {@link World} in the View Pager.
     * TODO 1/14/18: Update this when an established persistent World model exists.
     *
     * @param pagerPosition The position in the View Pager that the selected {@link World} would be.
     * @param worldId The specific {@link World} to focus on.
     */
    private void switchToWorld(final int pagerPosition, final long worldId) {
        if (homeViewPager.getCurrentItem() != pagerPosition) {
            homeViewPager.setCurrentItem(pagerPosition, false);
        }
        headerTitle.setText(worlds.get(pagerPosition).name());
        headerImage.setImageDrawable(headerImage.getDrawable());
    }
}
