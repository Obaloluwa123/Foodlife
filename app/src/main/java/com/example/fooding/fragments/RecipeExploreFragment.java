package com.example.fooding.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.activities.MainActivity;
import com.example.fooding.adapters.RecipeExploreAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.favourite.FavouriteList;
import com.example.fooding.models.Ingredient;
import com.example.fooding.models.Recipe;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;


public class RecipeExploreFragment extends Fragment implements RecipeExploreAdapter.RecipeExploreAdapterListener {
    private static final Integer INGREDIENT_NUMBER_LIMIT = 150;
    public TextView recipeCategory;
    public RecyclerView parentRecyclerView;
    public RecyclerView parentRecyclerView2;
    public RecyclerView parentRecyclerView3;
    public RecyclerView parentRecyclerView4;
    public RecyclerView parentRecyclerView5;
    private RecipeExploreAdapter recipeAdapter;
    private List<FavouriteList> favouriteLists;
    private RecipeExploreAdapter italianRecipeAdapter;
    private RecipeExploreAdapter americanRecipeAdapter;
    private RecipeExploreAdapter chineseRecipeAdapter;
    private RecipeExploreAdapter breakfastRecipeAdapter;

    private final ArrayList<Recipe> uniqueRecipes = new ArrayList<>();
    private final ArrayList<Recipe> chineseRecipes = new ArrayList<>();
    private final ArrayList<Recipe> italianRecipes = new ArrayList<>();
    private final ArrayList<Recipe> americanRecipes = new ArrayList<>();
    private final ArrayList<Recipe> breakfastRecipes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeCategory = view.findViewById(R.id.recipeCategory);
        parentRecyclerView = view.findViewById(R.id.recipeByIngredientRecyclerView);
        parentRecyclerView2 = view.findViewById(R.id.italianRecyclerView);
        parentRecyclerView3 = view.findViewById(R.id.americanRecyclerView);
        parentRecyclerView4 = view.findViewById(R.id.chineseRecyclerView);
        parentRecyclerView5 = view.findViewById(R.id.breakfastRecyclerView);

        recipeAdapter = new RecipeExploreAdapter(uniqueRecipes, this);
        italianRecipeAdapter = new RecipeExploreAdapter(italianRecipes, this);
        americanRecipeAdapter = new RecipeExploreAdapter(americanRecipes, this);
        chineseRecipeAdapter = new RecipeExploreAdapter(chineseRecipes, this);
        breakfastRecipeAdapter = new RecipeExploreAdapter(breakfastRecipes, this);

        setupRecycler(parentRecyclerView, recipeAdapter);
        setupRecycler(parentRecyclerView2, italianRecipeAdapter);
        setupRecycler(parentRecyclerView3, breakfastRecipeAdapter);
        setupRecycler(parentRecyclerView4, americanRecipeAdapter);
        setupRecycler(parentRecyclerView5, chineseRecipeAdapter);

        queryRecipesBasedOnFridge();
        querySimilarCuisinesRecipe("Italian");
        querySimilarCuisinesRecipe("American");
        querySimilarCuisinesRecipe("Chinese");
        queryPreviouslyLikedRecipe();

    }

    private void setupRecycler(RecyclerView recyclerView, RecipeExploreAdapter adapter) {
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void queryRecipesBasedOnFridge() {
        ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
        query.include(Ingredient.USER_KEY);
        query.setLimit(INGREDIENT_NUMBER_LIMIT);
        query.addDescendingOrder("createdAt");
        query.findInBackground((ingredients, e) -> {
            if (e != null) {
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
            uniqueIngredients.add(ingredient.uniqueIngredientSet());
        }
        List<String> list = new ArrayList<>(uniqueIngredients);
        StringBuilder query = new StringBuilder();
        query.append(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            query.append(",+");
            query.append(list.get(i));
        }

        FoodClient client = new FoodClient();
        client.getRecipeByIngredients("apples,+flour,+sugar&number=2", new NetworkCallback<List<Recipe>>() {

            @Override
            public void onSuccess(List<Recipe> data) {

                uniqueRecipes.addAll(data);
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    private void querySimilarCuisinesRecipe(String cuisine) {
        favouriteLists = MainActivity.favouriteDatabase.favouriteDao().getFavouriteData();

        if (favouriteLists.isEmpty()) {
            Log.d(TAG, "Favourite list is empty");
            return;
        }

        StringBuilder features = new StringBuilder();
        for (int i = 0; i < favouriteLists.size(); i++) {
            features.append(favouriteLists.get(i).getTitle());
            features.append(" ");
        }
        String favoriteFeatures = features.toString();
        String[] favFeatures = favoriteFeatures.split(" ");
        Random favoriteIngredient = new Random();
        int randomFav = favoriteIngredient.nextInt(favFeatures.length - 1);

        FoodClient client = new FoodClient();
        client.suggestByFavorite(null, cuisine, favFeatures[randomFav], new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> data) {

                if (Objects.equals(cuisine, "Italian")) {
                    italianRecipes.addAll(data);
                    italianRecipeAdapter.notifyDataSetChanged();
                } else if (cuisine.equals("American")) {
                    americanRecipes.addAll(data);
                    americanRecipeAdapter.notifyDataSetChanged();
                } else if (cuisine.equals("Chinese")) {
                    chineseRecipes.addAll(data);
                    chineseRecipeAdapter.notifyDataSetChanged();
                } else {
                    breakfastRecipes.addAll(data);
                    breakfastRecipeAdapter.notifyDataSetChanged();
                }
                if (data.size() == 0 ) {
                    querySimilarCuisinesRecipe(cuisine);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "failed to display recipes");
            }
        });

    }

    private void queryPreviouslyLikedRecipe() {

        favouriteLists = MainActivity.favouriteDatabase.favouriteDao().getFavouriteData();
        if (favouriteLists.isEmpty()) return;

        StringBuilder features = new StringBuilder();

        for (int i = 0; i < favouriteLists.size(); i++) {
            features.append(favouriteLists.get(i).getTitle());
            features.append(" ");
        }
        String favoriteFeatures = features.toString();
        String[] favFeatures = favoriteFeatures.split(" ");
        Random favoriteIngredient = new Random();
        int randomFav = favoriteIngredient.nextInt(favFeatures.length - 1);
        FoodClient client = new FoodClient();
        client.suggestByFavorite("Breakfast", null, favFeatures[randomFav], new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> data) {
                breakfastRecipes.addAll(data);
                breakfastRecipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "on failure");
            }
        });

    }

    @Override
    public void onRecipeClicked(Recipe food) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.FOOD_ID_ARG, food.getId());
        startActivity(intent);

    }
}
