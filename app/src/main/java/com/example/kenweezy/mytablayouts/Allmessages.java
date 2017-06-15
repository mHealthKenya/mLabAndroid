package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by kennedy on 5/31/17.
 */

public class Allmessages extends SugarRecord{

    String bdy;
    String addr;

    public Allmessages(){}

    public Allmessages(String addr,String bdy){
        this.addr=addr;
        this.bdy=bdy;


    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setBdy(String bdy) {
        this.bdy = bdy;
    }

    public String getAddr() {
        return addr;
    }

    public String getBdy() {
        return bdy;
    }
}
