package com.twenk11k.todolists.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.twenk11k.todolists.repository.UserRepository;
import com.twenk11k.todolists.roomdb.user.User;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends AndroidViewModel {

    private CompositeDisposable compositeDisposable;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<User> autoLoginInfoLiveData;

    @Inject
    public UserRepository userRepository;

    @Inject
    public UserViewModel(Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        userLiveData = new MutableLiveData<>();
        autoLoginInfoLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }


    public MutableLiveData<User> getAutoLoginInfoLiveData() {
        return autoLoginInfoLiveData;
    }

    public void insert(User data) {

        Disposable insertUser = userRepository.insertData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(insertUser);

    }

    public void updateAllAutoLoginToZero() {

        Disposable updateAllAutoLoginToZero = userRepository.updateAllAutoLoginToZero()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdateAllAutoLoginToZeroComplete,this::onUpdateAllAutoLoginToZeroError);

        compositeDisposable.add(updateAllAutoLoginToZero);

    }

    private void onUpdateAllAutoLoginToZeroError(Throwable t) {
        Log.d(getClass().getName(),"onUpdateAllAutoLoginToZeroError: "+ t.getMessage());
    }

    private void onUpdateAllAutoLoginToZeroComplete() {
        Log.d(getClass().getName(),"onUpdateAllAutoLoginToZeroComplete: "+"complete");
    }


    public void checkIfUserExist(User user) {

        Disposable userListDisposable = userRepository.findUserByEmailAndPassword(user.getEmail(),user.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadUserFetched, this::onLoadUserError);

        compositeDisposable.add(userListDisposable);

    }

    private void onLoadUserFetched(User user) {
        userLiveData.setValue(user);
    }

    private void onLoadUserError(Throwable t) {
        Log.d(getClass().getName(), t.getMessage());
        userLiveData.setValue(null);
    }

    public void updateAutoLogin(int tid, int autoLogin) {

        Disposable updateAutoLogin = userRepository.updateAutoLogin(tid, autoLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdateAutoLoginComplete,this::onUpdateAutoLoginError);

        compositeDisposable.add(updateAutoLogin);
    }

    private void onUpdateAutoLoginError(Throwable t) {
        Log.d(getClass().getName(),"onUpdateAutoLoginError: "+t.getMessage());
    }

    private void onUpdateAutoLoginComplete() {
        Log.d(getClass().getName(),"onUpdateAutoLoginComplete: complete");
    }

    public void loadAutoLoginInfo(){

        Disposable autoLoginDisposable = userRepository.getAutoLoginInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadAutoLoginInfoFetched, this::onLoadAutoLoginInfoError);

        compositeDisposable.add(autoLoginDisposable);

    }

    private void onLoadAutoLoginInfoError(Throwable t) {
        Log.d(getClass().getName(),t.getMessage());
        autoLoginInfoLiveData.setValue(null);
    }

    private void onLoadAutoLoginInfoFetched(User user) {
        autoLoginInfoLiveData.setValue(user);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }

}
