package com.example.fooding.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


public class RecipeExploreFragment extends Fragment implements RecipeExploreAdapter.RecipeExploreAdapterListener {
    private static final Integer INGREDIENT_NUMBER_LIMIT = 150;
    public TextView recipeCategory;
    public RecyclerView parentRecyclerView;
    public RecyclerView parentRecyclerView1;
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
    private RecipeExploreAdapter caloriesRecipeAdapter;
    ShimmerFrameLayout shimmerFrameLayout;

    private final ArrayList<Recipe> caloriesRecipes = new ArrayList<>();
    private final ArrayList<Recipe> uniqueRecipes = new ArrayList<>();
    private final ArrayList<Recipe> chineseRecipes = new ArrayList<>();
    private final ArrayList<Recipe> italianRecipes = new ArrayList<>();
    private final ArrayList<Recipe> americanRecipes = new ArrayList<>();
    private final ArrayList<Recipe> breakfastRecipes = new ArrayList<>();

    String favoriteId;
    private final Map<String, String> caloriesMapsId= new HashMap<String, String>();
    private final ArrayList<Integer> calories = new ArrayList<>();
    StringBuilder caloriesString = new StringBuilder();

    List<Integer> targetCalories = new ArrayList<>();
    List<String> targetId = new ArrayList<>();
    List<Recipe> targetRecipes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_explore, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeCategory = view.findViewById(R.id.recipeCategory);
        parentRecyclerView = view.findViewById(R.id.recipeByIngredientRecyclerView);
        parentRecyclerView2 = view.findViewById(R.id.italianRecyclerView);
        parentRecyclerView3 = view.findViewById(R.id.americanRecyclerView);
        parentRecyclerView4 = view.findViewById(R.id.chineseRecyclerView);
        parentRecyclerView5 = view.findViewById(R.id.breakfastRecyclerView);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();

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

        Log.i(TAG, "onViewCreated: "+calories.size());
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
                fetchRecipesByIngredients(ingredients);
            }
        });
    }

    private void fetchRecipesByIngredients(List<Ingredient> ingredients) {
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
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                parentRecyclerView.setVisibility(View.VISIBLE);
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
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                parentRecyclerView2.setVisibility(View.VISIBLE);
                parentRecyclerView3.setVisibility(View.VISIBLE);
                parentRecyclerView4.setVisibility(View.VISIBLE);

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
                if (data.size() == 0) {
                    querySimilarCuisinesRecipe(cuisine);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "failed to display recipes");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                parentRecyclerView5.setVisibility(View.VISIBLE);
                breakfastRecipes.addAll(data);
                breakfastRecipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public static List<Integer> getRecipesForTargetcalorie(ArrayList<Integer> calories, int targetCalories){
            Map<Integer, Integer> map = new HashMap();
            ArrayList<Integer> recipes = new ArrayList<Integer>();
            for(int i=0; i<calories.size(); i++){
                int complement = targetCalories - calories.get(i);
                if(map.containsKey(complement)){
                    recipes.add(map.get(complement));
                    recipes.add(i);
                }else{
                    map.put(calories.get(i),i);
                }

            }
            if(recipes.isEmpty()){
                int total = 0;
                for(int i=0; i<calories.size(); i++){
                    total+=calories.get(i);
                    if(total <= targetCalories){
                        recipes.add(i);
                    }
                }
            }
            return recipes.stream().distinct().collect(
                    Collectors.toList());
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void querySimilarCuisinesRecipeByCalories() {
            favouriteLists = MainActivity.favouriteDatabase.favouriteDao().getFavouriteData();

            FoodClient client = new FoodClient();

            for (int i = 0; i < favouriteLists.size(); i++) {
                favoriteId = favouriteLists.get(i).getId();
                client.getCaloriesById(favouriteLists.get(i).getId(), new NetworkCallback<String>() {
                    @Override
                    public void onSuccess(String data) {

                        caloriesMapsId.put(favoriteId,data);
                        int cal = Integer.parseInt(data);
                        calories.add(cal);
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            targetCalories = getRecipesForTargetcalorie(calories,2000);
            for(String key: caloriesMapsId.keySet()){
                int value = 0;
                if(caloriesMapsId.get(key).equals(targetCalories.get(value).toString())){
                    targetId.add(key);
                }
            }

            for(int p = 0; p<favouriteLists.size();p++){
                if(targetId.get(p).equals(favoriteId)){
                    Recipe target = new Recipe(targetId.get(p), favouriteLists.get(p).getImage(),favouriteLists.get(p).getTitle());
                    targetRecipes.add(target);
                }
            }

        }
    @Override
    public void onRecipeClicked(Recipe food) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.FOOD_ID_ARG, food.getId());
        startActivity(intent);
    }
}