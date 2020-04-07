package com.tricky_tweaks.homekeeping.model;

public class IdentityProofModel {
    String aadhaarCardFrontImageUrl;
    String aadhaarCardBackImageUrl;
    String panCardUrl;

    public IdentityProofModel() {}

    public IdentityProofModel(String aadhaarCardFrontImageUrl, String aadhaarCardBackImageUrl, String panCardUrl) {
        this.aadhaarCardFrontImageUrl = aadhaarCardFrontImageUrl;
        this.aadhaarCardBackImageUrl = aadhaarCardBackImageUrl;
        this.panCardUrl = panCardUrl;
    }

    public String getAadhaarCardFrontImageUrl() {
        return aadhaarCardFrontImageUrl;
    }

    public void setAadhaarCardFrontImageUrl(String aadhaarCardFrontImageUrl) {
        this.aadhaarCardFrontImageUrl = aadhaarCardFrontImageUrl;
    }

    public String getAadhaarCardBackImageUrl() {
        return aadhaarCardBackImageUrl;
    }

    public void setAadhaarCardBackImageUrl(String aadhaarCardBackImageUrl) {
        this.aadhaarCardBackImageUrl = aadhaarCardBackImageUrl;
    }

    public String getPanCardUrl() {
        return panCardUrl;
    }

    public void setPanCardUrl(String panCardUrl) {
        this.panCardUrl = panCardUrl;
    }
}
