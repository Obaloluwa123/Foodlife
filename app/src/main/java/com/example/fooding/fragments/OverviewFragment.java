package com.example.fooding.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.models.FoodExtended;

@SuppressWarnings({"PointlessBooleanExpression", "StatementWithEmptyBody"})
public class OverviewFragment extends Fragment {

    public static final String FOOD_ARG = "FOOD_ID_ARG";

    public FoodExtended foodExtended;

    public static OverviewFragment newInstance(FoodExtended food) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(FOOD_ARG, food);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.title_textView);
        ImageView imageView = view.findViewById(R.id.ivImage);
        TextView likestextView = view.findViewById(R.id.like_textView);
        TextView summaryTextView = view.findViewById(R.id.summary_textView);
        TextView timeTextView = view.findViewById(R.id.time_textView);
        foodExtended = requireArguments().getParcelable(FOOD_ARG);

        textView.setText(foodExtended.title);
        likestextView.setText(String.valueOf(foodExtended.aggregateLikes));
        summaryTextView.setText(foodExtended.summary);
        timeTextView.setText(String.valueOf(foodExtended.readyInMinutes));
        if (foodExtended.Vegetarian == true) {
        }

        Glide.with(requireContext()).load(foodExtended.image).into(imageView);
    }
}
