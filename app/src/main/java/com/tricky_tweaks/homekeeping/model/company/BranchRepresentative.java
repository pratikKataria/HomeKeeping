package com.tricky_tweaks.homekeeping.model.company;

public class BranchRepresentative {
    private String representativeName;
    private String representativePhone;
    private String representativeEmail;
    private String representativePosition;

    public BranchRepresentative() {}

    public BranchRepresentative(String representativeName, String representativePhone, String representativeEmail, String representativePosition) {
        this.representativeName = representativeName;
        this.representativePhone = representativePhone;
        this.representativeEmail = representativeEmail;
        this.representativePosition = representativePosition;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativePhone() {
        return representativePhone;
    }

    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    public String getRepresentativeEmail() {
        return representativeEmail;
    }

    public void setRepresentativeEmail(String representativeEmail) {
        this.representativeEmail = representativeEmail;
    }

    public String getRepresentativePosition() {
        return representativePosition;
    }

    public void setRepresentativePosition(String representativePosition) {
        this.representativePosition = representativePosition;
    }

    //todo remove this in production
    @Override
    public String toString() {
        return "BranchRepresentative{" +
                "representativeName='" + representativeName + '\'' +
                ", representativePhone='" + representativePhone + '\'' +
                ", representativeEmail='" + representativeEmail + '\'' +
                ", representativePosition='" + representativePosition + '\'' +
                '}';
    }
}
