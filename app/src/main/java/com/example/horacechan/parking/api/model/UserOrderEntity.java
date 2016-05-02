package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/5/2.
 */
public class UserOrderEntity {

    /**
     * orderId : 8389f6ea-1032-11e6-9ba1-00ff9099da81
     * markerId : b4850fda-047b-11e6-b034-00ff9099da81
     * markName : 广工大停车场
     * orderTime : May 5, 2016 1:26:28 PM
     */

    private String orderId;
    private String markerId;
    private String markName;
    private String orderTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
