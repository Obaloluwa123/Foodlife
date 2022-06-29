package com.example.fooding.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fooding.R;
import com.example.fooding.models.Food;

import java.util.ArrayList;

import com.example.fooding.adapters.FoodAdapter;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener,
        SearchView.OnSuggestionListener {

    private EditText etSearch;
    private SearchView recipeSearchView;
    private RecyclerView rvRecipes;

    private ArrayList<Food> aFood;
    private FoodAdapter foodAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        return false;
    }
}

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        etSearch = view.findViewById(R.id.etSearch);
//        recipeSearchView = view.findViewById(R.id.Ing_search_view);
//        rvRecipes = view.findViewById(R.id.rvSearch);
//
//        aFood = new ArrayList<>();
//
//        foodAdapter = new FoodAdapter(getContext(), aFood);
//
//        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, int position) {
//                Toast.makeText(
//                        getContext(),
//                        aFood.get(position).getImage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        rvRecipes.setAdapter(foodAdapter);
//
//        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        recipeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                aFood.clear();
//
//                fetchFood(query);
//
//                recipeSearchView.clearFocus();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//
//            private void fetchFood(String query) {
//                Log.i(TAG, "FETCHING Food");
//                foodclient.getFood(query, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Headers headers, JSON response) {
//                        try {
//                            JSONArray jsonResults;
//                            if (response != null) {
//                                // Get the jsonResults
//                                jsonResults = response.jsonObject.getJSONArray("docs");
//                                // Parse json array into array of model objects
//                                // final ArrayList<Result> results = Result.fromJson(docs);
//                                // add five books into the results array
//                                int jsonResultsLength = jsonResults.length();
//                                final ArrayList<Food> results;
//                                if (jsonResultsLength > 0) {
//                                    if (jsonResultsLength > 0)
//                                        results = new ArrayList<>(Food.fromJson(jsonResults, "Food");
//                                    else
//                                        results = new ArrayList<>(Food.fromJson(jsonResults, "Food");
//                                    // Load model objects into the adapter
//                                    for (Food food : Foods) {
//                                        aFood.add(food); // add result through the adapter
//                                    }
//                                    FoodAdapter.notifyDataSetChanged();
//                                }
//                            }
//                        } catch (JSONException e) {
//                            // Invalid JSON format, show appropriate error.
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                        Log.e(TAG,
//                                "Request failed with code " + statusCode + ". Response message: " + response);
//                    }
//
//
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }
//
//    @Override
//    public boolean onSuggestionSelect(int position) {
//        return false;
//    }
//
//    @Override
//    public boolean onSuggestionClick(int position) {
//        return false;
//    }
//}
//
//    private void fetchFood() {
//    }