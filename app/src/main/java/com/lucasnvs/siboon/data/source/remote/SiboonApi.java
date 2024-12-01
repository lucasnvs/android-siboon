package com.lucasnvs.siboon.data.source.remote;


import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SiboonApi {
    String BASE_URL = "http://10.0.2.2/siboon/api/";

    @GET("produtos")
    Single<ApiResponse<List<NetworkProduct>>> getProducts();

    @GET("produtos/{id}")
    Single<ApiResponse<NetworkProduct>> getProductById(@Path("id") Long id);

    @GET("website/sections")
    Single<ApiResponse<List<NetworkSection>>> getSections();

    @POST("usuarios/login")
    Single<ApiResponse<LoginResponse>> login(@Body LoginRequest request);
}