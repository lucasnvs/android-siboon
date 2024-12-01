package com.lucasnvs.siboon.data.repository;

import android.content.Context;
import android.util.Log;

import com.lucasnvs.siboon.data.source.remote.ApiResponse;
import com.lucasnvs.siboon.data.source.remote.LoginRequest;
import com.lucasnvs.siboon.data.source.remote.LoginResponse;
import com.lucasnvs.siboon.data.source.remote.RetrofitClient;
import com.lucasnvs.siboon.data.source.remote.SiboonApi;

import io.reactivex.rxjava3.core.Single;

public class UserRepository {
    private final SiboonApi api;

    public UserRepository(Context context) {
        this.api = RetrofitClient.getApi(context);
    }

    public Single<LoginResponse> login(String email, String password) {
        return api.login(new LoginRequest(email, password))
                .map(response -> {
                    if (!response.isError() || response.getData() != null) {
                        return response.getData();
                    } else {
                        throw new Exception("Erro: Resposta inválida da API.");
                    }
                });
    }
}
