package com.example.fooding.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooding.R;
import com.example.fooding.models.FoodExtended;

@SuppressWarnings({"ConstantConditions", "unused"})
public class InstructionsFragment extends Fragment {

    public static final String FOOD_ARG = "FOOD_ID_ARG";

    public FoodExtended foodExtended;

    public InstructionsFragment() {
    }

    public static InstructionsFragment newInstance(FoodExtended food) {
        InstructionsFragment fragment = new InstructionsFragment();
        Bundle args = new Bundle();
        args.putParcelable(FOOD_ARG, food);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instructions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodExtended = getArguments().getParcelable(FOOD_ARG);

    }
}
