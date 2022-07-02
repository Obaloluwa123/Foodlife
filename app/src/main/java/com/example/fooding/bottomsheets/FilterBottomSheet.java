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
    private Chip ketogenicChip;
    private Chip lactoVegetarianChip;
    private Chip ovoVegetarianChip;
    private Chip veganChip;
    private Chip PescetarianChip;
    private Chip PaleoChip;
    private Chip PrimalChip;
    private Chip LowFODMAPChip;
    private Chip Whole30Chip;
    private Chip breakfastChip;
    private Chip appetizerChip;
    private Chip maincourseChip;
    private Chip sidedishChip;
    private Chip dessertChip;
    private Chip saladChip;
    private Chip breadChip;
    private Chip soupChip;
    private Chip beverageChip;
    private Chip sauceChip;
    private Chip marinadeChip;
    private Chip fingerfooddish;
    private Chip snackChip;
    private Chip drinkChip;

    public FilterBottomSheet(String diet, String meal) {
        if (diet != null) this.selectedDiet = diet;
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
        glutenFreeChip = view.findViewById(R.id.glutenFreeChip);
        vegeterianChip = view.findViewById(R.id.vegetarianchip);
        ketogenicChip = view.findViewById(R.id.ketogenicChip);
        lactoVegetarianChip = view.findViewById(R.id.lactoVegetarianChip);
        ovoVegetarianChip = view.findViewById(R.id.ovoVegetarianChip);
        veganChip = view.findViewById(R.id.veganChip);
        PescetarianChip = view.findViewById(R.id.PescetarianChip);
        PaleoChip = view.findViewById(R.id.PaleoChip);
        PrimalChip = view.findViewById(R.id.PrimalChip);
        LowFODMAPChip = view.findViewById(R.id.LowFODMAPChip);
        Whole30Chip = view.findViewById(R.id.Whole30Chip);
        breakfastChip = view.findViewById(R.id.breakfastChip);
        appetizerChip = view.findViewById(R.id.appetizerChip);
        maincourseChip = view.findViewById(R.id.maincourseChip);
        sidedishChip = view.findViewById(R.id.sidedish);
        dessertChip = view.findViewById(R.id.dessertChip);
        saladChip = view.findViewById(R.id.saladChip);
        breadChip = view.findViewById(R.id.breadChip);
        soupChip = view.findViewById(R.id.soupChip);
        beverageChip = view.findViewById(R.id.soupChip);
        sauceChip = view.findViewById(R.id.sauceChip);
        marinadeChip = view.findViewById(R.id.marinadeChip);
        fingerfooddish = view.findViewById(R.id.fingerfooddish);
        snackChip = view.findViewById(R.id.snackChip);
        drinkChip = view.findViewById(R.id.drinkChip);


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
                PescetarianChip.setChecked(true);
                break;
            case "PaleoChip":
                PaleoChip.setChecked(true);
                break;
            case "PrimalChip":
                PrimalChip.setChecked(true);
                break;
            case "LowFODMAPChip":
                LowFODMAPChip.setChecked(true);
                break;
            case "Whole30":
                Whole30Chip.setChecked(true);
                break;
        }

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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        String diet = null;
        String meal = null;

        switch (dietChipGroup.getCheckedChipId()) {
            case R.id.glutenFreeChip:
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
            case R.id.appetizerChip:
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

        SearchFragment fragment = (SearchFragment) getParentFragment();
        if (fragment != null) {
            fragment.setVariables(diet, meal);
        }

        super.onDismiss(dialog);
    }
}
