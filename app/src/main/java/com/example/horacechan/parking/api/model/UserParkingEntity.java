package com.example.horacechan.parking.api.model;

public class UserParkingEntity {


    /**
     * logId : bad5ee26-0ea0-11e6-969e-00ff9099da81
     * userId : bfd083b6-9179-4c99-81ee-2713bc2a463c
     * carId : 0a9a5764-f36f-4450-8a36-f721ed4dae2b
     * markerId : b4850fda-047b-11e6-b034-00ff9099da81
     * enterTime : Apr 30, 2016 6:13:07 AM
     * leaveTime : Apr 30, 2016 11:19:13 AM
     * isOrder : 0
     * isComplete : 1
     */

    private String logId;
    private String userId;
    private String carId;
    private String markerId;
    private String enterTime;
    private String leaveTime;
    private int isOrder;
    private int isComplete;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }
}
