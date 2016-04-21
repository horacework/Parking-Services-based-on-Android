package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/4/22.
 */
public class MarkId {

    /**
     * id : b4850fda-047b-11e6-b034-00ff9099da81
     * latitude : 23.0444140612
     * longitude : 113.3944387036
     * isDel : 0
     */

    private String id;
    private double latitude;
    private double longitude;
    private int isDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
