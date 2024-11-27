package com.lucasnvs.siboon.data.source.remote;

import com.lucasnvs.siboon.model.Section;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SiboonApi {
    String BASE_URL = "http://10.0.2.2/siboon/api/";

    @GET("produtos")
    Single<ApiResponse<List<NetworkProduct>>> getProducts();

    @GET("produtos/{id}")
    Single<ApiResponse<NetworkProduct>> getProductById(@Path("id") Long id);

    @GET("website/sections")
    Single<ApiResponse<List<NetworkSection>>> getSections();
}