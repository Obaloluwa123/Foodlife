package com.example.fooding.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.fooding.R;
import com.example.fooding.adapters.PagerAdapter;
import com.example.fooding.clients.FoodClient;
import com.example.fooding.clients.NetworkCallback;
import com.example.fooding.models.FoodExtended;
import com.google.android.material.tabs.TabLayout;

@SuppressWarnings("ALL")
public class DetailActivity extends AppCompatActivity {

    public static final String FOOD_ID_ARG = "FOOD_ID_ARG";
    public static final String FOOD_DIET_ARG = "FOOD_DIET_ARG";
    public static final String FOOD_MEAL_ARG = "FOOD_MEAL_ARG";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    final FoodClient foodClient = new FoodClient();
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setupFood();
    }

    private void setupFood() {
        String foodId = getIntent().getStringExtra(FOOD_ID_ARG);
        String fooddiet = getIntent().getStringExtra(FOOD_DIET_ARG);
        String foodmeal = getIntent().getStringExtra(FOOD_MEAL_ARG);

        if (foodId != null) {
            foodClient.getExtendedFood(foodId, fooddiet, foodmeal, new NetworkCallback<FoodExtended>() {
                @Override
                public void onSuccess(FoodExtended data) {
                    setupViewPager(data);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("Failed", throwable.toString());
                }
            });
        }
    }

    private void setupViewPager(FoodExtended food) {
        PagerAdapter pagerAdapter = new PagerAdapter(food, fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
