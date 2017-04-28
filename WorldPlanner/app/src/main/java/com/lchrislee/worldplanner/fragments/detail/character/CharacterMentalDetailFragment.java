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
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.fragments.detail.character.mental.CharacterBeliefsDetailFragment;
import com.lchrislee.worldplanner.fragments.detail.character.mental.CharacterMemoriesDetailFragment;
import com.lchrislee.worldplanner.models.StoryCharacter;

public class CharacterMentalDetailFragment
        extends WorldPlannerBaseFragment
        implements SupportCharacterUpdate
{

    private static final String CHARACTER = "CHARACTER";

    private StoryCharacter character;
    private CharacterMentalPagerAdapter adapter;

    public CharacterMentalDetailFragment() {

    }

    public static @NonNull CharacterMentalDetailFragment newInstance(@NonNull StoryCharacter c)
    {
        CharacterMentalDetailFragment fragment = new CharacterMentalDetailFragment();
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
        View v = inflater.inflate(R.layout.fragment_character_mental, container, false);
        adapter = new CharacterMentalPagerAdapter(getChildFragmentManager(), character);
        final TabLayout layout = (TabLayout) v.findViewById(R.id.fragment_detail_character_mental_tab);
        final ViewPager pager = (ViewPager) v.findViewById(R.id.fragment_detail_character_mental_pager);
        pager.setAdapter(adapter);
        layout.setupWithViewPager(pager);
        return v;
    }

    @Override
    public void updateCharacter() {
        adapter.updateCharacter();
    }

    public void setAdapterListener(@NonNull OnTabSelected l)
    {
        adapter.setListener(l);
    }

    private class CharacterMentalPagerAdapter
            extends FragmentPagerAdapter
            implements SupportCharacterUpdate
    {
        private OnTabSelected listener;

        private static final int NUM_MENTAL_FRAGMENTS = 2;

        private WorldPlannerBaseFragment fragments[] = new WorldPlannerBaseFragment[NUM_MENTAL_FRAGMENTS];
        private String titles[] = {"Memories", "Beliefs"};
        CharacterMentalPagerAdapter(FragmentManager fm, @NonNull StoryCharacter character) {
            super(fm);
            fragments[0] = CharacterMemoriesDetailFragment.newInstance(character);
            fragments[1] = CharacterBeliefsDetailFragment.newInstance(character);
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
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public void updateCharacter() {
            for (WorldPlannerBaseFragment fragment : fragments)
            {
                ((SupportCharacterUpdate) fragment).updateCharacter();
            }
        }

        public void setListener(@NonNull OnTabSelected l)
        {
            listener = l;
        }
    }
}
