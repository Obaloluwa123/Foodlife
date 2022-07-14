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

import java.util.ArrayList;


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

        recipes.add(new RecipeParent("Recipe By Ingredients in my Fridge"));
        recipeParentAdapter = new RecipeParentAdapter(recipes, getContext());

        RecyclerView exploreRecyclerView = view.findViewById(R.id.exploreRecyclerView);
        exploreRecyclerView.setAdapter(recipeParentAdapter);
        exploreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeParentAdapter.notifyDataSetChanged();

    }
}
