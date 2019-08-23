package com.twenk11k.todolists.repository;


import com.twenk11k.todolists.roomdb.user.User;
import com.twenk11k.todolists.roomdb.user.UserDao;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;


@Singleton
public class UserRepository {

    private final UserDao userDao;

    @Inject
    UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public Single<List<User>> getUserList() {
        return userDao.getUserList();
    }

    public Flowable<User> getAutoLoginInfo() {
        return userDao.getAutoLoginInfo();
    }


    public Completable insertData(User data) {
        return userDao.insert(data);
    }

    public Completable updateAllAutoLoginToZero() {
        return userDao.updateAllAutoLoginToZero();
    }

    public Completable updateAutoLogin(int tid, int autoLogin) {
        return userDao.updateAutoLogin(tid, autoLogin);
    }

    public Single<User> findUserByEmailAndPassword(String email,String password){
        return userDao.findUserByEmailAndPassword(email,password);
    }



}

