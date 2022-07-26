package com.example.fooding.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
@Entity
public class User {

    @ColumnInfo
    @PrimaryKey
    public long id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    String ingredientName;
    @ColumnInfo
    public String ingredientImage;

    private User() {
    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.id = jsonObject.getLong("id");
        user.name = jsonObject.getString("name");
        user.ingredientName = jsonObject.getString("ingredientName");
        user.ingredientImage = jsonObject.getString("ingredientImage");
        return user;
    }
}
