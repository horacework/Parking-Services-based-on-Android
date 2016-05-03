package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/5/3.
 */
public class BaiduPOIEntity {

    /**
     * name : 招商银行(北京东方君悦大酒店北)
     * location : {"lat":39.91597,"lng":116.423475}
     * address : 东城区东长安街1号东方广场E3座，安永大楼二层平台
     * street_id : 803708f1828d71780189726b
     * telephone : (010)85150201,(010)85186723-8008
     * detail : 1
     * uid : 803708f1828d71780189726b
     */

    private String name;
    /**
     * lat : 39.91597
     * lng : 116.423475
     */

    private LocationBean location;
    private String address;
    private String street_id;
    private String telephone;
    private int detail;
    private String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet_id() {
        return street_id;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static class LocationBean {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
