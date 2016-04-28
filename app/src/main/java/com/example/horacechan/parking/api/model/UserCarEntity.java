package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/4/28.
 */
public class UserCarEntity {

    /**
     * carId : bfd083b6-9179-4c99-81ee-2711232a463c
     * userId : bfd083b6-9179-4c99-81ee-2713bc2a463c
     * plate : 粤B 12345
     * isDel : 0
     */

    private String carId;
    private String userId;
    private String plate;
    private int isDel;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
