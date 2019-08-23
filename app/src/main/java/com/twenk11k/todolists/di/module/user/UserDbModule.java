package com.twenk11k.todolists.di.module.user;


import android.content.Context;
import androidx.room.Room;
import com.twenk11k.todolists.roomdb.user.UserDao;
import com.twenk11k.todolists.roomdb.user.UserDb;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class UserDbModule {

    @Provides
    @Singleton
    public UserDb provideUserDb(Context context){
        return Room.databaseBuilder(
                context, UserDb.class, "USER_DATABASE")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public UserDao provideUserDao(UserDb userDb){
        return userDb.userDao();
    }


}
