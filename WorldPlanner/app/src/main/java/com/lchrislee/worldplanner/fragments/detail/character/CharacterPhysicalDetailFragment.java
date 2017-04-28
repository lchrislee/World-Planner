package com.lchrislee.worldplanner.fragments.detail.character;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.EntityDetailActivity;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.physical.CharacterArmDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.character.physical.CharacterHeadDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.character.physical.CharacterLegDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.character.physical.CharacterTorsoDetailFragment;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterPhysicalDetailFragment
        extends WorldPlannerBaseFragment
        implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";

    private StoryCharacter character;
    private CharacterPhysicalPagerAdapter adapter;
    private OnTabSelected listener;

    public CharacterPhysicalDetailFragment() {

    }

    public static @NonNull
    CharacterPhysicalDetailFragment newInstance(@NonNull StoryCharacter c)
    {
        CharacterPhysicalDetailFragment fragment = new CharacterPhysicalDetailFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(CHARACTER, c);
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        character = (StoryCharacter) getArguments().getSerializable(CHARACTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_character_physical, container, false);
        adapter = new CharacterPhysicalPagerAdapter(getChildFragmentManager(), character);
        final TabLayout layout = (TabLayout) v.findViewById(R.id.fragment_detail_character_physical_tab);
        final ViewPager pager = (ViewPager) v.findViewById(R.id.fragment_detail_character_physical_pager);
        pager.setAdapter(adapter);
        layout.setupWithViewPager(pager);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setListener(listener);
    }

    @Override
    public void updateCharacter() {
        adapter.updateCharacter();
    }

    void setAdapterListener(OnTabSelected l)
    {
        listener = l;
    }

    private class CharacterPhysicalPagerAdapter extends FragmentPagerAdapter implements SupportCharacterUpdate
    {
        public void setListener(@NonNull OnTabSelected l)
        {
            listener = l;
        }

        private static final int NUM_PHYSICAL_FRAGMENTS = 4;

        private WorldPlannerBaseFragment fragments[] = new WorldPlannerBaseFragment[NUM_PHYSICAL_FRAGMENTS];
        private String titles[] = {"Head", "Torso", "Arms", "Legs"};

        private OnTabSelected listener;

        CharacterPhysicalPagerAdapter(FragmentManager fm, @NonNull StoryCharacter character) {
            super(fm);
            fragments[0] = CharacterHeadDetailFragment.newInstance(character);
            fragments[1] = CharacterTorsoDetailFragment.newInstance(character);
            fragments[2] = CharacterArmDetailFragment.newInstance(character);
            fragments[3] = CharacterLegDetailFragment.newInstance(character);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (listener != null)
            {
                listener.onTabSelected();
            }
            ((EntityDetailActivity) getActivity()).onCharacterTabSwitch();
            return fragments[position];
        }

        @Override
        public int getCount() {
            return NUM_PHYSICAL_FRAGMENTS;
        }

        @Override
        public void updateCharacter() {
            for (WorldPlannerBaseFragment fragment : fragments)
            {
                ((SupportCharacterUpdate) fragment).updateCharacter();
            }
        }
    }
}
