package com.example.fooding.favourite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert
    void addData(FavouriteList favouriteList);

    @Query("select * from favouritelist")
    List<FavouriteList> getFavouriteData();

    @Query("SELECT EXISTS(SELECT * FROM favouritelist WHERE Id=:id)")
    Boolean exists(String id);


    @Delete
    void delete(FavouriteList favouriteList);

}

