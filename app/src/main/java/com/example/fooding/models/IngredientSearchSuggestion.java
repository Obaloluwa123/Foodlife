package com.example.fooding.models;

public class IngredientSearchSuggestion {

    public String name;
    public String image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public IngredientSearchSuggestion(String name, String image) {
        this.name = name;
        this.image = image;
    }

}

