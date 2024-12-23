package com.lucasnvs.siboon.data.source.remote;


import com.lucasnvs.siboon.utils.Constants;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SiboonApi {
    String BASE_URL = Constants.BASE_URL + "api/";

    @GET("produtos")
    Single<ApiResponse<List<NetworkProduct>>> getProducts();

    @GET("produtos/{id}")
    Single<ApiResponse<NetworkProduct>> getProductById(@Path("id") Long id);

    @GET("website/sections")
    Single<ApiResponse<List<NetworkSection>>> getSections();

    @POST("usuarios/login")
    Single<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("usuarios")
    Single<ApiResponse<SignupResponse>> signup(@Body SignupRequest request);

    @GET("usuarios/me")
    Single<ApiResponse<NetworkUser>> me();
}