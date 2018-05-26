package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("start")
    @Expose
    private Date start;

    @SerializedName("end")
    @Expose
    private Date end;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("show")
    @Expose
    private Show show;

    @SerializedName("facilityHall")
    @Expose
    private FacilityHall facilityHall;

    @SerializedName("repertoire")
    @Expose
    private Repertoire repertoire;

    public Event() {}

    public Event(Long id, Date start, Date end, Double price, Show show,
                 FacilityHall facilityHall, Repertoire repertoire) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.price = price;
        this.show = show;
        this.facilityHall = facilityHall;
        this.repertoire = repertoire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public FacilityHall getFacilityHall() {
        return facilityHall;
    }

    public void setFacilityHall(FacilityHall facilityHall) {
        this.facilityHall = facilityHall;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }
}
