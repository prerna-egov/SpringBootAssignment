package com.assignment.assignment.model;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String gender;
    private String mobileNumber;
    private Address address;
    private long createdTime;
    private boolean isActive;


    public User() {

    }


    public User(UUID id, String name, String gender, String mobileNumber, Address address,long createdTime, boolean isActive) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.createdTime = createdTime;
        this.isActive = isActive;
    }

    public User(String name, String gender, String mobileNumber, Address address, boolean isActive) {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = String.valueOf(address);
//    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
