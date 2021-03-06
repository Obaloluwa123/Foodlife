package com.example.fooding.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.fooding.R;
import com.example.fooding.adapters.PagerAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.Recipe;
import com.example.fooding.models.ExtendedRecipe;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ALL")
public class DetailActivity extends AppCompatActivity {

    public static final String FOOD_ID_ARG = "FOOD_ID_ARG";
    public static final String FOOD_DIET_ARG = "FOOD_DIET_ARG";
    public static final String FOOD_MEAL_ARG = "FOOD_MEAL_ARG";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    final FoodClient foodClient = new FoodClient();
    public boolean suggestionShown = false;
    private Dialog suggestionDialog;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Recipe> suggestedRecipes;
    private int suggestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        suggestedRecipes = new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setupFood();

        if (!suggestionShown) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    suggestionShown = true;
                    showSuggestionDialog();
                }
            }, 3000);
        } else {
            return;
        }

    }

    private void setupFood() {
        String foodId = getIntent().getStringExtra(FOOD_ID_ARG);
        String fooddiet = getIntent().getStringExtra(FOOD_DIET_ARG);
        String foodmeal = getIntent().getStringExtra(FOOD_MEAL_ARG);

        if (foodId != null) {
            foodClient.getExtendedFood(foodId, fooddiet, foodmeal, new NetworkCallback<ExtendedRecipe>() {
                @Override
                public void onSuccess(ExtendedRecipe data) {
                    setupViewPager(data);
                    getSuggestionDetails("rice");
                }

                @Override
                public void onFailure(Throwable throwable) {
                }
            });
        }
    }

    public void getSuggestionDetails(String ingredient) {

        foodClient.suggestByIngredients(ingredient, new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> data) {
                suggestedRecipes.addAll(data);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void showSuggestionDialog() {
        Random random = new Random();
        if (!suggestedRecipes.isEmpty()) {
            suggestionIndex = random.nextInt(suggestedRecipes.size());
        }
        suggestionDialog = new Dialog(this);
        suggestionDialog.setContentView(R.layout.item_suggestion);
        suggestionDialog.getWindow().getAttributes().windowAnimations = R.style.SuggestionDialogAnimation;
        suggestionDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView suggestedFoodImage = suggestionDialog.findViewById(R.id.suggested_food_image);
        TextView suggestedFoodName = suggestionDialog.findViewById(R.id.suggested_food_name);
        ImageButton closeDialog = suggestionDialog.findViewById(R.id.close_suggestion_dialog);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestionDialog.dismiss();
            }
        });

        if (suggestedRecipes != null && suggestedRecipes.size() > suggestionIndex) {
            Glide.with(getApplicationContext()).load(suggestedRecipes.get(suggestionIndex).getImage()).into(suggestedFoodImage);
            suggestedFoodName.setText(suggestedRecipes.get(suggestionIndex).getTitle());
            suggestionDialog.show();
        }
    }

    private void setupViewPager(ExtendedRecipe food) {
        PagerAdapter pagerAdapter = new PagerAdapter(food, fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
