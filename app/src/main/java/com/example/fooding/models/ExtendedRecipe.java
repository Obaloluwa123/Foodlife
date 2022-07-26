package com.example.fooding.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ExtendedRecipe implements Parcelable {

    public String title;
    public String image;
    public int aggregateLikes;
    public int readyInMinutes;
    public String summary;
    public boolean cheap;
    public boolean dairyFree;
    public boolean glutenFree;
    private Integer amount;
    private String consistency;
    private String aisle;
    private String original;
    private String unit;
    private List<ExtendedIngredients> extendedIngredients;
    @SuppressWarnings("unused")
    public int id;
    public String sourceName;
    public String sourceUrl;
    public boolean Vegan;
    public boolean Vegetarian;
    public boolean veryHealthy;


    public ExtendedRecipe(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        image = jsonObject.getString("image");
        if (jsonObject.has("likes")) aggregateLikes = jsonObject.getInt("likes");
        readyInMinutes = jsonObject.getInt("readyInMinutes");
        summary = jsonObject.getString("summary");
        cheap = jsonObject.getBoolean("cheap");
        dairyFree = jsonObject.getBoolean("dairyFree");
        glutenFree = jsonObject.getBoolean("glutenFree");
        id = jsonObject.getInt("id");
        sourceName = jsonObject.getString("sourceName");

        if (jsonObject.has("sourceUrl")) sourceUrl = jsonObject.getString("sourceUrl");
        if (jsonObject.has("Vegan")) Vegan = jsonObject.getBoolean("Vegan");
        if (jsonObject.has("Vegetarian")) Vegetarian = jsonObject.getBoolean("Vegetarian");
        if (jsonObject.has("veryHealthy")) veryHealthy = jsonObject.getBoolean("veryHealthy");

    }

    public static ExtendedRecipe fromJsonObject(JSONObject object) throws JSONException {
        return new ExtendedRecipe(object);
    }

    public static List<ExtendedRecipe> fromJsonArray(JSONArray IngredientsJsonArray) throws JSONException {
        List<ExtendedRecipe> ingredient = new ArrayList<>();
        for (int i = 0; i < IngredientsJsonArray.length(); i++) {
            ingredient.add(new ExtendedRecipe(IngredientsJsonArray.getJSONObject(i)));
        }
        return ingredient;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected ExtendedRecipe(Parcel in) {
        title = in.readString();
        image = in.readString();
        aggregateLikes = in.readInt();
        readyInMinutes = in.readInt();
        summary = in.readString();
        cheap = in.readBoolean();
        dairyFree = in.readBoolean();
        glutenFree = in.readBoolean();
        id = in.readInt();
        sourceName = in.readString();
        sourceUrl = in.readString();
        Vegan = in.readBoolean();
        Vegetarian = in.readBoolean();
        veryHealthy = in.readBoolean();
        aisle = in.readString();
        image = in.readString();
        amount = in.readInt();
        unit = in.readString();
    }

    public static final Creator<ExtendedRecipe> CREATOR = new Creator<ExtendedRecipe>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public ExtendedRecipe createFromParcel(Parcel in) {
            return new ExtendedRecipe(in);
        }

        @Override
        public ExtendedRecipe[] newArray(int size) {
            return new ExtendedRecipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeInt(aggregateLikes);
        parcel.writeInt(readyInMinutes);
        parcel.writeString(summary);
        parcel.writeBoolean(cheap);
        parcel.writeBoolean(dairyFree);
        parcel.writeBoolean(glutenFree);
        parcel.writeString(sourceName);
        parcel.writeString(sourceUrl);
        parcel.writeBoolean(Vegan);
        parcel.writeBoolean(Vegetarian);
        parcel.writeBoolean(veryHealthy);
        parcel.writeString(aisle);
        parcel.writeString(image);
        parcel.writeInt(amount);
        parcel.writeString(unit);

    }
}
