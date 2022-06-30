package com.example.fooding.fragments;

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

public class SearchFragment extends Fragment{
    public static final String Tag = "SearchFragment";

    private ArrayList<Food> foods;
    private FoodAdapter foodAdapter;
    public static final String complex_search_url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=f1bb97f5a6b141f1b5f8e17a2eba1296";
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

        Button btnSubmit = view.findViewById(R.id.searchBtn);
        SearchView recipeSearchView = view.findViewById(R.id.recipeSearchView);
        RecyclerView rvRecipes = view.findViewById(R.id.rvSearch);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "search button pressed");
                Recipes();
            }
        });

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

    private boolean Recipes() {
        FoodClient client = new FoodClient();
        client.getRecipes("query", new JsonHttpResponseHandler() {
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


