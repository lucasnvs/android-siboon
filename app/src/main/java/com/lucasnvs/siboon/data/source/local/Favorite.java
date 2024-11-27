package com.lucasnvs.siboon.data.source.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey
    private int productId;
}