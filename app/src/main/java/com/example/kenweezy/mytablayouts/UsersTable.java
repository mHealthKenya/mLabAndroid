package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by KENWEEZY on 2017-03-21.
 */

public class UsersTable extends SugarRecord {

    public String username;
    public String password;

    public UsersTable(){}

    public UsersTable(String uname,String pwd){
        this.username=uname;
        this.password=pwd;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
