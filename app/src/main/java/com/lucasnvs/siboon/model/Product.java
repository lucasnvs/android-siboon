package com.lucasnvs.siboon.model;

import java.util.List;

public class Product {
    private Long id;
    private String title;
    private double price;
    private String imageSrc;

    private List<String> additionalImagesSrc;
    private String description;
    private Integer installments;

    public Product(Long id, String title, double price, String imageSrc, List<String> additionalImagesSrc, String description, Integer installments) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageSrc = imageSrc;
        this.additionalImagesSrc = additionalImagesSrc;
        this.description = description;
        this.installments = installments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public List<String> getAdditionalImagesSrc() {
        return additionalImagesSrc;
    }

    public void setAdditionalImagesSrc(List<String> additionalImagesSrc) {
        this.additionalImagesSrc = additionalImagesSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }
}
