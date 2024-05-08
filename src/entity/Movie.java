/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enums.MovieStatus;

/**
 *
 * @author daoducdanh
 */
public class Movie {
    private String movieId;
    private String name;
    private int runningTime;
    private String country;
    private String language;
    private String description;
    private int releaseYear;
    private String author;
    private String poster;
    private MovieStatus movieStatus;
    private MovieType movieType;
    
    public Movie(){
        
    }

    public Movie(String movieId) {
        this.movieId = movieId;
    }

    public Movie(String movieId, String name, int runningTime, String country, String language, String description, int releaseYear, String author, String poster, MovieStatus movieStatus, MovieType movieType) {
        this.movieId = movieId;
        this.name = name;
        this.runningTime = runningTime;
        this.country = country;
        this.language = language;
        this.description = description;
        this.releaseYear = releaseYear;
        this.author = author;
        this.poster = poster;
        this.movieStatus = movieStatus;
        this.movieType = movieType;
    }



    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }
    
    
    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    @Override
    public String toString() {
        return "Movie{" + "movieId=" + movieId + ", name=" + name + ", runningTime=" + runningTime + ", country=" + country + ", language=" + language + ", description=" + description + ", releaseYear=" + releaseYear + ", author=" + author + ", poster=" + poster + ", movieStatus=" + movieStatus + '}';
    }

    

}
