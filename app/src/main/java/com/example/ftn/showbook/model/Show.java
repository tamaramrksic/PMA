package com.example.ftn.showbook.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Show {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    public enum Type {
        MOVIE,
        PLAY
    }

    @SerializedName("type")
    @Expose
    private Type type;


    @SerializedName("genre")
    @Expose
    private String genre;

    @SerializedName("duration")
    @Expose
    private Double duration;

    @SerializedName("performers")
    @Expose
    private String performers;

    @SerializedName("directors")
    @Expose
    private String directors;

    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("numOfRatings")
    @Expose
    private Integer numOfRatings;

    public Show() {}

    public Show(Long id, String name, String description, Type type, String genre,
                Double duration, String performers, String directors, Double rating,
                Integer numOfRatings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.genre = genre;
        this.duration = duration;
        this.performers = performers;
        this.directors = directors;
        this.rating = rating;
        this.numOfRatings = numOfRatings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getPerformers() {
        return performers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(Integer numOfRatings) {
        this.numOfRatings = numOfRatings;
    }
}
