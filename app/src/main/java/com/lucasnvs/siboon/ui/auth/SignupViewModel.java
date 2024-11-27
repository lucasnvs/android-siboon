package com.lucasnvs.siboon.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SignupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Login");
    }

    public LiveData<String> getText() {
        return mText;
    }
}