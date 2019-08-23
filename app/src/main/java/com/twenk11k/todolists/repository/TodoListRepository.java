package com.twenk11k.todolists.repository;


import com.twenk11k.todolists.roomdb.todolist.TodoDao;
import com.twenk11k.todolists.roomdb.todolist.TodoItem;
import com.twenk11k.todolists.roomdb.todolist.TodoList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;


@Singleton
public class TodoListRepository {

    private TodoDao todoDao;

    @Inject
    public TodoListRepository(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    public Completable insertData(TodoList data) {
        return todoDao.insert(data);
    }

    public Completable insertData(TodoItem data) {

        return todoDao.insert(data);

    }

    public Completable deleteData(TodoItem data) {
        return todoDao.delete(data);
    }

    public Completable deleteTodoList(TodoList data) {
        return todoDao.delete(data);
    }

    public Completable deleteItemsByTodoListIdAndEmail(int id,String todoListEmail) {
        return todoDao.deleteItemsByTodoListIdAndEmail(id,todoListEmail);
    }

    public Completable updateData(TodoItem data) {
        return todoDao.update(data);
    }

    public Flowable<List<TodoList>> loadUserWithTodoList(String email) {
        return todoDao.loadUserWithTodoList(email);
    }

    public Single<List<TodoItem>> loadTodoItems(int todoListId, String todoListEmail) {
        return todoDao.loadTodoItems(todoListId,todoListEmail);
    }

}
