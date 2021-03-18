package com.example.daktari;

public class upload {
    private String mName;
    private String mImageUrl;

    public upload(){
//empty constructor neede
    }
    public upload(String name, String imageUrl){
        if(name.trim().equals("")){
            name="No name";
        }
        mName=name;
        mImageUrl=imageUrl;
    }

    public String  GetName(){
        return mName;
    }
    public void setmName(String name){
        mName=name;
    }
    public String getmImageUrl(){
        return mImageUrl;
    }
    public void  setmImageUrl(String imageUrl){
        mImageUrl=imageUrl;
    }
}
