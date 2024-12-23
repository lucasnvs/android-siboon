package com.lucasnvs.siboon.data.source.remote;

import android.content.Context;

import com.lucasnvs.siboon.model.SessionManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static SiboonApi siboonApi;

    private RetrofitClient(Context context) {
        SessionManager sessionManager = new SessionManager(context);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(sessionManager))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SiboonApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        siboonApi = retrofit.create(SiboonApi.class);
    }

    public static SiboonApi getApi(Context context) {
        if (siboonApi == null) {
            new RetrofitClient(context);
        }
        return siboonApi;
    }
}