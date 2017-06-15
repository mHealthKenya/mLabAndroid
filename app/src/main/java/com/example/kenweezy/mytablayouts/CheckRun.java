package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by KENWEEZY on 2017-03-21.
 */

public class CheckRun extends SugarRecord {
    public String firstRun;

    public CheckRun(){}

    public CheckRun(String fr){
        this.firstRun=fr;
    }

    public void setFirstRun(String firstRun) {
        this.firstRun = firstRun;
    }

    public String getFirstRun() {
        return firstRun;
    }
}
