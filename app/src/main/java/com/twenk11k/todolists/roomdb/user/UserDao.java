package com.twenk11k.todolists.roomdb.user;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(final User data);

    @Query("UPDATE UserTable SET autologin = 0 WHERE autologin = 1")
    Completable updateAllAutoLoginToZero();

    @Query("UPDATE UserTable SET autologin = :autoLogin WHERE id = :tid")
    Completable updateAutoLogin(int tid, int autoLogin);

    @Query("SELECT * FROM UserTable")
    Single<List<User>> getUserList();

    @Query("SELECT * FROM UserTable WHERE autologin = 1 LIMIT 1")
    Flowable<User> getAutoLoginInfo();

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password LIMIT 1")
    Single<User> findUserByEmailAndPassword(String email, String password);


    // Test
    @Query("SELECT * FROM UserTable WHERE email = :email LIMIT 1")
    User findUserByEmailTest(String email);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTest(final User data);

    @Delete()
    void deleteTest(final User data);





}
