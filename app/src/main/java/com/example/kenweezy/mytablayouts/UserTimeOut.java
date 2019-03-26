package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by KENWEEZY on 2017-03-29.
 */

public class UserTimeOut extends SugarRecord {

    String lasttime;

    public UserTimeOut() {
    }

    public UserTimeOut(String lt) {

        this.lasttime = lt;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }
}
