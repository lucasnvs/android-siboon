package com.lucasnvs.siboon.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lucasnvs.siboon.data.repository.SectionRepository;
import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.model.Section;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<String> mText;

    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Section>> sections = new MutableLiveData<>();

    public MutableLiveData<List<Product>> products = new MutableLiveData<>();

    private final SectionRepository sectionRepository;

    public HomeViewModel(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;

        mText = new MutableLiveData<>();
        mText.setValue("Seções de produtos");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void loadSections() {
        compositeDisposable.add(
                sectionRepository.fetchSectionsWithProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> this.sections.postValue(response),
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
