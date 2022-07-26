package com.example.fooding.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooding.R;
import com.example.fooding.activities.DetailActivity;
import com.example.fooding.activities.MainActivity;
import com.example.fooding.adapters.FavouriteAdapter;
import com.example.fooding.favourite.FavouriteList;

import java.util.List;

@SuppressWarnings("ALL")
public class FavouriteRecipeFragment extends Fragment implements FavouriteAdapter.FavouriteAdapterListener {

//change
    private FavouriteAdapter favouriteAdapter;
    private RecyclerView favouriteRecipesRecyclerView;
    private TextView noItemText;

    private String selectedDiet;
    private String selectedMeal;
    private String currentSearch;

    public static final String TAG = "FavouriteFragment";

    public FavouriteRecipeFragment() {
        selectedDiet = null;
        selectedMeal = null;
        currentSearch = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouriteRecipesRecyclerView = view.findViewById(R.id.rvFavourite);
        noItemText = view.findViewById(R.id.NoItemText);

        List<FavouriteList> favoriteLists = MainActivity.favouriteDatabase.favouriteDao().getFavouriteData();

        if (favoriteLists.isEmpty()) {
            noItemText.setVisibility(View.VISIBLE);
        }
        favouriteAdapter = new FavouriteAdapter(favoriteLists, this);
        favouriteRecipesRecyclerView.setAdapter(favouriteAdapter);
        favouriteRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onFavouriteFoodClicked(FavouriteList favouriteList) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.FOOD_ID_ARG, favouriteList.getId());
        startActivity(intent);
    }


}