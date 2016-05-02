package com.example.horacechan.parking.api.model;


import java.sql.Timestamp;

public class UserStatusEntity {

    /**
     * logId : df8d5af0-103d-11e6-9ba1-00ff9099da81
     * markerName : 华工大停车场
     * carName : 川A 51211
     * enterTime : May 2, 2016 4:31:06 PM
     * price : 2.56
     */

    private String logId;
    private String markerName;
    private String carName;
    private Timestamp enterTime;
    private double price;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Timestamp getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Timestamp enterTime) {
        this.enterTime = enterTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
