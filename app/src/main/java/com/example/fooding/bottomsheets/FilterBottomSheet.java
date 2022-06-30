package com.example.fooding.bottomsheets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fooding.R;
import com.example.fooding.fragments.SearchFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "FILTER_BOTTOM_SHEET";

    public String selectedDiet = "";
    public String selectedMeal = "";

    private ChipGroup dietChipGroup;
    private ChipGroup mealChipGroup;

    private Chip glutenFreeChip;
    private Chip vegeterianChip;
    private Chip breakfastChip;
    private Chip appetizerChip;

    public FilterBottomSheet(String diet, String meal) {
        if (diet!= null) this.selectedDiet = diet;
        if (meal != null) this.selectedMeal = meal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dietChipGroup = view.findViewById(R.id.dietChipGroup);
        mealChipGroup = view.findViewById(R.id.mealChipGroup);
        glutenFreeChip = view.findViewById(R.id.gluttenFreechip);
        vegeterianChip = view.findViewById(R.id.vegetarianchip);
        breakfastChip = view.findViewById(R.id.breakfastChip);
        appetizerChip = view.findViewById(R.id.appetizerchip);

        switch (selectedDiet) {
            case "Gluten Free":
                glutenFreeChip.setChecked(true);
                break;
            case "Vegetarian":
                vegeterianChip.setChecked(true);
                break;
        }

        switch (selectedMeal) {
            case "breakfast":
                breakfastChip.setChecked(true);
                break;
            case "appetizer":
                appetizerChip.setChecked(true);
                break;
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        String diet = null;
        String meal = null;

        switch (dietChipGroup.getCheckedChipId()) {
            case R.id.gluttenFreechip:
                diet = "Gluten Free";
                break;
            case R.id.vegetarianchip:
                diet = "Vegetarian";
                break;
        }

        switch (mealChipGroup.getCheckedChipId()) {
            case R.id.breakfastChip:
                meal = "breakfast";
                break;
            case R.id.appetizerchip:
                meal = "appetizer";
                break;
        }

        SearchFragment fragment = (SearchFragment) getParentFragment();
        if (fragment != null) {
            fragment.setVariables(diet, meal);
        }

        super.onDismiss(dialog);
    }
}
