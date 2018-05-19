package com.example.ftn.showbook.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User{

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String username;
    private String password;
    private Integer maxDistance;

    public enum FacilityType {
        ALL,
        CINEMA,
        THEATER
    }

    private FacilityType facilityType;

    public User() {};
    public User(Long id, String firstName, String lastName, String address, String username, String password, Integer maxDistance, FacilityType facilityType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.username = username;
        this.password = password;
        this.maxDistance = maxDistance;
        this.facilityType = facilityType;
    }



    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

}
