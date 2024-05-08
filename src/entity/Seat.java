/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enums.SeatType;
import java.util.Objects;

/**
 *
 * @author daoducdanh
 */
public class Seat {
    private String seatId;
    private int seatNumber;
    private String row;
    private SeatType seatType;
    private Room room;
    
    public Seat(){
        
    }

    public Seat(String seatId) {
        this.seatId = seatId;
    }

    public Seat(String seatId, int seatNumber, String row, SeatType seatType, Room room) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.row = row;
        this.seatType = seatType;
        this.room = room;
    }

    

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    public int getPricePercent(){
        return seatType == SeatType.VIP ? 20 : 0;
    }
    
    public String getName(){
        return "" + row + seatNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.seatId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seat other = (Seat) obj;
        return Objects.equals(this.seatId, other.seatId);
    }

    @Override
    public String toString() {
        return "Seat{" + "seatId=" + seatId + ", seatNumber=" + seatNumber + ", row=" + row + ", seatType=" + seatType + '}';
    }
    
    
    
    
    
    
}
