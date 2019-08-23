package com.twenk11k.todolists.roomdb.todolist;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.twenk11k.todolists.roomdb.todolist.table.TodoItem;
import com.twenk11k.todolists.roomdb.todolist.table.TodoList;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(TodoList data);

    @Delete
    Completable delete(TodoList... data);

    @Query("DELETE FROM TodoItemTable WHERE todoListId = :todoListId AND todoListEmail = :todoListEmail")
    Completable deleteItemsByTodoListIdAndEmail(int todoListId, String todoListEmail);

    @Query("SELECT * FROM TodoListTable")
    Single<List<TodoList>> getTodoList();

    @Query("SELECT * FROM TodoListTable WHERE email = :email")
    Flowable<List<TodoList>> findToDoListsForUser(final String email);

    @Query("SELECT * FROM TodoListTable WHERE email = :email")
    Flowable<List<TodoList>> loadUserWithTodoList(String email);

    @Query("SELECT * FROM TodoItemTable WHERE todoListId = :todoListId AND todoListEmail = :todoListEmail")
    Flowable<List<TodoItem>> loadTodoItems(int todoListId, String todoListEmail);

    @Insert
    Completable insert(TodoItem data);

    @Delete
    Completable delete(TodoItem... data);

    @Update
    Completable update(TodoItem... data);

}
