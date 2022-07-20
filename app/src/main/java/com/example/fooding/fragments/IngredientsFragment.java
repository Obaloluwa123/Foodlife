package com.example.fooding.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooding.R;
import com.example.fooding.models.RecipeExtended;

@SuppressWarnings({"ALL", "ConstantConditions"})
//TODO:add ingredients to detail activity or delete this page
public class IngredientsFragment extends Fragment {

    public static final String FOOD_ARG = "FOOD_ID_ARG";

    public RecipeExtended foodExtended;

    public static IngredientsFragment newInstance(RecipeExtended food) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FOOD_ARG, food);
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
        foodExtended = getArguments().getParcelable(FOOD_ARG);

    }
}
