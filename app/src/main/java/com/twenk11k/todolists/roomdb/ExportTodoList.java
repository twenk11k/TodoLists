package com.twenk11k.todolists.roomdb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.twenk11k.todolists.roomdb.todolist.TodoItem;
import java.util.ArrayList;
import java.util.List;


public class ExportTodoList {

    @SerializedName("ToDos")
    @Expose
    private List<TodoItem> todos = new ArrayList<>();

    public List<TodoItem> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoItem> todos) {
        this.todos = todos;
    }

}
