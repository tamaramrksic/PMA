package com.example.ftn.showbook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facility {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private  String name;

    public enum Type {
        CINEMA,
        THEATER
    }

    @SerializedName("type")
    @Expose
    private Facility.Type type;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("location")
    @Expose
    private Location location;

    public Facility() {}

    public Facility(Long id, String name, Type type, String address, Location location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
