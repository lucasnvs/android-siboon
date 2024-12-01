package com.lucasnvs.siboon.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class NetworkUser {
    private String name;
    private String email;
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
