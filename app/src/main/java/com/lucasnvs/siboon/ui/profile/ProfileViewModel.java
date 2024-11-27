package com.lucasnvs.siboon.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();

    public ProfileViewModel() {
        userName.setValue("Usuário Desconhecido");
        userEmail.setValue("email@exemplo.com");
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public void setUserName(String name) {
        userName.setValue(name);
    }

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }
}
