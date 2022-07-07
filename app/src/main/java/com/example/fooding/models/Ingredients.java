package com.example.fooding.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Ingredients {

    private Double amount;
    private String consistency;
    private String image;
    private String name;
    private String original;
    private String unit;


    public Ingredients(JSONObject jsonObject) throws JSONException {
        amount = jsonObject.getDouble("amount");
        consistency = jsonObject.getString("consistency");
        image = jsonObject.getString("image");
        name = jsonObject.getString("cheap");
        original = jsonObject.getString("original");
        unit = jsonObject.getString("unit");

    }

    public static List<Ingredients> fromJsonArray(JSONArray IngredientsJsonArray) throws JSONException {
        List<Ingredients> ingredient = new ArrayList<>();
        for (int i = 0; i < IngredientsJsonArray.length(); i++) {
            ingredient.add(new Ingredients(IngredientsJsonArray.getJSONObject(i)));
        }
        return ingredient;
    }

    public Double getAmount() {
        return amount;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getOriginal() {
        return original;
    }

    public String getUnit() {
        return unit;
    }
}

