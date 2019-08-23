package com.twenk11k.todolists.roomdb.todolist;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "TodoItemTable")
public class TodoItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "todoListId")
    private int todoListId;

    @ColumnInfo(name = "todoListEmail")
    private String todoListEmail;


    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "createDate")
    private String createDate;

    @ColumnInfo(name = "deadline")
    private String deadline;

    @ColumnInfo(name = "status")
    private int status = 0;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(int todoListId) {
        this.todoListId = todoListId;
    }

    public void setTodoListEmail(String todoListEmail) {
        this.todoListEmail = todoListEmail;
    }

    public String getTodoListEmail() {
        return todoListEmail;
    }
}
