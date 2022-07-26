package com.example.fooding.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooding.R;
import com.example.fooding.models.ExtendedIngredients;

@SuppressWarnings({"ALL", "ConstantConditions"})
public class IngredientsFragment extends Fragment {

    public static final String FOOD_ARG = "FOOD_ID_ARG";

    public ExtendedIngredients extendedIngredients;

    public static IngredientsFragment newInstance(ExtendedIngredients recipes) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FOOD_ARG, (Parcelable) recipes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView ingredientNameTextView = view.findViewById(R.id.ingredientName);
        ImageView ingredientImageView = view.findViewById(R.id.ingredient_imageview);
        TextView ingredientUnitTextView = view.findViewById(R.id.ingredient_unit);
        TextView ingredientAmountTextView = view.findViewById(R.id.ingredient_amount);

        extendedIngredients = getArguments().getParcelable(FOOD_ARG);
    }
}
