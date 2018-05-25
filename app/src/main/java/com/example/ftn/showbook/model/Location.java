package com.example.ftn.showbook.model;

public class Location {

    private Long id;

    private String name;

    private String zipCode;

    private String state;

    public Location() {}

    public Location(Long id, String name, String zipCode, String state) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.state = state;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
