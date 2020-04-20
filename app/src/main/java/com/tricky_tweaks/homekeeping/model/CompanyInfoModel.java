package com.tricky_tweaks.homekeeping.model;

public class CompanyInfoModel {
    private HeadOffice headOffice;
    private Branch branch;
    private Company company;
    private BranchRepresentative representative;

    public CompanyInfoModel() {}

    public CompanyInfoModel(HeadOffice headOffice, Company company, Branch branch, BranchRepresentative representative) {
        this.headOffice = headOffice;
        this.company = company;
        this.branch = branch;
        this.representative = representative;
    }

    public CompanyInfoModel(Branch branch, Company company, BranchRepresentative representative) {
        this.branch = branch;
        this.company = company;
        this.representative = representative;
    }

    public HeadOffice getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(HeadOffice headOffice) {
        this.headOffice = headOffice;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public BranchRepresentative getRepresentative() {
        return representative;
    }

    public void setRepresentative(BranchRepresentative representative) {
        this.representative = representative;
    }
}

