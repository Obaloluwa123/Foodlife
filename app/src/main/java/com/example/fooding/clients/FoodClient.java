package com.example.fooding.clients;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fooding.BuildConfig;
import com.example.fooding.models.Food;
import com.example.fooding.models.FoodExtended;
import com.example.fooding.models.IngredientSearchSuggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

@SuppressWarnings("unused")
public class FoodClient extends AsyncHttpClient {
    public static final String API_KEY = BuildConfig.SPOONACULAR_KEY;
    public static final String RECIPE_SEARCH_URL = String.format("https://api.spoonacular.com/recipes/complexSearch?apiKey=%s", API_KEY);
    public static final String INGREDIENTS_SEARCH_URL = String.format("https://api.spoonacular.com/recipes/findByIngredients/{id}ingredientWidget.json?apiKey=%s", API_KEY);
    public static final String EXTENDED_FOOD_TEMPLATE = "https://api.spoonacular.com/recipes/%s/information?apiKey=%s";
    public static final String BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/";
    public static final String INGREDIENT_AUTO_COMPLETE_URL = String.format("https://api.spoonacular.com/food/ingredients/autocomplete?apiKey=%s", API_KEY);

    public FoodClient() {
        super();
    }

    public void getRecipes(String query, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("query", query);
        params.put("number", 50);
        get(RECIPE_SEARCH_URL, params, handler);
    }

    public void getExtendedFood(String id, String diet, String meal, NetworkCallback<FoodExtended> callback) {
        RequestParams params = new RequestParams();
        params.put("addRecipeInformation", true);
        params.put("fillIngredients", true);
        params.put("type", meal);
        params.put("diet", diet);
        params.put("number", 50);
        String url = String.format(EXTENDED_FOOD_TEMPLATE, id, API_KEY);

        get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    callback.onSuccess(FoodExtended.fromJsonObject(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getIngredients(String diet, String meal, String query, NetworkCallback<List<Food>> callback) {
        RequestParams params = new RequestParams();
        params.put("query", query);
        params.put("diet", diet);
        params.put("type", meal);
        params.put("number", 50);
        get(RECIPE_SEARCH_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i("Results", "Results: " + results);
                    callback.onSuccess(Food.fromJsonArray(results));
                } catch (JSONException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void getSuggestions(String query, NetworkCallback<List<IngredientSearchSuggestion>> callback) {
        RequestParams params = new RequestParams();
        params.put("query", query);
        params.put("number", 5);
        get(INGREDIENT_AUTO_COMPLETE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray array = json.jsonArray;
                try {
                    List<IngredientSearchSuggestion> results = new ArrayList<>();
                    Log.e("TAG", "onSuccess: " + array);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        IngredientSearchSuggestion suggestion = new IngredientSearchSuggestion(
                                object.getString("name"),
                                object.getString("image")
                        );
                        results.add(suggestion);
                    }
                    callback.onSuccess(results);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

}
