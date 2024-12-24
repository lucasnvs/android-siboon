package com.lucasnvs.siboon.data.source.remote;

public class SignupResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;

    public SignupResponse(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}