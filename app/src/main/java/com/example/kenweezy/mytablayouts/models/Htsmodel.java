package com.example.kenweezy.mytablayouts.models;

public class Htsmodel {

    String clientCode,gender,age,result,submitted,released;

    public Htsmodel() {
    }

    public Htsmodel(String clientCode, String gender, String age, String result, String submitted, String released) {
        this.clientCode = clientCode;
        this.gender = gender;
        this.age = age;
        this.result = result;
        this.submitted = submitted;
        this.released = released;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
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
