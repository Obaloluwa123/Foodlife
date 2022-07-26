package com.example.fooding.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.example.fooding.R;
import com.example.fooding.favourite.FavouriteDatabase;
import com.example.fooding.fragments.FavouriteFragment;
import com.example.fooding.fragments.FridgeFragment;
import com.example.fooding.fragments.ProfileFragment;
import com.example.fooding.fragments.RecipeExploreFragment;
import com.example.fooding.fragments.RecipeSearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static FavouriteDatabase favouriteDatabase;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favouriteDatabase = Room.databaseBuilder(getApplicationContext(), FavouriteDatabase.class, "favourite").allowMainThreadQueries().build();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_pantry:
                        fragment = new FridgeFragment();
                        break;
                    case R.id.action_recipe:
                        fragment = new RecipeSearchFragment();
                        break;
                    case R.id.action_explore:
                        fragment = new RecipeExploreFragment();
                        break;
                    case R.id.action_favorite:
                        fragment = new FavouriteFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;

            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Fragment fragment = new FavouriteFragment();
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onLogout() {
        ParseUser.logOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}

