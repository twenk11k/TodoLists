package com.twenk11k.todolists.roomdb.todolist.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "TodoListTable")
public class TodoList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "createDate")
    private String createDate;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public String getCreateDate() {
        return createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
