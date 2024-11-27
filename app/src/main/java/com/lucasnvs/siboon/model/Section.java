package com.lucasnvs.siboon.model;

import java.util.List;

public class Section {
    private String title;
    private List<Product> products;

    public Section(String title, List<Product> products) {
        this.title = title;
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
