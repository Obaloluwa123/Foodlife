package com.example.fooding.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.adapters.SelectedIngredientsAdapter;
import com.example.fooding.adapters.SuggestionsAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.IngredientSearchSuggestion;

import java.util.ArrayList;
import java.util.List;


public class PantryFragment extends Fragment implements SearchView.OnQueryTextListener, SuggestionsAdapter.SuggestionAdapterListener {

    private List<IngredientSearchSuggestion> selectedSuggestions = new ArrayList<>();

    private SearchView recipeSearchView;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView suggestedRecyclerView;

    private SuggestionsAdapter suggestionsAdapter = new SuggestionsAdapter();
    private SelectedIngredientsAdapter ingredientsAdapter = new SelectedIngredientsAdapter();
    private FoodClient client = new FoodClient();

    public PantryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeSearchView = view.findViewById(R.id.ingredientSearchView);
        ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        suggestedRecyclerView = view.findViewById(R.id.suggestedIngredientsRecyclerView);
        recipeSearchView.setOnQueryTextListener(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        suggestedRecyclerView.setAdapter(suggestionsAdapter);
        suggestionsAdapter.listener = this;

        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.ingredients = selectedSuggestions;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 0) {
            onNewQuery(newText);
        } else {
            suggestionsAdapter.suggestions = new ArrayList<>();
            suggestionsAdapter.notifyDataSetChanged();
        }
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onNewQuery(String query) {
        client.getSuggestions(query, new NetworkCallback<List<IngredientSearchSuggestion>>() {
            @Override
            public void onSuccess(List<IngredientSearchSuggestion> data) {
                Log.e("TAG", "onSuccess: " + data.toString());
                suggestionsAdapter.suggestions = data;
                suggestionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("TAG", "There was an error");
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeSearchView.clearFocus();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSuggestionClicked(IngredientSearchSuggestion suggestion) {
        selectedSuggestions.add(suggestion);
        ingredientsAdapter.notifyDataSetChanged();

        suggestionsAdapter.suggestions = new ArrayList<>();
        suggestionsAdapter.notifyDataSetChanged();

        recipeSearchView.setQuery("", false);
    }
}
