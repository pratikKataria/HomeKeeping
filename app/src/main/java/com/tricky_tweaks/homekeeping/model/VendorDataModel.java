package com.tricky_tweaks.homekeeping.model;

public class VendorDataModel {
    private BankDetailsModel bankDetailsModel;
    private CurrentAddressModel currentAddressModel;
    private IdentityProofModel identityProofModel;
    private PersonalDetailModel personalDetailModel;

    public VendorDataModel() {}

    public VendorDataModel(BankDetailsModel bankDetailsModel, IdentityProofModel identityProofModel, CurrentAddressModel currentAddressModel, PersonalDetailModel personalDetailModel) {
        this.bankDetailsModel = bankDetailsModel;
        this.identityProofModel = identityProofModel;
        this.currentAddressModel = currentAddressModel;
        this.personalDetailModel = personalDetailModel;
    }

    public BankDetailsModel getBankDetailsModel() {
        return bankDetailsModel;
    }

    public void setBankDetailsModel(BankDetailsModel bankDetailsModel) {
        this.bankDetailsModel = bankDetailsModel;
    }

    public IdentityProofModel getIdentityProofModel() {
        return identityProofModel;
    }

    public void setIdentityProofModel(IdentityProofModel identityProofModel) {
        this.identityProofModel = identityProofModel;
    }

    public CurrentAddressModel getCurrentAddressModel() {
        return currentAddressModel;
    }

    public void setCurrentAddressModel(CurrentAddressModel currentAddressModel) {
        this.currentAddressModel = currentAddressModel;
    }

    public PersonalDetailModel getPersonalDetailModel() {
        return personalDetailModel;
    }

    public void setPersonalDetailModel(PersonalDetailModel personalDetailModel) {
        this.personalDetailModel = personalDetailModel;
    }


}
