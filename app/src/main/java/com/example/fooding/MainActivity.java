package com.example.fooding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fooding.models.food;
import com.facebook.stetho.common.ArrayListAccumulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.foodadapter;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String complex_search_url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=f1bb97f5a6b141f1b5f8e17a2eba1296";
    public static final String TAG = "MainActivity";

    List<food> Foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvFood = findViewById(R.id.rvFood);

        Foods = new ArrayList<>();

        foodadapter Foodadapter = new foodadapter(this, Foods);

        rvFood.setAdapter(Foodadapter);

        rvFood.setLayoutManager(new LinearLayoutManager(this));

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
                    Foods.addAll(food.fromJsonArray(results));
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