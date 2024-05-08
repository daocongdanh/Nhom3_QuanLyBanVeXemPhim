/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.MovieDAL;
import dal.MovieTypeDAL;
import entity.Movie;
import entity.MovieType;
import enums.MovieStatus;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class MovieBUS {
    private MovieDAL movieDAL;
    public MovieBUS(){
        movieDAL = new MovieDAL();
    }
    public List<Movie> getAllMovie(){
        return movieDAL.findAll();
    }
    public List<Movie> getMovieByKeywordAndMovieStatus(String Keyword, MovieStatus movieStatus){
        return movieDAL.findByKeywordAndMovieStatus(Keyword, movieStatus);
    }
    public boolean insertMovie(Movie movie){
        return movieDAL.insert(movie);
    }
    public Movie getMovieById(String id){
        return movieDAL.findById(id);
    }
    public Movie getMovieByName(String name){
        return movieDAL.findByName(name);
    }
    public List<Movie> getAllMovieExistsShowtimeByTime(String search, String type, LocalDate date){
        return movieDAL.findByShowtime(search,type,date);
    }
    public boolean existsByName(String name){
        return movieDAL.existsByName(name);
    }
    public boolean findByExistsShowtime(String id){
        return movieDAL.findByExistsShowtime(id);
    }
    public boolean updateMovie(Movie movie){
        return movieDAL.update(movie);
    }
    public boolean deleteMovieById(String id) {
        return movieDAL.delete(id);
    }
}
