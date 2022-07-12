package com.example.fooding.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.adapters.FoodAdapter;
import com.example.fooding.adapters.RecipeExploreAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Food;
import com.example.fooding.models.FoodExtended;
import com.example.fooding.models.Ingredient;
import com.example.fooding.models.Ingredients;
import com.example.fooding.models.Recipes;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RecipeExploreFragment extends Fragment implements FoodAdapter.FoodAdapterListener {

    private RecipeExploreAdapter recipeAdapter;
    private List<Ingredients> recipeByIngredients;

    public RecipeExploreFragment() {
    }


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
        queryIngredients();
        recipeAdapter = new RecipeExploreAdapter(getContext(), recipeByIngredients);
        RecyclerView exploreRecyclerView = view.findViewById(R.id.exploreRecyclerView);
        recipeByIngredients = new ArrayList<>();
        exploreRecyclerView.setAdapter(recipeAdapter);
        exploreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void queryIngredients() {
        ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
        query.include(Ingredient.USER_KEY);
        query.setLimit(120);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Ingredient>() {
            @Override
            public void done(List<Ingredient> ingredients, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting ingredients ", e);
                    return;
                }

                if (ingredients != null) {
                    fetchRecipes(ingredients);
                }
            }
        });
    }

    private void fetchRecipes(List<Ingredient> ingredients) {
        if (ingredients.isEmpty()) return;
        Set<String> uniqueIngredients = new HashSet<>();
        for (Ingredient ingredient : ingredients) {
            uniqueIngredients.add(ingredient.getIngredientName());
        }
        List<String> list = new ArrayList<>(uniqueIngredients);
        StringBuilder query = new StringBuilder();
        query.append(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            query.append(",+");
            query.append(list.get(i));
        }

        FoodClient client = new FoodClient();
        client.getRecipeByIngredients(query.toString(), new NetworkCallback<List<FoodExtended>>() {

            @Override
            public void onSuccess(List<FoodExtended> data) {
                Log.e(TAG, "DATAAAAAAA: " + data.toString());
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "ONFAIL: ");
            }
        });
    }

    @Override
    public void onFoodClicked(Food food) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.FOOD_ID_ARG, food.getId());
        startActivity(intent);
    }
}
