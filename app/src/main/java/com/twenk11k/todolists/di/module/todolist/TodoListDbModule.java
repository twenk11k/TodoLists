package com.twenk11k.todolists.di.module.todolist;


import android.content.Context;
import com.twenk11k.todolists.roomdb.todolist.TodoDao;
import com.twenk11k.todolists.roomdb.todolist.TodoListDb;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;


@Module
public class TodoListDbModule {

    @Provides
    @Singleton
    public TodoListDb provideTodoListDb(Context context){
        return TodoListDb.getDatabase(context);
    }
    @Provides
    @Singleton
    public TodoDao provideTodoDao(TodoListDb todoListDb){
        return todoListDb.todoListDao();
    }
}
