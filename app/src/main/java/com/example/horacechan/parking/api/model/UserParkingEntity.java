package com.example.horacechan.parking.api.model;

public class UserParkingEntity {

    /**
     * logId : bad5ee26-0ea0-11e6-969e-00ff9099da81
     * enterTime : Apr 30, 2016 6:13:07 AM
     * leaveTime : Apr 30, 2016 11:19:13 AM
     * plate : 粤A 14444
     * name : 广工大停车场
     */

    private String logId;
    private String enterTime;
    private String leaveTime;
    private String plate;
    private String name;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
