/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author daoducdanh
 */
public class MovieType {
    private String movieTypeId;
    private String name;
    
    public MovieType(){
        
    }

    public MovieType(String movieTypeId) {
        this.movieTypeId = movieTypeId;
    }

    public MovieType(String movieTypeId, String name) {
        this.movieTypeId = movieTypeId;
        this.name = name;
    }

    public String getMovieTypeId() {
        return movieTypeId;
    }

    public void setMovieTypeId(String movieTypeId) {
        this.movieTypeId = movieTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MovieType{" + "movieTypeId=" + movieTypeId + ", name=" + name + '}';
    }
    
}
