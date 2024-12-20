package com.lucasnvs.siboon.data.repository;

import android.content.Context;
import android.util.Log;

import com.lucasnvs.siboon.data.source.local.LocalCart;
import com.lucasnvs.siboon.data.source.local.CartDAO;
import com.lucasnvs.siboon.data.source.local.LocalProduct;
import com.lucasnvs.siboon.data.source.local.ProductDAO;
import com.lucasnvs.siboon.data.source.local.SiboonDatabase;
import com.lucasnvs.siboon.data.source.remote.RetrofitClient;
import com.lucasnvs.siboon.data.source.remote.SiboonApi;
import com.lucasnvs.siboon.mapper.ProductMapper;
import com.lucasnvs.siboon.model.Cart;
import com.lucasnvs.siboon.model.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class ProductRepository {

    private final ProductDAO productDAO;
    private final CartDAO cartDAO;
    private final ProductMapper productMapper = new ProductMapper();
    private final SiboonApi api;

    public ProductRepository(Context context) {
        this.productDAO = SiboonDatabase.getInstance(context).productDAO();
        this.cartDAO = SiboonDatabase.getInstance(context).cartDAO();
        this.api = RetrofitClient.getApi(context);
    }

    public Flowable<List<LocalProduct>> getLocalProducts() {
        return productDAO.getAllProducts();
    }

    public Single<LocalProduct> getLocalProduct(Long id) { return productDAO.getProductById(id); }
    public Completable insert(LocalProduct product) {
        return productDAO.insert(product);
    }

    public Completable updateLocalProducts(List<LocalProduct> products) {
        return productDAO.deleteAll()
                .andThen(productDAO.insertAll(products));
    }

    public Completable refresh() {
        return fetchProducts()
                .map(productMapper::toLocal)
                .flatMapCompletable(this::updateLocalProducts)
                .doOnComplete(() -> {
                    Log.e("ProductRepository", "Produtos recarregados com sucesso!");
                })
                .doOnError(throwable -> {
                    Log.e("ProductRepository", "Erro: Ao recarregar produtos.", throwable);
                });
    }

    public Single<List<Product>> fetchProducts() {
        return api.getProducts()
                .map(response -> {
                    if (!response.isError() && response.getData() != null) {
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

    public Completable upsertCartProduct(Product product, int newQuantity) {
        return refresh()
                .andThen(Completable.fromCallable(() -> {
                    LocalCart existingLocalCart = cartDAO.getCartByProductId(product.getId());
                    if (existingLocalCart != null) {
                        existingLocalCart.setQuantity(existingLocalCart.getQuantity() + newQuantity);
                        cartDAO.updateCart(existingLocalCart);
                    } else {
                        LocalCart newLocalCart = new LocalCart(product.getId(), newQuantity);
                        cartDAO.insertCart(newLocalCart);
                    }
                    return Completable.complete();
                }))
                .doOnError(throwable -> {
                    Log.e("ProductRepository", "Erro: Ao atualizar carrinho", throwable);
                });
    }

    public Flowable<List<Product>> getCartProducts() {
        return cartDAO.getAllCart()
                .flatMap(localCarts -> Flowable.fromIterable(localCarts)
                        .flatMap(localCart -> productDAO.getProductById(localCart.getProductId()).toFlowable().map(productMapper::toModel))
                        .toList()
                        .toFlowable()
                )
                .doOnComplete( () -> {
                    Log.d("ProductRepository", "Retorno com sucesso!");
                })
                .onErrorResumeNext(throwable -> {
                    Log.d("ProductRepository", "Erro ao pegar items do carrinho!", throwable);
                    return Flowable.error(throwable);
                });
    }

}
