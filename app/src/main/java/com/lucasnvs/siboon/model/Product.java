package com.lucasnvs.siboon.model;

public class Product {
    private String title;
    private double price;
    private String imageUrl;

    public Product(String title, double price, String imageUrl) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
