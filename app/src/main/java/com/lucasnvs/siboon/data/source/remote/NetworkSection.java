package com.lucasnvs.siboon.data.source.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkSection {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;

    @SerializedName("featured_items")
    private List<FeaturedItemResponse> featuredItems;

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

    public List<FeaturedItemResponse> getFeaturedItems() {
        return featuredItems;
    }

    public void setFeaturedItems(List<FeaturedItemResponse> featuredItems) {
        this.featuredItems = featuredItems;
    }

    public static class FeaturedItemResponse {
        @SerializedName("product_id")
        private Long productId;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }
    }
}
