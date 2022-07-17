package com.example.fooding.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.activities.MainActivity;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.favourite.FavouriteList;
import com.example.fooding.models.Food;
import com.example.fooding.models.Ingredient;
import com.example.fooding.models.RecipeParent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RecipeParentAdapter extends RecyclerView.Adapter<RecipeParentAdapter.MyViewholder> implements RecipeExploreAdapter.RecipeExploreAdapterListener {
    private ArrayList<RecipeParent> parentModelArrayList;
    private RecipeExploreAdapter recipeAdapter;
    private RecipeExploreAdapter breakfastRecipeAdapter;

    public Context context;
    private static final Integer INGREDIENT_NUMBER_LIMIT = 150;
    private ArrayList<Food> recipes = new ArrayList<>();
    private ArrayList<Food> breakfastRecipes = new ArrayList<>();
    private List<FavouriteList> favouriteLists;


    public RecipeParentAdapter(ArrayList<RecipeParent> parentModel, Context context) {
        this.parentModelArrayList = parentModel;
        this.context = context;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView recipeCategory;
        public RecyclerView parentRecyclerView;
        public TextView breakfastCategory;
        public RecyclerView parentRecyclerView2;

        public MyViewholder(View itemView) {
            super(itemView);

            recipeCategory = itemView.findViewById(R.id.recipeCategory);
            parentRecyclerView = itemView.findViewById(R.id.parentRecyclerView);
//            breakfastCategory =  itemView.findViewById(R.id.breakfastCategory);
            parentRecyclerView2 = itemView.findViewById(R.id.parentRecyclerView);

        }
    }
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_explore, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        RecipeParent currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.parentRecyclerView.setLayoutManager(layoutManager);
        holder.parentRecyclerView.setHasFixedSize(true);

        holder.recipeCategory.setText(currentItem.recipeCategory());
        if (parentModelArrayList.get(position).recipeCategory().equals("Recommended Recipes Based on Ingredients in my Fridge")) {
            queryIngredients();
        }
        if (parentModelArrayList.get(position).recipeCategory().equals("Recommended Breakfast Recipes Based on my favorite ")) {
            queryPreviouslyLikedRecipe();
        }
        if (parentModelArrayList.get(position).recipeCategory().equals("Recommended Italian Recipes Based on my favorite")) {
            querySimilarCuisinesRecipe("Italian");
        }
        if (parentModelArrayList.get(position).recipeCategory().equals("Recommended American Recipes Based on my favorite")) {
            querySimilarCuisinesRecipe("American");
        }
        if (parentModelArrayList.get(position).recipeCategory().equals("Recommended Chinese Recipes Based on my favorite")) {
            querySimilarCuisinesRecipe("Chinese");
        }

        recipeAdapter = new RecipeExploreAdapter(recipes, this);
        holder.parentRecyclerView.setAdapter(recipeAdapter);
        breakfastRecipeAdapter = new RecipeExploreAdapter(breakfastRecipes, this);
        holder.parentRecyclerView2.setAdapter(breakfastRecipeAdapter);

    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    @Override
    public void onRecipeClicked(Food food) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.FOOD_ID_ARG, food.getId());
        context.startActivity(intent);
    }



    private void queryIngredients() {
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

                recipes.addAll(data);
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
                recipes.addAll(data);
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
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
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });

    }
}



