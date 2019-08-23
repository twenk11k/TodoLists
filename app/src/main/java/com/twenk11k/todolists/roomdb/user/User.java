package com.twenk11k.todolists.roomdb.user;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.HashSet;
import java.util.Set;
import io.reactivex.annotations.NonNull;


@Entity(tableName = "UserTable",indices = @Index(value = {"email"},unique = true))
public class User {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "surname")
    private String surname;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "autologin")
    private int autoLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setAutoLogin(int autoLogin) {
        this.autoLogin = autoLogin;
    }

    public int getAutoLogin() {
        return autoLogin;
    }

    public Set<String> getDataSet() {
        Set<String> setStr = new HashSet<>();
        setStr.add(name);
        setStr.add(surname);
        setStr.add(email);
        setStr.add(password);
        return setStr;
    }


}
