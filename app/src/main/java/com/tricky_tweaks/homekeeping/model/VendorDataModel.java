package com.tricky_tweaks.homekeeping.model;

import java.io.Serializable;

public class VendorDataModel implements Serializable {
    private BankDetailsModel bankDetailsModel;
    private CurrentAddressModel currentAddressModel;
    private IdentityProofModel identityProofModel;
    private PersonalDetailModel personalDetailModel;
    private Metadata metadata;

    public VendorDataModel() {}

    public VendorDataModel(Metadata metadata, CurrentAddressModel currentAddressModel) {
        this.currentAddressModel = currentAddressModel;
        this.metadata = metadata;
    }

    public VendorDataModel(BankDetailsModel bankDetailsModel, IdentityProofModel identityProofModel, CurrentAddressModel currentAddressModel, PersonalDetailModel personalDetailModel, Metadata metadata) {
        this.bankDetailsModel = bankDetailsModel;
        this.identityProofModel = identityProofModel;
        this.currentAddressModel = currentAddressModel;
        this.personalDetailModel = personalDetailModel;
        this.metadata = metadata;
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

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
