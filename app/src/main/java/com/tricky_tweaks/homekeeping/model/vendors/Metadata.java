package com.tricky_tweaks.homekeeping.model.vendors;

public class Metadata {
    private String userId;
    private String applicationId;
    private String date;
    private String service;
    private String status;

    public Metadata() {}

    public Metadata(String userId, String applicationId, String date, String service, String status) {
        this.userId = userId;
        this.applicationId = applicationId;
        this.date = date;
        this.service = service;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "userId='" + userId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", date='" + date + '\'' +
                ", service='" + service + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
