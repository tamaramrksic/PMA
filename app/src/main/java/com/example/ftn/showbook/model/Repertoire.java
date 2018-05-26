package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Repertoire {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("start")
    @Expose
    private Date start;

    @SerializedName("end")
    @Expose
    private Date end;

    @SerializedName("facility")
    @Expose
    private Facility facility;

    public Repertoire() {}

    public Repertoire(Long id, Date start, Date end, Facility facility) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.facility = facility;
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

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
