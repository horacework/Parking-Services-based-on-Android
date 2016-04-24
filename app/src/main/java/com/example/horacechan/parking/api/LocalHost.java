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

    public void setUserid(String id){
        sHostInfo.setId(id);
    }

    static class HostInfo{
        private String id;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


}
