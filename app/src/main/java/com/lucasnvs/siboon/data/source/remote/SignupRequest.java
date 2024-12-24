package com.lucasnvs.siboon.data.source.remote;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("first_name")
    private final String name;
    @SerializedName("last_name")
    private final String lastName;
    private final String email;
    private final String password;

    public SignupRequest(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
