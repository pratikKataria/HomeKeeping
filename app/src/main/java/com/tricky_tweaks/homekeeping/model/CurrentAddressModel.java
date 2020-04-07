package com.tricky_tweaks.homekeeping.model;

public class CurrentAddressModel {
    private String HouseNumber;
    private String locality;
    private String pincode;
    private String city;
    private String state;

    public CurrentAddressModel( ) {}

    public CurrentAddressModel(String houseNumber, String locality, String pincode, String city, String state) {
        HouseNumber = houseNumber;
        this.locality = locality;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
