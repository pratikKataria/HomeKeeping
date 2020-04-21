package com.tricky_tweaks.homekeeping.model.company;

public class Company {
    private String companyName;
    private String companyEmail;

    public Company() {}

    public Company(String companyName, String companyEmail) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
}
