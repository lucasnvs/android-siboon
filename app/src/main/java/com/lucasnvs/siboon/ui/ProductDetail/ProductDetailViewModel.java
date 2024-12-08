package com.lucasnvs.siboon.ui.ProductDetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.model.Product;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ProductRepository productRepository;
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MutableLiveData<Product> product = new MutableLiveData<>();

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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
