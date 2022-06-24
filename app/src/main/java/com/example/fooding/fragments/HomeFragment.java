package com.example.fooding.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fooding.R;
import com.example.fooding.models.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.fooding.adapters.FoodAdapter;
import okhttp3.Headers;

public class HomeFragment extends Fragment {
    public static final String complex_search_url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=f1bb97f5a6b141f1b5f8e17a2eba1296";
    public static final String TAG = "HomeFragment";
    List<Food> Foods;
    EditText etSearch;
//    RecyclerView rvFood;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Foods = new ArrayList<>();

        RecyclerView rvFood = view.findViewById(R.id.rvFood);


        Foods = new ArrayList<>();

        FoodAdapter Foodadapter = new FoodAdapter(getContext(), Foods);

        rvFood.setAdapter(Foodadapter);

        rvFood.setLayoutManager(new LinearLayoutManager(getContext()));
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(complex_search_url, new JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    Foods.addAll(Food.fromJsonArray(results));
                    Foodadapter.notifyDataSetChanged();
                    Log.i(TAG, "food: " + Foods.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");

            }
        });
    }
}