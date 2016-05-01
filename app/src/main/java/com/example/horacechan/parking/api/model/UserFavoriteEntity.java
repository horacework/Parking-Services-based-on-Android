package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/5/1.
 */
public class UserFavoriteEntity {


    private String id;
    private String name;
    private String markerId;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
