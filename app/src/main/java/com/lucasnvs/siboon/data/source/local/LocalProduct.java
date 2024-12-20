package com.lucasnvs.siboon.data.source.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "products")
public class LocalProduct {
    @PrimaryKey(autoGenerate = true) public Long id;

    private String name;

    private String description;

    private String color;

    private Double priceBrl;

    private int discountBrlPercentage;

    private int maxInstallments;

    private int sizeTypeId;

    private String principalImage;

    private List<String> additionalImages;

    public LocalProduct(Long id, String name, String description, String color, Double priceBrl, int discountBrlPercentage, int maxInstallments, int sizeTypeId, String principalImage, List<String> additionalImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.priceBrl = priceBrl;
        this.discountBrlPercentage = discountBrlPercentage;
        this.maxInstallments = maxInstallments;
        this.sizeTypeId = sizeTypeId;
        this.principalImage = principalImage;
        this.additionalImages = additionalImages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPriceBrl() {
        return priceBrl;
    }

    public void setPriceBrl(Double priceBrl) {
        this.priceBrl = priceBrl;
    }

    public int getDiscountBrlPercentage() {
        return discountBrlPercentage;
    }

    public void setDiscountBrlPercentage(int discountBrlPercentage) {
        this.discountBrlPercentage = discountBrlPercentage;
    }

    public int getMaxInstallments() {
        return maxInstallments;
    }

    public void setMaxInstallments(int maxInstallments) {
        this.maxInstallments = maxInstallments;
    }

    public int getSizeTypeId() {
        return sizeTypeId;
    }

    public void setSizeTypeId(int sizeTypeId) {
        this.sizeTypeId = sizeTypeId;
    }

    public String getPrincipalImage() {
        return principalImage;
    }

    public void setPrincipalImage(String principalImage) {
        this.principalImage = principalImage;
    }

    public List<String> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<String> additionalImages) {
        this.additionalImages = additionalImages;
    }
}
