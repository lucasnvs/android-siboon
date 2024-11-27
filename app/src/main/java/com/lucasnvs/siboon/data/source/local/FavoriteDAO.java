package com.lucasnvs.siboon.data.source.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Insert
    void insertFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);

    @Query("SELECT productId FROM favorites")
    List<Integer> getAllFavoriteIds();
}