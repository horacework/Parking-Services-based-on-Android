package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/4/21.
 */
public class MarkInfo {


    /**
     * id : b4850fda-047b-11e6-b034-00ff9099da81
     * name : 广工大停车场
     * latitude : 0
     * longitude : 0
     * zan : 0
     * isDel : 0
     * price : 2
     */

    private String id;
    private String name;
    private int latitude;
    private int longitude;
    private int zan;
    private int isDel;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "MarkInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", zan=" + zan +
                ", isDel=" + isDel +
                ", price=" + price +
                '}';
    }
}
