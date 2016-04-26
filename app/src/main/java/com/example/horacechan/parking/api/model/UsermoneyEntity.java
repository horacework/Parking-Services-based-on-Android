package com.example.horacechan.parking.api.model;


public class UsermoneyEntity {

    /**
     * logId : 1
     * userId : bfd083b6-9179-4c99-81ee-2713bc2a463c
     * type : 2
     * figure : 10
     * remain : 10
     * currentTime : Apr 26, 2016 10:59:56 PM
     */

    private int logId;
    private String userId;
    private int type;
    private int figure;
    private int remain;
    private String currentTime;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
