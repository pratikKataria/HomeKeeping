package com.tricky_tweaks.homekeeping.model;

public class BankDetailsModel {
    private String name;
    private String accountNumber;
    private String passbookImageUrl;
    private String IFSCCode;

    public BankDetailsModel() {}

    public BankDetailsModel(String name, String accountNumber, String passbookImageUrl, String IFSCCode) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.passbookImageUrl = passbookImageUrl;
        this.IFSCCode = IFSCCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassbookImageUrl() {
        return passbookImageUrl;
    }

    public void setPassbookImageUrl(String passbookImageUrl) {
        this.passbookImageUrl = passbookImageUrl;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        this.IFSCCode = IFSCCode;
    }
}
