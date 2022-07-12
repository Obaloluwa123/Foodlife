package com.example.fooding.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.BuildConfig;
import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.adapters.FoodAdapter;
import com.example.fooding.bottomsheets.FilterBottomSheet;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class RecipeFragment extends Fragment implements FoodAdapter.FoodAdapterListener {

    private FloatingActionButton filterFab;
    private SearchView recipeSearchView;

    private ArrayList<Food> foods;
    private FoodAdapter foodAdapter;

    private String selectedDiet;
    private String selectedMeal;
    private String currentSearch;

    private static final Object API_KEY = BuildConfig.SPOONACULAR_KEY;
    private static final String complex_search_url = String.format("https://api.spoonacular.com/recipes/complexSearch?apiKey=%s", API_KEY);

    public static final String TAG = "RecipeFragment";

    public RecipeFragment() {
        selectedDiet = null;
        selectedMeal = null;
        currentSearch = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foods = new ArrayList<>();
        recipeSearchView = view.findViewById(R.id.recipeSearchView);
        RecyclerView rvRecipes = view.findViewById(R.id.rvSearch);
        filterFab = view.findViewById(R.id.filter_fab);
        recipeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                    @Override
                                                    public boolean onQueryTextSubmit(String query) {
                                                        currentSearch = recipeSearchView.getQuery().toString();
                                                        getRecipes(currentSearch);
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onQueryTextChange(String newText) {
                                                        CharSequence searchValue = recipeSearchView.getQuery().toString();
                                                        return false;
                                                    }
                                                }
        );
        foodAdapter = new FoodAdapter(foods, this);
        rvRecipes.setAdapter(foodAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        setupFAB();
        getRecipes(currentSearch);
    }

    private void setupFAB() {
        filterFab.setOnClickListener(view -> {
            FilterBottomSheet bottomSheet = new FilterBottomSheet(selectedDiet, selectedMeal);
            bottomSheet.show(getChildFragmentManager(), FilterBottomSheet.TAG);

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeSearchView.clearFocus();
    }

    public void setVariables(String diet, String meal) {
        this.selectedDiet = diet;
        this.selectedMeal = meal;
        if (currentSearch != null && currentSearch.length() > 0) {
            getRecipes(currentSearch);
        }
        recipeSearchView.clearFocus();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchRecipe = menu.findItem(R.id.rvSearch);
        searchRecipe.setVisible(true);
        SearchView searchView = (SearchView) searchRecipe.getActionView();
        searchView.setQueryHint("Search Recipe");

    }

    private void getRecipes(String searchValue) {
        FoodClient client = new FoodClient();
        client.getIngredients(selectedDiet, selectedMeal, searchValue, new NetworkCallback<List<Food>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Food> data) {
                foods.clear();
                foods.addAll(data);
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {

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


