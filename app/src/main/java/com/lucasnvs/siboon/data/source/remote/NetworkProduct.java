package com.lucasnvs.siboon.data.source.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkProduct {

    @SerializedName("id")
    private Long id;

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
    private String principalImage;

    @SerializedName("additional_imgs")
    private List<String> additionalImages;

    public NetworkProduct(Long id, String name, String description, String color, Double priceBrl, String formattedPriceBrl, int discountBrlPercentage, String formattedPriceBrlWithDiscount, int maxInstallments, int sizeTypeId, String principalImage, List<String> additionalImages) {
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
