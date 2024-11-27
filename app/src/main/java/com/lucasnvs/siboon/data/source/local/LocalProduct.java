package com.lucasnvs.siboon.data.source.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class LocalProduct {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name;
    public String description;
    public String color;
    public Double price_brl;
    public String formated_price_brl;
    public int discount_brl_percentage;
    public String formated_price_brl_with_discount;
    public int max_installments;
    public int size_type_id;
}
