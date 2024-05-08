/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author daoducdanh
 */
public class Ticket {
    private String ticketId;
    private Bill bill;
    private Showtime showtime;
    private Seat seat;
    
    public Ticket(){
        
    }

    public Ticket(String ticketId) {
        this.ticketId = ticketId;
    }

    public Ticket(String ticketId, Bill bill, Showtime showtime, Seat seat) {
        this.ticketId = ticketId;
        this.bill = bill;
        this.showtime = showtime;
        this.seat = seat;
    }
    

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    
    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    
    public double calcTotal(){
        return showtime.getTicketPrice()*(1 + seat.getPricePercent()*0.01);
    }

    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + '}';
    }
    
}
