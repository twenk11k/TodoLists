package com.twenk11k.todolists.utils;


import com.twenk11k.todolists.roomdb.todolist.table.TodoItem;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<TodoItem> tempTodoItems(){
        String letters = "fax";
        List<TodoItem> todoItemList = new ArrayList<>();
        for(int i=0; i<3; i++){
            TodoItem todoItem = new TodoItem();
            todoItem.setName(String.valueOf(letters.charAt(i)));
            todoItemList.add(todoItem);
        }
        return todoItemList;
    }
}
