package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("show")
    @Expose
    private Show show;

    @SerializedName("user")
    @Expose
    private User user;

    public Comment() {}

    public Comment(Long id, String text, Show show, User user) {
        this.id = id;
        this.text = text;
        this.show = show;
        this.user = user;
    }



    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Show getShow() {
        return show;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
