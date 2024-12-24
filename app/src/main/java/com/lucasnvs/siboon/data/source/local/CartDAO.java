package com.lucasnvs.siboon.data.source.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM cart")
    Flowable<List<LocalCart>> getAllCart();

    @Query("SELECT * FROM cart WHERE productId = :productId LIMIT 1")
    Maybe<LocalCart> getCartByProductId(Long productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCart(LocalCart localCart);

    @Update
    void updateCart(LocalCart localCart);

    @Delete
    void deleteCart(LocalCart localCart);

    @Query("DELETE FROM cart WHERE productId = :productId")
    void deleteCartByProductId(Long productId);
}
