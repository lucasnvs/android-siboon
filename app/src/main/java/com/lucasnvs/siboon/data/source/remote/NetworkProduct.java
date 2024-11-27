package com.lucasnvs.siboon.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class NetworkProduct {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("color")
    private String color;

    @SerializedName("price_brl")
    private Double priceBrl;

    @SerializedName("formated_price_brl")
    private String formattedPriceBrl;

    @SerializedName("discount_brl_percentage")
    private int discountBrlPercentage;

    @SerializedName("formated_price_brl_with_discount")
    private String formattedPriceBrlWithDiscount;

    @SerializedName("max_installments")
    private int maxInstallments;

    @SerializedName("size_type_id")
    private int sizeTypeId;

    @SerializedName("principal_img")
    private String principalImg;

    public NetworkProduct(int id, String name, String description, String color, Double priceBrl,
                          String formattedPriceBrl, int discountBrlPercentage, String formattedPriceBrlWithDiscount,
                          int maxInstallments, int sizeTypeId, String principalImg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.priceBrl = priceBrl;
        this.formattedPriceBrl = formattedPriceBrl;
        this.discountBrlPercentage = discountBrlPercentage;
        this.formattedPriceBrlWithDiscount = formattedPriceBrlWithDiscount;
        this.maxInstallments = maxInstallments;
        this.sizeTypeId = sizeTypeId;
        this.principalImg = principalImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getFormattedPriceBrl() {
        return formattedPriceBrl;
    }

    public void setFormattedPriceBrl(String formattedPriceBrl) {
        this.formattedPriceBrl = formattedPriceBrl;
    }

    public int getDiscountBrlPercentage() {
        return discountBrlPercentage;
    }

    public void setDiscountBrlPercentage(int discountBrlPercentage) {
        this.discountBrlPercentage = discountBrlPercentage;
    }

    public String getFormattedPriceBrlWithDiscount() {
        return formattedPriceBrlWithDiscount;
    }

    public void setFormattedPriceBrlWithDiscount(String formattedPriceBrlWithDiscount) {
        this.formattedPriceBrlWithDiscount = formattedPriceBrlWithDiscount;
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

    public String getPrincipalImg() {
        return principalImg;
    }

    public void setPrincipalImg(String principalImg) {
        this.principalImg = principalImg;
    }
}
