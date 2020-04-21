package com.tricky_tweaks.homekeeping.model.company;

import java.util.Map;

public class MetaData {
    String dateAdded;
    Map<String,String> services;

    public MetaData() {}

    public MetaData(String dateAdded, Map<String,String> services) {
        this.dateAdded = dateAdded;
        this.services = services;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Map<String, String> getServices() {
        return services;
    }

    public void setServices(Map<String, String> services) {
        this.services = services;
    }
}
