package com.twenk11k.todolists.listener;


import com.twenk11k.todolists.roomdb.todolist.table.TodoItem;


public interface OnToDoAdapterClick {

    void onAdapterClickDelete(TodoItem todoItem);
    void onAdapterClickUpdate(TodoItem todoItem);

}
