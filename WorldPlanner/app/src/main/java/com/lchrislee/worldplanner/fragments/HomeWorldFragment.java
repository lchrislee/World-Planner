package com.lchrislee.worldplanner.fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.World;

import org.androidannotations.annotations.AfterViews;
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

    @ViewById RecyclerView homeWorldList;

    @AfterViews
    protected void viewDidLoad() {
        homeWorldDescription.setText(world.description());
    }

}
