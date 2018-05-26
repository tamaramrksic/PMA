package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacilityHall {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("facility")
    @Expose
    private Facility facility;

    public FacilityHall() {}

    public FacilityHall(Long id, String name, Facility facility) {
        this.id = id;
        this.name = name;
        this.facility = facility;
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

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
