package com.example.fooding.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooding.R;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.FoodExtended;
import com.example.fooding.models.Ingredient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RecipeExploreFragment extends Fragment {


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
    }

    private void queryIngredients() {
        ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
        query.include(Ingredient.USER_KEY);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground((ingredients, e) -> {
            // check for errors
            if (e != null) {
                Log.e(TAG, "Issue with getting posts", e);
                return;
            }

            if (ingredients != null) {
                fetchRecipes(ingredients);
            }
        });
    }

    private void fetchRecipes(List<Ingredient> ingredients) {
        if (ingredients.isEmpty()) return;
        Set<String> uniqueIngredients = new HashSet<>();
        for (Ingredient ingredient : ingredients) {
            uniqueIngredients.add(ingredient.getIngredient());
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
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "ONFAIL: ");
            }
        });
    }
}
