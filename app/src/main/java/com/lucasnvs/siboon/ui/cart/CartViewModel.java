package com.lucasnvs.siboon.ui.cart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.data.source.local.LocalCart;
import com.lucasnvs.siboon.mapper.ProductMapper;
import com.lucasnvs.siboon.model.Cart;
import com.lucasnvs.siboon.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ProductRepository productRepository;
    public final MutableLiveData<List<Product>> cart = new MutableLiveData<>();

    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public CartViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void loadCart() {
        compositeDisposable.add(
                productRepository.getCartProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cart -> {
                                    Log.d("CartViewModel", cart.toString());
                                    Log.d("CartViewModel", "Carregando na tela...");
                                    this.cart.postValue(cart);
                                },
                                throwable -> {
                                    Log.e("CartViewModel", "Erro ao carregar carrinho", throwable);
                                    errorLiveData.postValue(throwable.getMessage());
                                    throw throwable;
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}