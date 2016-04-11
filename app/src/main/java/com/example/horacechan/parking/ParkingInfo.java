package com.example.horacechan.parking;

import com.baidu.platform.comapi.map.E;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkingInfo implements Serializable {

    private double latitude;
    private double longitude;
    private int imgId;
    private String name;
    private String distance;
    private int zan;

    public static List<ParkingInfo> infos = new ArrayList<>();

    public ParkingInfo(double latitude, double longitude, int imgId, String name, String distance, int zan) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.zan = zan;
    }

    static {
        infos.add(new ParkingInfo(34.242652, 108.971171, R.mipmap.a01, "英伦贵族小旅馆",
                "距离209米", 1456));
        infos.add(new ParkingInfo(34.242952, 108.972171, R.mipmap.a02, "沙井国际洗浴会所",
                "距离897米", 456));
        infos.add(new ParkingInfo(34.242852, 108.973171, R.mipmap.a03, "五环服装城",
                "距离249米", 1456));
        infos.add(new ParkingInfo(34.242152, 108.971971, R.mipmap.a04, "老米家泡馍小炒",
                "距离679米", 1456));
        infos.add(new ParkingInfo(23.050997, 113.401106, R.mipmap.a04, "广工大医院",
                "距离666米", 186));
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

}
