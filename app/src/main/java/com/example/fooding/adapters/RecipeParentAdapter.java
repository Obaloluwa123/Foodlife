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
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Food;
import com.example.fooding.models.Ingredient;
import com.example.fooding.models.RecipeParent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO:working on this Adapter for my explore page
public class RecipeParentAdapter extends RecyclerView.Adapter<RecipeParentAdapter.MyViewholder> implements RecipeExploreAdapter.RecipeExploreAdapterListener {
    private ArrayList<RecipeParent> parentModelArrayList;
    private RecipeExploreAdapter recipeAdapter;
    public Context context;
    private static final Integer INGREDIENT_NUMBER_LIMIT = 150;
    private ArrayList<Food> recipes = new ArrayList<>();


    public RecipeParentAdapter(ArrayList<RecipeParent> parentModelArrayList, Context context) {
        this.parentModelArrayList = parentModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        RecipeParent currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.parentRecyclerView.setLayoutManager(layoutManager);
        holder.parentRecyclerView.setHasFixedSize(true);

        holder.recipeCategory.setText(currentItem.recipeCategory());
        if (parentModelArrayList.get(position).recipeCategory().equals("Recipe By Ingredients in my Fridge")) {
            queryIngredients();
        }

        recipeAdapter = new RecipeExploreAdapter(recipes, this);
        holder.parentRecyclerView.setAdapter(recipeAdapter);
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

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView recipeCategory;
        public RecyclerView parentRecyclerView;

        public MyViewholder(View itemView) {
            super(itemView);

            recipeCategory = itemView.findViewById(R.id.recipeCategory);
            parentRecyclerView = itemView.findViewById(R.id.parentRecyclerView);
        }
    }

    private void queryIngredients() {
        ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
        query.include(Ingredient.USER_KEY);
        query.setLimit( INGREDIENT_NUMBER_LIMIT);
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
                Log.e(TAG, "ONFAIL: ");
            }
        });
    }

}



