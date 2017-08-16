package com.example.kenweezy.mytablayouts;

import com.orm.SugarRecord;

/**
 * Created by KENWEEZY on 2017-03-02.
 */

public class Messages extends SugarRecord {
    public String mAddress;
    public String mBody;
    public String mTimeStamp;
    public String read;
    public String dateRead;
    public String chkd;
    public String viralCount;

    public Messages(){

    }

    public Messages(String chkd,String mAddress,String mBody,String mTimeStamp,String Read,String dateRead,String vcount){

        this.mAddress=mAddress;
        this.mBody=mBody;
        this.mTimeStamp=mTimeStamp;
        this.read=Read;
        this.dateRead=dateRead;
        this.chkd=chkd;
        this.viralCount=vcount;
    }

    public String getViralCount() {
        return viralCount;
    }

    public void setViralCount(String viralCount) {
        this.viralCount = viralCount;
    }

    public String getChkd() {
        return chkd;
    }

    public void setChkd(String chkd) {
        this.chkd = chkd;
    }

    public String getmAddress(){
        return mAddress;
    }

    public String getmBody(){

        return mBody;
    }
    public String getRead(){

        return read;
    }

    public String getmTimeStamp(){

        return mTimeStamp;
    }

    public void setDateRead(String dateRead) {
        this.dateRead = dateRead;
    }

    public String getDateRead() {
        return dateRead;
    }

    public void setmAddress(String addr){
        this.mAddress=addr;
    }

    public void setmBody(String bdy){
        this.mBody=bdy;
    }
    public void setRead(String read){
        this.read=read;
    }

    public void setmTimeStamp(String tstamp){
        this.mTimeStamp=tstamp;
    }


}
