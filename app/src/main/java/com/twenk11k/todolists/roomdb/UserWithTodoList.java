package com.twenk11k.todolists.roomdb;


import androidx.room.Embedded;
import androidx.room.Relation;
import com.twenk11k.todolists.roomdb.todolist.TodoList;
import com.twenk11k.todolists.roomdb.user.User;
import java.util.List;


public class UserWithTodoList {

    @Embedded
    public User user;

    @Relation(parentColumn = "email", entityColumn = "email",entity = TodoList.class)
    public List<TodoList> todoLists;

}
