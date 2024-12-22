package com.lucasnvs.siboon.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lucasnvs.siboon.data.repository.UserRepository;
import com.lucasnvs.siboon.model.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final UserRepository userRepository;

    private final MutableLiveData<User> user = new MutableLiveData<>();

    public final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;

        loadUserData();
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void loadUserData() {
        compositeDisposable.add(
                userRepository.me()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this.user::postValue,
                                throwable -> errorLiveData.postValue(throwable.getMessage())
                        )
        );
    }
}
