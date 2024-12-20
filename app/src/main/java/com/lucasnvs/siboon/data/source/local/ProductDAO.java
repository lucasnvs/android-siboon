package com.lucasnvs.siboon.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductDAO {
    @Query("SELECT * FROM products")
    Flowable<List<LocalProduct>> getAllProducts();

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    Single<LocalProduct> getProductById(Long productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(LocalProduct product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<LocalProduct> products);

    @Query("DELETE FROM products")
    Completable deleteAll();
}