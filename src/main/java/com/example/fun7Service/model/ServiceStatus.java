package com.example.fun7Service.model;

public class ServiceStatus {
    private String multiplayer;
    private String customerSupport;
    private String ads;

    public ServiceStatus() {
    }

    public ServiceStatus(String multiplayer, String customerSupport, String ads) {
        this.multiplayer = multiplayer;
        this.customerSupport = customerSupport;
        this.ads = ads;
    }

    public String getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(String multiplayer) {
        this.multiplayer = multiplayer;
    }

    public String getCustomerSupport() {
        return customerSupport;
    }

    public void setCustomerSupport(String customerSupport) {
        this.customerSupport = customerSupport;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }
}
