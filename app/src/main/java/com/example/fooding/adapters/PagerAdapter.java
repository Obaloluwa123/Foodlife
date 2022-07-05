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

public class PagerAdapter extends FragmentStatePagerAdapter {

    public FoodExtended food;

    public PagerAdapter(FoodExtended food, @NonNull FragmentManager fm) {
        super(fm);
        this.food = food;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OverviewFragment.newInstance(food);
            case 1:
                return IngredientsFragment.newInstance(food);
            case 2:
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
            case 0:
                return "Overview";
            case 1:
                return "Ingredients";
            case 2:
                return "Instructions";
            default:
                return null;
        }
    }
}
