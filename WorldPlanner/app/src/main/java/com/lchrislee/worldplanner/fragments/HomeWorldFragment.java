package com.lchrislee.worldplanner.fragments;

import android.util.Log;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.World;
import com.lchrislee.worldplanner.views.WorldEntityHolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * A fragment that displays the contents of a {@link World}.
 */
@EFragment(R.layout.fragment_home_world)
public class HomeWorldFragment extends BaseFragment
{
    private static final String LOG_TAG = HomeWorldFragment.class.getSimpleName();

    @FragmentArg World world;

    @ViewById TextView homeWorldDescription;

    @ViewById WorldEntityHolder homeWorldCharacters;

    @ViewById WorldEntityHolder homeWorldLocations;

    @ViewById WorldEntityHolder homeWorldItems;

    @AfterViews
    protected void viewDidLoad() {
        homeWorldDescription.setText(world.description());
        homeWorldCharacters.setAddEntityDelegate(this::onAddEntity);
        homeWorldLocations.setAddEntityDelegate(this::onAddEntity);
        homeWorldItems.setAddEntityDelegate(this::onAddEntity);
    }

    /**
     * Decide what type of entity to add and launch the appropriate screen to create a new one.
     *
     * @param entityType The type requested to potentially create a new entity.
     */
    public void onAddEntity(final int entityType) {
        switch(entityType) {
            case R.id.homeWorldCharacters:
                Log.d(LOG_TAG, "Request to create a Character.");
                return;
            case R.id.homeWorldLocations:
                Log.d(LOG_TAG, "Request to create a Location.");
                return;
            case R.id.homeWorldItems:
                Log.d(LOG_TAG, "Request to create an Item.");
                return;
            default:
                break;
        }
    }

    @Click({R.id.homeWorldDetails})
    protected void onWorldDetailsClicked() {
        Log.d(LOG_TAG, "View details for world: " + world.id());
    }
}
