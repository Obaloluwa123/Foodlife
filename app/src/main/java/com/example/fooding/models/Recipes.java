package com.example.fooding.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipes {

    private int servings;
    private String title;
    private String imageURL;
    private String sourceURL;
    private String summary;
    private String steps;
    private String ingredients;

    public Recipes(JSONObject jsonObject) throws JSONException {
        servings = jsonObject.getInt("servings");
        title = jsonObject.getString("title");
        imageURL = jsonObject.getString("imageURL");
        sourceURL = jsonObject.getString("sourceURL");
        summary = jsonObject.getString("summary");
        steps = jsonObject.getString("steps");
        ingredients = jsonObject.getString("ingredients");

    }

    public static List<Recipes> fromJsonArray(JSONArray foodJsonArray) throws JSONException {
        List<Recipes> recipes = new ArrayList<>();
        for (int i = 0; i < foodJsonArray.length(); i++) {
            recipes.add(new Recipes(foodJsonArray.getJSONObject(i)));
        }
        return recipes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
