package com.example.kenweezy.mytablayouts;

/**
 * Created by KENWEEZY on 2017-03-10.
 */

public class Mydata {

    private String msgbody;
    private String date;
    private String read;
    private boolean isSelected;
    private int vcount;

    public Mydata(boolean isSelected,String mbdy,String date,String read,int vcount){

        this.msgbody=mbdy;
        this.date=date;
        this.read=read;
        this.isSelected=isSelected;
        this.vcount=vcount;
    }

    public int getVcount() {
        return vcount;
    }

    public void setVcount(int vcount) {
        this.vcount = vcount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setMsgbody(String msgbdy){

        this.msgbody=msgbdy;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setRead(String read){
        this.read=read;
    }

    public String getDate() {
        return date;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public String getRead() {
        return read;
    }
}
