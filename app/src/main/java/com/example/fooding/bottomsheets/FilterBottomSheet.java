package com.example.fooding.bottomsheets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fooding.R;
import com.example.fooding.fragments.RecipeSearchFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "FILTER_BOTTOM_SHEET";

    private final String selectedDiet;
    private final String selectedMeal;

    private ChipGroup dietChipGroup;
    private ChipGroup mealChipGroup;

    public FilterBottomSheet(String diet, String meal) {
        this.selectedDiet = diet;
        this.selectedMeal = meal;
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
        Chip glutenFreeChip = view.findViewById(R.id.gluttenFreechip);
        Chip vegeterianChip = view.findViewById(R.id.vegetarianchip);
        Chip ketogenicChip = view.findViewById(R.id.ketogenicChip);
        Chip lactoVegetarianChip = view.findViewById(R.id.lactoVegetarianChip);
        Chip ovoVegetarianChip = view.findViewById(R.id.ovoVegetarianChip);
        Chip veganChip = view.findViewById(R.id.veganChip);
        Chip pescetarianChip = view.findViewById(R.id.PescetarianChip);
        Chip paleoChip = view.findViewById(R.id.PaleoChip);
        Chip primalChip = view.findViewById(R.id.PrimalChip);
        Chip lowFODMAPChip = view.findViewById(R.id.LowFODMAPChip);
        Chip whole30Chip = view.findViewById(R.id.Whole30Chip);
        Chip breakfastChip = view.findViewById(R.id.breakfastChip);
        Chip appetizerChip = view.findViewById(R.id.appetizerchip);
        Chip maincourseChip = view.findViewById(R.id.maincourseChip);
        Chip sidedishChip = view.findViewById(R.id.sidedish);
        Chip dessertChip = view.findViewById(R.id.dessertChip);
        Chip saladChip = view.findViewById(R.id.saladChip);
        Chip breadChip = view.findViewById(R.id.breadChip);
        Chip soupChip = view.findViewById(R.id.soupChip);
        Chip beverageChip = view.findViewById(R.id.soupChip);
        Chip sauceChip = view.findViewById(R.id.sauceChip);
        Chip marinadeChip = view.findViewById(R.id.marinadeChip);
        Chip fingerfooddish = view.findViewById(R.id.fingerfooddish);
        Chip snackChip = view.findViewById(R.id.snackChip);
        Chip drinkChip = view.findViewById(R.id.drinkChip);

        if (selectedDiet != null) {
            switch (selectedDiet) {
                case "Gluten Free":
                    glutenFreeChip.setChecked(true);
                    break;
                case "Vegetarian":
                    vegeterianChip.setChecked(true);
                    break;
                case "Ketogenic":
                    ketogenicChip.setChecked(true);
                    break;
                case "lactoVegetarianChip":
                    lactoVegetarianChip.setChecked(true);
                    break;
                case "Ovo-Vegetarian":
                    ovoVegetarianChip.setChecked(true);
                    break;
                case "veganChip":
                    veganChip.setChecked(true);
                    break;
                case "PescetarianChip":
                    pescetarianChip.setChecked(true);
                    break;
                case "PaleoChip":
                    paleoChip.setChecked(true);
                    break;
                case "PrimalChip":
                    primalChip.setChecked(true);
                    break;
                case "LowFODMAPChip":
                    lowFODMAPChip.setChecked(true);
                    break;
                case "Whole30":
                    whole30Chip.setChecked(true);
                    break;
            }
        }

        if (selectedMeal != null) {
            switch (selectedMeal) {
                case "breakfast":
                    breakfastChip.setChecked(true);
                    break;
                case "appetizer":
                    appetizerChip.setChecked(true);
                    break;
                case "maincourse":
                    maincourseChip.setChecked(true);
                    break;
                case "sidedish":
                    sidedishChip.setChecked(true);
                    break;
                case "dessertChip":
                    dessertChip.setChecked(true);
                    break;
                case "saladChip":
                    saladChip.setChecked(true);
                    break;
                case "breadChip":
                    breadChip.setChecked(true);
                    break;
                case "soup":
                    soupChip.setChecked(true);
                    break;
                case "beverage":
                    beverageChip.setChecked(true);
                    break;
                case "sauceChip":
                    sauceChip.setChecked(true);
                    break;
                case "marinade":
                    marinadeChip.setChecked(true);
                    break;
                case "fingerfooddish":
                    fingerfooddish.setChecked(true);
                    break;
                case "snack":
                    snackChip.setChecked(true);
                    break;
                case "drink":
                    drinkChip.setChecked(true);
                    break;
            }
        }
    }

    @SuppressWarnings("DuplicateBranchesInSwitch")
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        String diet = null;
        String meal = null;

        switch (dietChipGroup.getCheckedChipId()) {
            case R.id.gluttenFreechip:
                diet = "Gluten Free";
                break;
            case R.id.vegetarianchip:
                diet = "Vegetarian";
                break;
            case R.id.ketogenicChip:
                diet = "Ketogenic";
                break;
            case R.id.lactoVegetarianChip:
                diet = "Lacto-Vegetarian";
                break;
            case R.id.ovoVegetarianChip:
                diet = "Ovo-Vegetarian";
                break;
            case R.id.veganChip:
                diet = "Vegan";
                break;
            case R.id.PescetarianChip:
                diet = "Pescetarian";
                break;
            case R.id.PaleoChip:
                diet = "Paleo";
                break;
            case R.id.PrimalChip:
                diet = "Primal";
                break;
            case R.id.LowFODMAPChip:
                diet = "Low FODMAP";
                break;
            case R.id.Whole30Chip:
                diet = "Whole30";
                break;

        }

        switch (mealChipGroup.getCheckedChipId()) {
            case R.id.breakfastChip:
                meal = "breakfast";
                break;
            case R.id.appetizerchip:
                meal = "appetizer";
                break;
            case R.id.maincourseChip:
                meal = "main course";
                break;
            case R.id.sidedish:
                meal = "side dish";
                break;
            case R.id.dessertChip:
                meal = "dessertChip";
                break;
            case R.id.saladChip:
                meal = "dessertChip";
                break;
            case R.id.breadChip:
                meal = "bread";
                break;
            case R.id.soupChip:
                meal = "soup";
                break;
            case R.id.beverageChip:
                meal = "beverage";
                break;
            case R.id.sauceChip:
                meal = "sauce";
                break;
            case R.id.marinadeChip:
                meal = "marinade";
                break;
            case R.id.fingerfooddish:
                meal = "fingerfooddish";
                break;
            case R.id.snackChip:
                meal = "snack";
                break;
            case R.id.drinkChip:
                meal = "drink";
                break;
        }

        RecipeSearchFragment fragment = (RecipeSearchFragment) getParentFragment();
        if (fragment != null) {
            fragment.setVariables(diet, meal);
        }

    }

}
