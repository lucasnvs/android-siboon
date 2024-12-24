package com.lucasnvs.siboon.data.source.remote;

import androidx.annotation.NonNull;

import com.lucasnvs.siboon.model.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final SessionManager sessionManager;

    public AuthInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String token = sessionManager.getToken();
        if (token != null) {
            return chain.proceed(chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build());
        }
        return chain.proceed(chain.request());
    }
}
