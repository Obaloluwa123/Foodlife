package com.example.fooding.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;


public class FoodExtended implements Parcelable {

    public String title;
    public String image;
    public int aggregateLikes;
    public int readyInMinutes;
    public String summary;
    public boolean cheap;
    public boolean dairyFree;
    public boolean glutenFree;
    public int id;
    public String sourceName;
    public String sourceUrl;
    public boolean Vegan;
    public boolean Vegetarian;
    public boolean veryHealthy;


    public FoodExtended(JSONObject jsonObject) throws JSONException {
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

    public static FoodExtended fromJsonObject(JSONObject object) throws JSONException {
        return new FoodExtended(object);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected FoodExtended(Parcel in) {
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
    }

    public static final Creator<FoodExtended> CREATOR = new Creator<FoodExtended>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public FoodExtended createFromParcel(Parcel in) {
            return new FoodExtended(in);
        }

        @Override
        public FoodExtended[] newArray(int size) {
            return new FoodExtended[size];
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
    }
}
