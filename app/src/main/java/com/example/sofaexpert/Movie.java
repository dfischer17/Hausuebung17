package com.example.sofaexpert;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie implements Serializable {
    private final String baseUrl = "http://image.tmdb.org/t/p/w154/";
    private String url;
    private String title;
    private double rating;
    private LocalDate releaseDate;
    private String description;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    public Movie(String url, String title, double rating, LocalDate releaseDate, String description) {
        this.url = url;
        this.title = title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.description = description;
    }

    public String getUrl() {
        return baseUrl + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
