package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("num")
    @Expose
    private Integer num;

    @SerializedName("show")
    @Expose
    private Show show;

    public Rating() {}

    public Rating(Long id, Integer num, Show show) {
        this.id = id;
        this.num = num;
        this.show = show;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
