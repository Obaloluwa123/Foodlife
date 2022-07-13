package com.example.fooding.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.adapters.SelectedIngredientsAdapter;
import com.example.fooding.adapters.SuggestionsAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Ingredient;
import com.example.fooding.models.IngredientSearchSuggestion;
import com.example.fooding.models.Ingredients;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class PantryFragment extends Fragment implements SearchView.OnQueryTextListener, SuggestionsAdapter.SuggestionAdapterListener {

    protected List<Ingredient> allIngredients;
    private final List<IngredientSearchSuggestion> selectedSuggestions = new ArrayList<>();
    private final List<Ingredients> selectedIngredients = new ArrayList<>();
    private SearchView recipeSearchView;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView suggestedRecyclerView;
    private TextView ingredientNameTextView;

    private final SuggestionsAdapter suggestionsAdapter = new SuggestionsAdapter();
    private SelectedIngredientsAdapter ingredientsAdapter;
    final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            allIngredients.remove(viewHolder.getAdapterPosition());
            ingredientsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            Toast.makeText(getContext(), "Ingredient deleted", Toast.LENGTH_SHORT).show();
            queryIngredients();
        }

        private void queryIngredients() {
            ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
            query.include(Ingredient.USER_KEY);
            query.findInBackground(new FindCallback<Ingredient>() {
                @Override
                public void done(List<Ingredient> ingredients, ParseException e) {
                    if (e != null) {
                    }
                }
            });
        }
    };
    private final FoodClient client = new FoodClient();

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
        ingredientNameTextView = view.findViewById(R.id.etRecipe);
        recipeSearchView.setOnQueryTextListener(this);


        setupRecyclerView();
        queryIngredients();
    }

    private void setupRecyclerView() {
        suggestedRecyclerView.setAdapter(suggestionsAdapter);
        suggestionsAdapter.listener = this;

        allIngredients = new ArrayList<>();
        ingredientsAdapter = new SelectedIngredientsAdapter(getContext(), allIngredients);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(ingredientsRecyclerView);
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
                suggestionsAdapter.suggestions = data;
                suggestionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {
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
        String ingredientName = ingredientNameTextView.getText().toString();
        selectedSuggestions.add(suggestion);
        ingredientsAdapter.notifyDataSetChanged();

        suggestionsAdapter.suggestions = new ArrayList<>();
        suggestionsAdapter.notifyDataSetChanged();

        recipeSearchView.setQuery("", false);
        saveIngredient(suggestion.name);

    }

    private void saveIngredient(String ingredientName) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        ingredient.setUser(ParseUser.getCurrentUser());
        ingredient.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                } else {
                }
            }
        });
    }

    private void queryIngredients() {
        ParseQuery<Ingredient> query = ParseQuery.getQuery(Ingredient.class);
        query.include(Ingredient.USER_KEY);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Ingredient>() {
            @Override
            public void done(List<Ingredient> ingredients, ParseException e) {
                if (e != null) {
                    return;
                }

                for (Ingredient ingredient : ingredients) {
                }

                allIngredients.addAll(ingredients);
                ingredientsAdapter.notifyDataSetChanged();
            }
        });
    }
}
