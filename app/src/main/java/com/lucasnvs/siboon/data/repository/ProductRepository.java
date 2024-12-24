package com.lucasnvs.siboon.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.rxjava3.EmptyResultSetException;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
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
                .doOnComplete(() -> Log.e("ProductRepository", "Produtos recarregados com sucesso!"))
                .doOnError(throwable -> Log.e("ProductRepository", "Erro: Ao recarregar produtos.", throwable));
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

    public Completable deleteById(Long id) {
        return Completable.fromAction(() -> cartDAO.deleteCartByProductId(id))
                .doOnComplete(() -> Log.d("ProductRepository", "Produto deletado com sucesso: ID = " + id))
                .doOnError(throwable -> Log.e("ProductRepository", "Erro ao deletar produto: ID = " + id, throwable));
    }

    public Single<LocalCart> upsertCartProduct(Long productId, int newQuantity) {
        return refresh()
                .andThen(cartDAO.getCartByProductId(productId)
                        .flatMap(existingLocalCart -> {
                            existingLocalCart.setQuantity(existingLocalCart.getQuantity() + newQuantity);
                            cartDAO.updateCart(existingLocalCart);
                            return Maybe.just(existingLocalCart);
                        })
                        .switchIfEmpty(Maybe.defer(() -> {
                            LocalCart newLocalCart = new LocalCart(productId, newQuantity);
                            cartDAO.insertCart(newLocalCart);
                            return Maybe.just(newLocalCart);
                        })))
                .toSingle()
                .doOnError(throwable -> Log.e("ProductRepository", "Erro: Ao atualizar carrinho", throwable));
    }

    public Single<Integer> decreaseCartProduct(Long productId, int decreaseQuantity) {
        return cartDAO.getCartByProductId(productId)
                .flatMap(existingLocalCart -> {
                    int newQuantity = existingLocalCart.getQuantity() - decreaseQuantity;
                    if (newQuantity <= 0) {
                        cartDAO.deleteCart(existingLocalCart);
                        return Maybe.just(0);
                    } else {
                        existingLocalCart.setQuantity(newQuantity);
                        cartDAO.updateCart(existingLocalCart);
                        return Maybe.just(newQuantity);
                    }
                })
                .switchIfEmpty(Maybe.just(0))
                .toSingle()
                .doOnError(throwable -> Log.e("ProductRepository", "Erro: Ao reduzir produto no carrinho", throwable));
    }

    public Flowable<List<Cart>> getCartProducts() {
        return cartDAO.getAllCart()
                .flatMap(localCarts ->
                        Flowable.fromIterable(localCarts)
                                .flatMap(localCart ->
                                        productDAO.getProductById(localCart.getProductId())
                                                .toFlowable()
                                                .map(product -> new Cart(
                                                        productMapper.toModel(product),
                                                        localCart.getQuantity(),
                                                        localCart.getCreatedAt()
                                                ))
                                )
                                .toList()
                                .toFlowable()
                )
                .doOnComplete(() -> Log.d("ProductRepository", "Retorno com sucesso!"))
                .doOnError(throwable -> Log.d("ProductRepository", "Erro ao pegar itens do carrinho!", throwable));
    }
}
