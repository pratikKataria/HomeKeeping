package com.tricky_tweaks.homekeeping.model.company;

import org.jetbrains.annotations.NotNull;

public class Branch {
    private String branchName;
    private String branchCode;
    private String branchEmail;
    private String branchCity;
    private String branchState;
    private String branchLocation;
    private String branchAreaCode;

    public Branch() { }

    public Branch(String branchName, String branchCode, String branchEmail, String branchCity, String branchState, String branchLocation, String branchAreaCode) {
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.branchEmail = branchEmail;
        this.branchCity = branchCity;
        this.branchState = branchState;
        this.branchLocation = branchLocation;
        this.branchAreaCode = branchAreaCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public String getBranchState() {
        return branchState;
    }

    public void setBranchState(String branchState) {
        this.branchState = branchState;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public String getBranchAreaCode() {
        return branchAreaCode;
    }

    public void setBranchAreaCode(String branchAreaCode) {
        this.branchAreaCode = branchAreaCode;
    }

    //todo remove this in production
    @NotNull
    @Override
    public String toString() {
        return "Branch{" +
                "branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", branchEmail='" + branchEmail + '\'' +
                ", branchCity='" + branchCity + '\'' +
                ", branchState='" + branchState + '\'' +
                ", branchLocation='" + branchLocation + '\'' +
                ", branchAreaCode='" + branchAreaCode + '\'' +
                '}';
    }
}
