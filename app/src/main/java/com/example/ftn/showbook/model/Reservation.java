package com.example.ftn.showbook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reservation {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;

    @SerializedName("rating")
    @Expose
    private Rating rating;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("event")
    @Expose
    private Event event;

    public Reservation() {}

    public Reservation(Long id, Double totalPrice, Rating rating, User user, Event event) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.rating = rating;
        this.user = user;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
