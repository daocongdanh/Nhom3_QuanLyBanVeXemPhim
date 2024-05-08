/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.MovieTypeDAL;
import entity.MovieType;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class MovieTypeBUS {
    private MovieTypeDAL movieTypeDAL;
    
    public MovieTypeBUS(){
        movieTypeDAL = new MovieTypeDAL();
    }
    public MovieType getMovieTypeById(String id){
        return movieTypeDAL.findById(id);
    }
    public MovieType getMovieTypeByName(String name){
        return movieTypeDAL.findByName(name);
    }
    
    public List<MovieType> getAllMovieType(){
        return movieTypeDAL.findAll();
    }
}
