package com.example.fooding.favourite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavouriteList.class}, version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();
}