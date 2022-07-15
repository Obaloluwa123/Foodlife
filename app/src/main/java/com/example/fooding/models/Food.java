package com.example.fooding.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Food {
    private final String title;
    private final String image;
    private final String id;


    public Food(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");
        id = jsonObject.getString("id");

    }


    public static List<Food> fromJsonArray(JSONArray foodJsonArray) throws JSONException {
        List<Food> meals = new ArrayList<>();
        for (int i = 0; i < foodJsonArray.length(); i++) {
            meals.add(new Food(foodJsonArray.getJSONObject(i)));
        }
        return meals;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }


}
