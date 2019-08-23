package com.twenk11k.todolists.roomdb.todolist;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.twenk11k.todolists.roomdb.todolist.table.TodoItem;
import com.twenk11k.todolists.roomdb.todolist.table.TodoList;


@Database(entities = {TodoList.class, TodoItem.class}, version = 13, exportSchema = false)
public abstract class TodoListDb extends RoomDatabase {

    public static final String DB_NAME = "TODO_LIST_DATABASE";

    public abstract TodoDao todoListDao();

    private static TodoListDb INSTANCE;


    public static TodoListDb getDatabase(final Context context) {

        if(INSTANCE == null){
            synchronized (TodoListDb.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context, TodoListDb.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
