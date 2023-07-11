package com.assignment.assignment.model;

import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

public class UserSearchCriteria {

    private UUID id;
    private String mobileNumber;
    private boolean isActive;
    public UserSearchCriteria(@Qualifier UUID id, String mobileNumber) {
        this.id = id;
        this.mobileNumber = mobileNumber;
    }

    public UserSearchCriteria(boolean isActive) {
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
