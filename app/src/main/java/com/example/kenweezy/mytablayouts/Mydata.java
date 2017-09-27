package com.example.kenweezy.mytablayouts;

import java.util.Comparator;

/**
 * Created by KENWEEZY on 2017-03-10.
 */

public class Mydata {

    private String msgbody;
    private String date;
    private String read;
    private boolean isSelected;
    private int vcount;

    public Mydata(){

    }

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


    public static Comparator<Mydata> VlcountComparator = new Comparator<Mydata>() {

        @Override
        public int compare(Mydata m1, Mydata m2) {

            int val1=m1.getVcount();
            int val2=m2.getVcount();

            if(val1>val2){

                return -1;
            }

            else if(val1<val2){

                return 1;
            }
            return 0;
        }
    };

//        public int compare(Student s1, Student s2) {
//            String StudentName1 = s1.getStudentname().toUpperCase();
//            String StudentName2 = s2.getStudentname().toUpperCase();
//
//            //ascending order
//            return StudentName1.compareTo(StudentName2);
//
//            //descending order
//            //return StudentName2.compareTo(StudentName1);
//        }};
}
