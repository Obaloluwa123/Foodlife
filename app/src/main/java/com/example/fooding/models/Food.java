package com.example.fooding.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Food {
    String title;
    String image;
    String id;

    public Food(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");
        id = jsonObject.getString("id");
    }
    public static List<Food> fromJsonArray (JSONArray FoodJsonArray) throws JSONException {
        List<Food> meals = new ArrayList<>();
        for (int i = 0; i < FoodJsonArray.length(); i++) {
            meals.add(new Food(FoodJsonArray.getJSONObject(i)));
        }
        return meals;
    }
    public String getId() {return id;}
    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
