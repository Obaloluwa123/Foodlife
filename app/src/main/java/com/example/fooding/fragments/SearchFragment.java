package com.example.fooding.fragments;

import static com.example.fooding.clients.FoodClient.API_KEY;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fooding.R;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.models.Food;

import java.util.ArrayList;

import com.example.fooding.adapters.FoodAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class SearchFragment extends Fragment {
    public static final String Tag = "SearchFragment";

    private ArrayList<Food> foods;
    private FoodAdapter foodAdapter;
    public static final String TAG = "SearchFragment";

    public SearchFragment() {
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
        SearchView recipeSearchView = view.findViewById(R.id.recipeSearchView);
        RecyclerView rvRecipes = view.findViewById(R.id.rvSearch);

        recipeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    CharSequence searchValue = recipeSearchView.getQuery().toString();
                    Recipes(searchValue);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    CharSequence searchValue = recipeSearchView.getQuery().toString();
                    return false;
                }
            }
        );
        foodAdapter = new FoodAdapter(getContext(), foods);
        rvRecipes.setAdapter(foodAdapter);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
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

    private boolean Recipes(CharSequence searchValue) {
        FoodClient client = new FoodClient();
        client.getIngredients(searchValue.toString(), new JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess: " +  json.toString());
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results);
                    foods.addAll(Food.fromJsonArray(results));
                    foodAdapter.notifyDataSetChanged();
                    Log.i(TAG, "food: " + foods.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
        return false;
    }
}


