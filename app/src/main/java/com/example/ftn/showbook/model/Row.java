package com.example.ftn.showbook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("number")
    @Expose
    private Integer number;

    @SerializedName("facilityHall")
    @Expose
    private FacilityHall facilityHall;

    public Row() {}

    public Row(Long id, Integer number, FacilityHall facilityHall) {
        this.id = id;
        this.number = number;
        this.facilityHall = facilityHall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public FacilityHall getFacilityHall() {
        return facilityHall;
    }

    public void setFacilityHall(FacilityHall facilityHall) {
        this.facilityHall = facilityHall;
    }
}
