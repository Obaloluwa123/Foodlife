package com.example.fooding.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.adapters.RecipeExploreAdapter;
import com.example.fooding.adapters.RecipeParentAdapter;
import com.example.fooding.models.RecipeParent;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RecipeExploreFragment extends Fragment {


    //TODO:Implement infinite scrolling as stretch goal
    RecipeExploreAdapter.RecipeExploreAdapterListener listener;
    private RecipeParentAdapter recipeParentAdapter;
    private ArrayList<RecipeParent> recipes = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = formattedDate.split(",");


        recipes.add(new RecipeParent("Recommended Recipes Based on Ingredients in my Fridge"));
        recipes.add(new RecipeParent("Recommended Breakfast Recipes Based on my favorite "));
        recipes.add(new RecipeParent("Recommended Italian Recipes Based on my favorite"));
        recipes.add(new RecipeParent("Recommended American Recipes Based on my favorite "));
        recipes.add(new RecipeParent("Recommended Chinese Recipes Based on my favorite"));

        recipeParentAdapter = new RecipeParentAdapter(recipes, getContext());

        RecyclerView exploreRecyclerView = view.findViewById(R.id.exploreRecyclerView);
        exploreRecyclerView.setHasFixedSize(true);
        exploreRecyclerView.setAdapter(recipeParentAdapter);
        exploreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeParentAdapter.notifyDataSetChanged();

    }
}
