package com.example.ftn.showbook.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mail {

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("commentUser")
    @Expose
    private String commentUser;

    public Mail() {}

    public Mail(String to, String subject, String text, String commentUser) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.commentUser = commentUser;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }
}
