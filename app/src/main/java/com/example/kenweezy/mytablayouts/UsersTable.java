package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by KENWEEZY on 2017-03-21.
 */

public class UsersTable extends SugarRecord {

    public String username;
    public String password;
    public String securityqn;
    public String securityans;

    public UsersTable(){}

    public UsersTable(String uname,String pwd,String secqn,String secans){
        this.username=uname;
        this.password=pwd;
        this.securityqn=secqn;
        this.securityans=secans;
    }

    public String getSecurityqn() {
        return securityqn;
    }

    public void setSecurityqn(String securityqn) {
        this.securityqn = securityqn;
    }

    public String getSecurityans() {
        return securityans;
    }

    public void setSecurityans(String securityans) {
        this.securityans = securityans;
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
