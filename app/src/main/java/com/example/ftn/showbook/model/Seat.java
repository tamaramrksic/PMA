package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seat {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("number")
    @Expose
    private Integer number;

    @SerializedName("row")
    @Expose
    private Row row;

    public Seat() {}

    public Seat(Long id, Integer number, Row row) {
        this.id = id;
        this.number = number;
        this.row = row;
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

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }
}
