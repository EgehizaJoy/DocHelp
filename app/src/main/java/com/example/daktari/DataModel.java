package com.example.daktari;

public class DataModel {

    private String caption,imgURL,username;
    public DataModel(){

    }

    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }

    public String getImgURL(){
        return imgURL;
    }
    public void setusername(String username){
        this.username = username;
    }

    public String getusername(){
        return username;
    }
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
