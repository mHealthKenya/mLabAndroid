package com.example.kenweezy.mytablayouts.fragmentTagTable;

import com.orm.SugarRecord;

/**
 * Created by root on 4/18/18.
 */

public class fragmenttags extends SugarRecord {

    String fragname;
    String tagname;

    public fragmenttags(String fragname, String tagname) {
        this.fragname = fragname;
        this.tagname = tagname;
    }

    public fragmenttags() {
    }

    public String getFragname() {
        return fragname;
    }

    public void setFragname(String fragname) {
        this.fragname = fragname;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}
