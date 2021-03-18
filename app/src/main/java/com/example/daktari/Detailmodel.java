package com.example.daktari;

public class Detailmodel {
    private String proposal,expertise,username;
    public Detailmodel(){

    }

    public void setExpertise(String expertise){
        this.expertise = expertise;
    }

    public String getExpertise(){
        return expertise;
    }
    public void setusername(String username){
        this.username = username;
    }

    public String getusername(){
        return username;
    }
    public String getproposal() {
        return proposal;
    }

    public void setproposal(String proposal) {
        this.proposal = proposal;
    }

}

