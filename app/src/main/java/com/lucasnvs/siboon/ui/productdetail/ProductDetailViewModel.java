package com.lucasnvs.siboon.ui.productdetail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.model.Product;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ProductRepository productRepository;
    public final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    public final MutableLiveData<Product> product = new MutableLiveData<>();
    public final MutableLiveData<Boolean> productAddedToCart = new MutableLiveData<>();

    public ProductDetailViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void loadProduct(Long id) {
        compositeDisposable.add(
                productRepository.fetchProductById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                product -> this.product.postValue(product),
                                throwable -> errorLiveData.postValue(throwable.getMessage())
                        )
        );
    }

    public void addToCart() {
        Log.d("ProductDetailViewModel", "Product Id:" + Objects.requireNonNull(product.getValue()).getId());
        compositeDisposable.add(
                productRepository.upsertCartProduct(product.getValue().getId(), 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    productAddedToCart.setValue(true);
                                },
                                throwable -> errorLiveData.postValue("Erro ao adicionar produto ao carrinho.")
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
