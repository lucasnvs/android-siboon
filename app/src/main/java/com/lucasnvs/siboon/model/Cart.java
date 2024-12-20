package com.lucasnvs.siboon.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Cart {

    private Long productId;

    private Product product;

    private int quantity = 0;

    private String createdAt;

    public Cart(Long productId, Product product, int quantity, String createdAt) {
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
