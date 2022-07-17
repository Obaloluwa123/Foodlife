package com.example.fooding.fragments;

import static android.content.ContentValues.TAG;

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
import com.example.fooding.activities.MainActivity;
import com.example.fooding.adapters.RecipeExploreAdapter;
import com.example.fooding.adapters.RecipeParentAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.favourite.FavouriteList;
import com.example.fooding.models.Food;
import com.example.fooding.models.Ingredient;
import com.example.fooding.models.RecipeParent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class RecipeExploreFragment extends Fragment {
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

    //TODO:Implement infinite scrolling as stretch goal
    RecipeExploreAdapter.RecipeExploreAdapterListener listener;
    private RecipeParentAdapter recipeParentAdapter;
    private ArrayList<RecipeParent> recipes = new ArrayList<>();
    private ArrayList<Food> uniqueRecipes = new ArrayList<>();
    private ArrayList<Food> cuisineRecipes = new ArrayList<>();
    private ArrayList<Food> breakfastRecipes = new ArrayList<>();


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
        recipeCategory = view.findViewById(R.id.recipeCategory);
        parentRecyclerView = view.findViewById(R.id.recipeByIngredientRecyclerView);
        parentRecyclerView2 = view.findViewById(R.id.italianRecyclerView);
        parentRecyclerView3 = view.findViewById(R.id.americanRecyclerView);
        parentRecyclerView4 = view.findViewById(R.id.chineseRecyclerView);
        parentRecyclerView5 = view.findViewById(R.id.breakfastRecyclerView);

        recipes.add(new RecipeParent("Recommended Recipes Based on Ingredients in my Fridge"));
        recipes.add(new RecipeParent("Recommended Italian Recipes Based on my favorite"));
        recipes.add(new RecipeParent("Recommended American Recipes Based on my favorite "));
        recipes.add(new RecipeParent("Recommended Chinese Recipes Based on my favorite"));
        recipes.add(new RecipeParent("Recommended Breakfast Recipes Based on my favorite "));


        recipeAdapter = new RecipeExploreAdapter(uniqueRecipes, null);
        italianRecipeAdapter = new RecipeExploreAdapter(cuisineRecipes, null);
        americanRecipeAdapter = new RecipeExploreAdapter(cuisineRecipes, null);
        chineseRecipeAdapter = new RecipeExploreAdapter(cuisineRecipes, null);
        breakfastRecipeAdapter = new RecipeExploreAdapter(breakfastRecipes, null);
        parentRecyclerView.setAdapter(recipeAdapter);
        parentRecyclerView2.setAdapter(italianRecipeAdapter);
        parentRecyclerView4.setAdapter(americanRecipeAdapter);
        parentRecyclerView5.setAdapter(chineseRecipeAdapter);
        parentRecyclerView3.setAdapter(breakfastRecipeAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        parentRecyclerView.setLayoutManager(layoutManager);
        parentRecyclerView.setHasFixedSize(true);
        parentRecyclerView2.setLayoutManager(layoutManager2);
        parentRecyclerView2.setHasFixedSize(true);
        parentRecyclerView3.setLayoutManager(layoutManager3);
        parentRecyclerView3.setHasFixedSize(true);
        parentRecyclerView4.setLayoutManager(layoutManager4);
        parentRecyclerView4.setHasFixedSize(true);
        parentRecyclerView5.setLayoutManager(layoutManager5);
        parentRecyclerView5.setHasFixedSize(true);

        queryRecipesBasedOnFridge();
        querySimilarCuisinesRecipe("Italian");
        querySimilarCuisinesRecipe("American");
        querySimilarCuisinesRecipe("Chinese");
        queryPreviouslyLikedRecipe();

    }

    private void queryRecipesBasedOnFridge() {
        ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
        query.include(Ingredient.USER_KEY);
        query.setLimit(INGREDIENT_NUMBER_LIMIT);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Ingredient>() {
            @Override
            public void done(List<Ingredient> ingredients, ParseException e) {
                if (e != null) {
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
        client.getRecipeByIngredients("apples,+flour,+sugar&number=2", new NetworkCallback<List<Food>>() {

            @Override
            public void onSuccess(List<Food> data) {

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
        client.suggestByFavorite(null, cuisine, favFeatures[randomFav], new NetworkCallback<List<Food>>() {
            @Override
            public void onSuccess(List<Food> data) {
                cuisineRecipes.addAll(data);
                italianRecipeAdapter.notifyDataSetChanged();
                americanRecipeAdapter.notifyDataSetChanged();
                chineseRecipeAdapter.notifyDataSetChanged();
                breakfastRecipeAdapter.notifyDataSetChanged();
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
        Log.i(TAG, "queryPreviouslyLikedRecipe: " + favFeatures[randomFav]);
        FoodClient client = new FoodClient();
        client.suggestByFavorite("Breakfast", null, favFeatures[randomFav], new NetworkCallback<List<Food>>() {
            @Override
            public void onSuccess(List<Food> data) {
                breakfastRecipes.addAll(data);
                breakfastRecipeAdapter.notifyDataSetChanged();
                Log.d(TAG, "on success");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "on failure");
            }
        });

    }
}
