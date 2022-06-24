package com.example.fooding.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class food {
    String title;
    String image;

    public food(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");



    }
    public static List<food> fromJsonArray (JSONArray FoodJsonArray) throws JSONException {
        List<food> Food = new ArrayList<>();
        for (int i = 0; i < FoodJsonArray.length(); i++) {
            Food.add(new food(FoodJsonArray.getJSONObject(i)));
        }
        return Food;

    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return  String.format(image);
    }
}
