package com.example.ftn.showbook.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationInfo {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("eventId")
    @Expose
    private Long eventId;

    @SerializedName("selectedSeats")
    @Expose
    private List<Long> selectedSeats;

    public ReservationInfo() {}

    public ReservationInfo(String username, Long eventId, List<Long> selectedSeats) {
        this.username = username;
        this.eventId = eventId;
        this.selectedSeats = selectedSeats;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<Long> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Long> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }
}
