package com.example.lenovo.auicideball.backstage;

import org.litepal.crud.DataSupport;

public class Remember_User extends DataSupport{
    String user_name;
    String password;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
