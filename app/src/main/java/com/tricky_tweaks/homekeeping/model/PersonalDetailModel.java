package com.tricky_tweaks.homekeeping.model;

import org.jetbrains.annotations.Contract;

public class PersonalDetailModel {

    private String name;
    private String aadharNo;
    private String panNo;
    private String dob;
    private String gender;
    private String fatherName;

    public PersonalDetailModel() {}

    public PersonalDetailModel(String name, String aadharNo, String panNo, String dob, String gender, String fatherName) {
        this.name = name;
        this.aadharNo = aadharNo;
        this.panNo = panNo;
        this.dob = dob;
        this.gender = gender;
        this.fatherName = fatherName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    @Override
    public String toString() {
        return "PersonalDetailModel{" +
                "name='" + name + '\'' +
                ", aadharNo='" + aadharNo + '\'' +
                ", panNo='" + panNo + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", fatherName='" + fatherName + '\'' +
                '}';
    }
}
