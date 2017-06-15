package com.example.kenweezy.mytablayouts;

/**
 * Created by kennedy on 5/31/17.
 */

public class MymodelClicked {
    String address;
    String body;

    public MymodelClicked(){


    }

    public MymodelClicked(String addr, String bdy){

        this.address=addr;
        this.body=bdy;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

