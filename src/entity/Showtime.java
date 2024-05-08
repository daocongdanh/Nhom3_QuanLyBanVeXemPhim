/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author daoducdanh
 */
public class Showtime {
    private String showtimeId;
    private double ticketPrice;
    private LocalDateTime startTime;
    private Room room;
    private Movie movie;
    public Showtime(){
        
    }

    public Showtime(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Showtime(String showtimeId, double ticketPrice, LocalDateTime startTime, Room room, Movie movie) {
        this.showtimeId = showtimeId;
        this.ticketPrice = ticketPrice;
        this.startTime = startTime;
        this.room = room;
        this.movie = movie;
    }

    
    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Showtime{" + "showtimeId=" + showtimeId + ", ticketPrice=" + ticketPrice + ", startTime=" + startTime + '}';
    }

    
    
    
    
}
