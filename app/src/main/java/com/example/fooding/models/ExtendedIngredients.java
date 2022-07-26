package com.example.fooding.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExtendedIngredients {

    private final Integer amount;
    private final String image;
    private final String name;
    private final String unit;


    public ExtendedIngredients(JSONObject jsonObject) throws JSONException {
        amount = jsonObject.getInt("amount");
        image = jsonObject.getString("image");
        unit = jsonObject.getString("unit");
        name = jsonObject.getString("name");

    }

    public static List<ExtendedIngredients> fromJsonArray(JSONArray IngredientsJsonArray) throws JSONException {
        List<ExtendedIngredients> ingredient = new ArrayList<>();
        for (int i = 0; i < IngredientsJsonArray.length(); i++) {
            ingredient.add(new ExtendedIngredients(IngredientsJsonArray.getJSONObject(i)));
        }
        return ingredient;
    }

    public Integer getAmount() {
        return amount;
    }


    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}

