package com.lucasnvs.siboon.model;

public class Cart {
    private Product product;
    private int quantity;
    private String createdAt;

    public Cart(Product product, int quantity, String createdAt) {
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
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