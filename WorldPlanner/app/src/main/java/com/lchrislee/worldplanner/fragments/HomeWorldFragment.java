package com.lchrislee.worldplanner.fragments;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
public class HomeWorldFragment
    extends BaseFragment
    implements WorldEntityHolder.EntityInteractionDelegate
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
        homeWorldCharacters.useList(world.getCharacters());
        homeWorldCharacters.setAddEntityDelegate(this);
        homeWorldLocations.useList(world.getLocations());
        homeWorldLocations.setAddEntityDelegate(this);
        homeWorldItems.useList(world.getItems());
        homeWorldItems.setAddEntityDelegate(this);
    }

    @Override
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

    @Override
    public void onEntityClicked (final long entityId)
    {
        // TODO 1/14/18: Figure out what entity this is and go to details.
        Toast.makeText(getContext(), "Clicked on entity: " + entityId, Toast.LENGTH_SHORT).show();
    }

    @Click({R.id.homeWorldDetails})
    protected void onWorldDetailsClicked() {
        Log.d(LOG_TAG, "View details for world: " + world.id());
    }
}
