package com.example.fooding.fragments;

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
import com.example.fooding.BuildConfig;
import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.adapters.RecipeAdapter;
import com.example.fooding.bottomsheets.FilterBottomSheet;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Recipe;
import com.example.fooding.models.Search;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@SuppressWarnings("ALL")
public class RecipeSearchFragment extends Fragment implements RecipeAdapter.FoodAdapterListener {

    protected List<Search> allSearches;
    private FloatingActionButton filterFab;
    private SearchView recipeSearchView;

    private ArrayList<Recipe> recipes;
    private RecipeAdapter recipeAdapter;

    private String selectedDiet;
    private String selectedMeal;
    private String currentSearch;
    private String myResponse1;

    private static final Object API_KEY = BuildConfig.SPOONACULAR_KEY;
    private static final String complex_search_url = String.format("https://api.spoonacular.com/recipes/complexSearch?apiKey=%s", API_KEY);

    public static final String TAG = "RecipeFragment";

    public RecipeSearchFragment() {
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
        recipes = new ArrayList<>();
        recipeSearchView = view.findViewById(R.id.recipeSearchView);
        RecyclerView rvRecipes = view.findViewById(R.id.rvSearch);
        filterFab = view.findViewById(R.id.filter_fab);
        setupFAB();
        recipeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                    @Override
                                                    public boolean onQueryTextSubmit(String query) {
                                                        currentSearch = recipeSearchView.getQuery().toString();
                                                        saveSearchQuery(currentSearch);
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
        recipeAdapter = new RecipeAdapter(recipes, this);
        rvRecipes.setAdapter(recipeAdapter);
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
        client.getIngredients(selectedDiet, selectedMeal, searchValue, new NetworkCallback<List<Recipe>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Recipe> data) {
                recipes.clear();
                recipes.addAll(data);
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, throwable.toString());
            }
        });
    }

    private void saveSearchQuery(String searchName) {
        Search search = new Search();
        search.setSearchName(searchName);
        search.setUser(ParseUser.getCurrentUser());
        search.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                } else {
                }
            }
        });
    }

    @Override
    public void onFoodClicked(Recipe recipe) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.FOOD_ID_ARG, recipe.getId());
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
                getDatafromDB();
            } else {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(complex_search_url, new JsonHttpResponseHandler() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;
                        final String myResponse = jsonObject.toString();
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

                    }

                });
            }

        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void getDatafromDB() {

        try {
            JSONObject jsonObject = new JSONObject(myResponse1);
            JSONArray results = jsonObject.getJSONArray("results");
            recipes.addAll(Recipe.fromJsonArray(results));
            recipeAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


