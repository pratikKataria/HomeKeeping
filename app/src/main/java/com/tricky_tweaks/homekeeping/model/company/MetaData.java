package com.tricky_tweaks.homekeeping.model.company;

import java.util.Map;

public class MetaData {
    private String dateAdded;
    private String databaseRefKey;
    private Map<String,String> services;

    public MetaData() {}

    public MetaData(String dateAdded, String databaseRefKey, Map<String, String> services) {
        this.dateAdded = dateAdded;
        this.databaseRefKey = databaseRefKey;
        this.services = services;
    }

    public String getDatabaseRefKey() {
        return databaseRefKey;
    }

    public void setDatabaseRefKey(String databaseRefKey) {
        this.databaseRefKey = databaseRefKey;
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

    @Override
    public String toString() {
        return "MetaData{" +
                "dateAdded='" + dateAdded + '\'' +
                ", databaseRefKey='" + databaseRefKey + '\'' +
                ", services=" + services +
                '}';
    }
}
