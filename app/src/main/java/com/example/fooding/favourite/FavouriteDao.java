package com.example.fooding.favourite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert
    public void addData(FavouriteList favouriteList);

    @Query("select * from favouritelist")
    public List<FavouriteList> getFavouriteData();

    @Query("SELECT EXISTS(SELECT * FROM favouritelist WHERE Id=:id)")
    public Boolean exists(String id);


    @Delete
    public void delete(FavouriteList favouriteList);

}

