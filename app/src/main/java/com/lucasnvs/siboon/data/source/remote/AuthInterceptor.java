package com.lucasnvs.siboon.data.source.remote;

import androidx.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {

    private final String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build());
    }
}
