package com.twenk11k.todolists.roomdb.user;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class}, version = 13, exportSchema = false)
public abstract class UserDb extends RoomDatabase {

    public static final String DB_NAME = "USER_DATABASE";

    public abstract UserDao userDao();

    private static UserDb INSTANCE;


    public static UserDb getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (UserDb.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context, UserDb.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                }

            }

        }

        return INSTANCE;

    }
}
