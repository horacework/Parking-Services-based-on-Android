package com.example.horacechan.parking.api;

/**
 * Created by HoraceChan on 2016/4/24.
 */
public enum  LocalHost {
    INSTANCE;

    private static HostInfo sHostInfo;


    public void  init(){
        if (sHostInfo==null)
        sHostInfo=new HostInfo();
    }

    public String getUserid(){
        return sHostInfo.getId();
    }
    public String getUserName(){
        return sHostInfo.getUsername();
    }
    public String getUserCar(){
        return sHostInfo.getUserCar();
    }

    public void setUserid(String id){
        sHostInfo.setId(id);
    }
    public void setUserName(String userName){
        sHostInfo.setUsername(userName);
    }
    public void setUserCar(String userCar){
        sHostInfo.setUserCar(userCar);
    }

    static class HostInfo{
        private String id;

        private String username;

        private String userCar;


        public String getUserCar() {
            return userCar;
        }

        public void setUserCar(String userCar) {
            this.userCar = userCar;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }


}
