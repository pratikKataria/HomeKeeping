package com.tricky_tweaks.homekeeping.model.company;

import org.jetbrains.annotations.NotNull;

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

    //todo remove this in production
    @NotNull
    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                '}';
    }
}
