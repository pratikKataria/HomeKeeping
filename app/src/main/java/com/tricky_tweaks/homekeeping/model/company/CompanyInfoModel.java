package com.tricky_tweaks.homekeeping.model.company;

import org.jetbrains.annotations.NotNull;

public class CompanyInfoModel {
    private HeadOffice headOffice;
    private Branch branch;
    private Company company;
    private BranchRepresentative representative;
    private MetaData metadata;

    public CompanyInfoModel() {}

    public CompanyInfoModel(HeadOffice headOffice, Company company, Branch branch, BranchRepresentative representative) {
        this.headOffice = headOffice;
        this.company = company;
        this.branch = branch;
        this.representative = representative;
    }

    public CompanyInfoModel(Branch branch, Company company, BranchRepresentative representative, MetaData metadata) {
        this.branch = branch;
        this.company = company;
        this.representative = representative;
        this.metadata = metadata;
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

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }

    //todo remove this in production
    @NotNull
    @Override
    public String toString() {
        return "CompanyInfoModel{" +
                ", branch=" + branch.toString() +
                ", company=" + company.toString() +
                ", representative=" + representative.toString() +
                ", metadata=" + metadata.toString() +
                '}';
    }
}

