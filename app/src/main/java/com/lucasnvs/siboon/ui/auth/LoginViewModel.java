package com.lucasnvs.siboon.ui.auth;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lucasnvs.siboon.data.repository.UserRepository;
import com.lucasnvs.siboon.data.source.remote.LoginResponse;
import com.lucasnvs.siboon.model.SessionManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserRepository userRepository;
    private SessionManager sessionManager;

    public final MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
    public final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void setContext(Context context) {
        this.userRepository = new UserRepository(context);
        this.sessionManager = new SessionManager(context);
    }

    public void login(String email, String password) {
        compositeDisposable.add(
                userRepository.login(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    sessionManager.saveToken(response.getToken());
                                    loginResponse.postValue(response);
                                },
                                throwable -> {
                                    Log.e("LoginError", "Erro ao realizar login", throwable);
                                    errorLiveData.postValue(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
