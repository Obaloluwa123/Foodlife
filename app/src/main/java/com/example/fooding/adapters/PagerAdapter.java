package com.example.fooding.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fooding.fragments.IngredientsFragment;
import com.example.fooding.fragments.InstructionsFragment;
import com.example.fooding.fragments.OverviewFragment;
import com.example.fooding.models.FoodExtended;

@SuppressWarnings("ALL")
public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_0 = 0;
    private static final int PAGE_1 = 1;
    private static final int PAGE_2 = 2;
    public FoodExtended food;

    @SuppressWarnings("deprecation")
    public PagerAdapter(FoodExtended food, @NonNull FragmentManager fm) {
        super(fm);
        this.food = food;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_0:
                return OverviewFragment.newInstance(food);
            case PAGE_1:
                return IngredientsFragment.newInstance(food);
            case PAGE_2:
                return InstructionsFragment.newInstance(food);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_0:
                return "Overview";
            case PAGE_1:
                return "Ingredients";
            case PAGE_2:
                return "Instructions";
            default:
                return null;
        }
    }
}
