package com.example.ftn.showbook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SeatAvailability {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("start")
    @Expose
    private Date start;

    @SerializedName("end")
    @Expose
    private Date end;

    public enum Status {
        FREE,
        RESERVED
    }

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("event")
    @Expose
    private Event event;

    @SerializedName("seat")
    @Expose
    private Seat seat;

    public SeatAvailability() {}

    public SeatAvailability(Long id, Date start, Date end, Status status, Event event, Seat seat) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.status = status;
        this.event = event;
        this.seat = seat;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
