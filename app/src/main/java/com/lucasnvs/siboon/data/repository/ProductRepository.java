package com.lucasnvs.siboon.data.repository;

import android.content.Context;
import android.util.Log;

import com.lucasnvs.siboon.data.source.local.LocalProduct;
import com.lucasnvs.siboon.data.source.local.ProductDAO;
import com.lucasnvs.siboon.data.source.local.SiboonDatabase;
import com.lucasnvs.siboon.data.source.remote.RetrofitClient;
import com.lucasnvs.siboon.data.source.remote.SiboonApi;
import com.lucasnvs.siboon.mapper.ProductMapper;
import com.lucasnvs.siboon.model.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class ProductRepository {

    private final ProductDAO productDAO;
    private final ProductMapper productMapper = new ProductMapper();
    private final SiboonApi api;

    public ProductRepository(Context context) {
        this.productDAO = SiboonDatabase.getInstance(context).productDAO();
        this.api = RetrofitClient.getApi(context);
    }

    public Flowable<List<LocalProduct>> getLocalProducts() {
        return productDAO.getAllProducts();
    }

    public Completable insertLocalProduct(LocalProduct product) {
        return productDAO.insert(product);
    }

    public Completable updateLocalProducts(List<LocalProduct> products) {
        return productDAO.deleteAll()
                .andThen(productDAO.insertAll(products));
    }

    public Single<List<Product>> fetchProducts() {
        return api.getProducts()
                .map(response -> {
                    if (!response.isError() || response.getData() != null) {
                        return productMapper.toModel(response.getData());
                    } else {
                        throw new Exception("Erro: Resposta inválida da API.");
                    }
                });
    }

    public Single<Product> fetchProductById(Long id) {
        return api.getProductById(id)
                .map(response -> {
                    if (!response.isError() || response.getData() != null) {
                        return productMapper.toModel(response.getData());
                    } else {
                        throw new Exception("Erro: Resposta inválida da API.");
                    }
                });
    }
}
