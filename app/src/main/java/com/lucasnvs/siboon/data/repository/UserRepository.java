package com.lucasnvs.siboon.data.repository;

import android.content.Context;

import com.lucasnvs.siboon.data.source.remote.LoginRequest;
import com.lucasnvs.siboon.data.source.remote.LoginResponse;
import com.lucasnvs.siboon.data.source.remote.RetrofitClient;
import com.lucasnvs.siboon.data.source.remote.SiboonApi;
import com.lucasnvs.siboon.data.source.remote.SignupRequest;
import com.lucasnvs.siboon.data.source.remote.SignupResponse;
import com.lucasnvs.siboon.model.User;

import org.json.JSONObject;

import io.reactivex.rxjava3.core.Single;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class UserRepository {
    private final SiboonApi api;

    public UserRepository(Context context) {
        this.api = RetrofitClient.getApi(context);
    }

    private String extractHttpErrorResponseMessage(Throwable throwable) {
        String errorMessage = "Erro";

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ResponseBody errorBody = httpException.response().errorBody();

            if (errorBody != null) {
                try {
                    JSONObject jsonError = new JSONObject(errorBody.string());
                    errorMessage = jsonError.optString("message", "Erro desconhecido");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return errorMessage;
    }

    public Single<SignupResponse> signup(String name, String lastName, String email, String password) {
        return api.signup(new SignupRequest(name, lastName, email, password))
                .map(response -> {
                    if (response.isError() || response.getData() == null) {
                        throw new Exception("Erro: Resposta inválida da API.");
                    }
                    return response.getData();
                })
                .onErrorResumeNext(throwable -> Single.error(new Exception(extractHttpErrorResponseMessage(throwable), throwable)));
    }


    public Single<LoginResponse> login(String email, String password) {
        return api.login(new LoginRequest(email, password))
                .map(response -> {
                    if (response.isError() || response.getData() != null) {
                        return response.getData();
                    } else {
                        throw new Exception("Erro: Resposta inválida da API.");
                    }
                })
                .onErrorResumeNext(throwable -> Single.error(new Exception(extractHttpErrorResponseMessage(throwable), throwable)));
    }

    public Single<User> me() {
        return api.me()
                .map(response -> {
                    if (response.isError() || response.getData() != null) {
                        return new User(
                                response.getData().getName(),
                                response.getData().getEmail(),
                                response.getData().getImg()
                        );
                    } else {
                        throw new Exception("Erro: Resposta inválida da API.");
                    }
                })
                .onErrorResumeNext(throwable -> Single.error(new Exception(extractHttpErrorResponseMessage(throwable), throwable)));
    }
}
