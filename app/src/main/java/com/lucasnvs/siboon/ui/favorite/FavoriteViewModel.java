package com.lucasnvs.siboon.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavoriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Você ainda não adicionou nenhum produto... :(");
    }

    public LiveData<String> getText() {
        return mText;
    }
}