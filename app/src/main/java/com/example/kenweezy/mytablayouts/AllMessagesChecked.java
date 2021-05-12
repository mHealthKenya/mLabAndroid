package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by kennedy on 9/26/17.
 */

public class AllMessagesChecked extends SugarRecord {

    String allchecked;

    public AllMessagesChecked(){}

    public AllMessagesChecked(String allchecked) {
        this.allchecked = allchecked;
    }

    public String getAllchecked() {
        return allchecked;
    }

    public void setAllchecked(String allchecked) {
        this.allchecked = allchecked;
    }
}
