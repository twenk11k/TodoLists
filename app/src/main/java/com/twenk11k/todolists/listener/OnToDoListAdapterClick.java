package com.twenk11k.todolists.listener;


import com.twenk11k.todolists.roomdb.todolist.table.TodoList;


public interface OnToDoListAdapterClick {

     void onAdapterClick(String name, int id, String email);
     void onAdapterClickDelete(TodoList todoList);
     void onExportListLocalClick(TodoList todoList);
     void onExportListEmailClick(TodoList todoList);

}
