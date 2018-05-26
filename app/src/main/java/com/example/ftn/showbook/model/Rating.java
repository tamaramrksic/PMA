package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("rate")
    @Expose
    private Integer rate;

    @SerializedName("show")
    @Expose
    private Show show;

    public Rating() {}

    public Rating(Long id, Integer rate, Show show) {
        this.id = id;
        this.rate = rate;
        this.show = show;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
