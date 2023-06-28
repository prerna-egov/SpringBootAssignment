package com.assignment.assignment.model;

public class UserSearchCriteria {

    private int id;
    private String mobileNumber;

    //    public UserSearchCriteria(){
//
//    }
    public UserSearchCriteria(int id, String mobileNumber) {
        this.id = id;
        this.mobileNumber = mobileNumber;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getMobileNumber() {

        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {

        this.mobileNumber = mobileNumber;
    }
}
