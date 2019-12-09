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
    public String messageId;
    public String patientid;

    public Messages(){

    }

    public Messages(String mAddress, String mBody, String mTimeStamp, String read, String dateRead, String chkd, String viralCount, String messageId, String patientid) {
        this.mAddress = mAddress;
        this.mBody = mBody;
        this.mTimeStamp = mTimeStamp;
        this.read = read;
        this.dateRead = dateRead;
        this.chkd = chkd;
        this.viralCount = viralCount;
        this.messageId = messageId;
        this.patientid = patientid;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmBody() {
        return mBody;
    }

    public void setmBody(String mBody) {
        this.mBody = mBody;
    }

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getDateRead() {
        return dateRead;
    }

    public void setDateRead(String dateRead) {
        this.dateRead = dateRead;
    }

    public String getChkd() {
        return chkd;
    }

    public void setChkd(String chkd) {
        this.chkd = chkd;
    }

    public String getViralCount() {
        return viralCount;
    }

    public void setViralCount(String viralCount) {
        this.viralCount = viralCount;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }
}
