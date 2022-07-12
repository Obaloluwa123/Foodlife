package com.example.fooding.favourite;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@SuppressWarnings("ALL")
@Entity(tableName = "favouritelist")
public class FavouriteList {

    @PrimaryKey
    @NonNull
    private String Id;

    @ColumnInfo(name = "title")
    private String Title;

    @ColumnInfo(name = "image")
    private String Image;


    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

}
