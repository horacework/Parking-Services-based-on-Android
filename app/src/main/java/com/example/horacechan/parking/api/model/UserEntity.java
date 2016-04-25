package com.example.horacechan.parking.api.model;

/**
 * Created by HoraceChan on 2016/4/25.
 */
public class UserEntity {


    /**
     * id : 213121
     * name : chen
     * password : ****
     * isDel : 0
     */

    private String id;
    private String name;
    private String password;
    private int isDel;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
