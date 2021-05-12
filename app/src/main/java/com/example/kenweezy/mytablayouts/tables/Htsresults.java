package com.example.kenweezy.mytablayouts.tables;

import com.orm.SugarRecord;

public class Htsresults extends SugarRecord {

    String clientcode,gender,age,result,submitted,released,sampleid;

    public Htsresults() {
    }

    public Htsresults(String clientcode, String gender, String age, String result, String submitted, String released,String sampleid) {
        this.clientcode = clientcode;
        this.gender = gender;
        this.age = age;
        this.result = result;
        this.submitted = submitted;
        this.released = released;
        this.sampleid=sampleid;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }
}
