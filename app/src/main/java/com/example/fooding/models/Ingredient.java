package com.example.fooding.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Ingredient")
public class Ingredient extends ParseObject {
    public static final String INGREDIENT_KEY = "ingredientName";
    public static final String IMAGE_KEY = "image";
    public static final String USER_KEY = "user";

    public String uniqueIngredientSet() {
        return getString(INGREDIENT_KEY);
    }

    public void setName(String ingredientName) {
        put(INGREDIENT_KEY, ingredientName);
    }

    public ParseFile getIngredientImage() {
        return getParseFile(IMAGE_KEY);
    }

    public void setImage(ParseFile parseFile) {
        put(IMAGE_KEY, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }
}