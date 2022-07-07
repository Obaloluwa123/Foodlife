package com.example.fooding.fragments;

import static com.example.fooding.clients.FoodClient.API_KEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.adapters.FoodAdapter;
import com.example.fooding.bottomsheets.FilterBottomSheet;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@SuppressWarnings({"ConstantConditions", "UnusedAssignment", "unused"})
public class RecipeFragment extends Fragment implements FoodAdapter.FoodAdapterListener {

    private FloatingActionButton filterFab;
    private SearchView recipeSearchView;

    private ArrayList<Food> foods;
    private FoodAdapter foodAdapter;

    private String selectedDiet;
    private String selectedMeal;
    private String currentSearch;
    private String myResponse1;

    public static final String complex_search_url = String.format("https://api.spoonacular.com/recipes/complexSearch?apiKey=%s", API_KEY);

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
                                                        Recipes(currentSearch);
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
        Recipes(currentSearch);
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
            Recipes(currentSearch);
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

    private void Recipes(String searchValue) {
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

    public void storeData() {

        String url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=%s";
        boolean apikey = false;

        try {
            DB snappydb = DBFactory.open(requireContext());
            apikey = snappydb.exists(url);
            if (apikey) {
                myResponse1 = snappydb.get(url);
                Log.d("Url", myResponse1);
                getDatafromDB();
                Log.i(TAG, "fromcache");
            } else {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(complex_search_url, new JsonHttpResponseHandler() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        final String myResponse = jsonObject.toString();
                        Log.i(TAG, "JSON" + myResponse);
                        try {
                            DB snappydb = DBFactory.open(getContext());
                            snappydb.put(url, myResponse);
                            snappydb.close();
                            storeData();
                        } catch (SnappydbException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d(TAG, "onFailure");

                    }

                });
            }

        } catch (SnappydbException e) {
            e.printStackTrace();
        }

    }


    public void getDatafromDB() {

        try {
            Log.d(TAG, myResponse1);
            JSONObject jsonObject = new JSONObject(myResponse1);
            JSONArray results = jsonObject.getJSONArray("results");
            Log.i(TAG, "Results: " + results);
            foods.addAll(Food.fromJsonArray(results));
            foodAdapter.notifyDataSetChanged();
            Log.i(TAG, "food: " + foods.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


