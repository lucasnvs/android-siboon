package com.lucasnvs.siboon.data.source.remote;

public class SignupResponse {
    private Long id;
    private String name;
    private String email;
    private String role;

    public SignupResponse(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}