/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enums.RoomStatus;

/**
 *
 * @author daoducdanh
 */
public class Room {
    private String roomId;
    private String name;
    private int seatQuantity;
    private RoomStatus roomStatus;
    
    public Room(){
        
    }

    public Room(String roomId) {
        this.roomId = roomId;
    }

    public Room(String roomId, String name, int seatQuantity, RoomStatus roomStatus) {
        this.roomId = roomId;
        this.name = name;
        this.seatQuantity = seatQuantity;
        this.roomStatus = roomStatus;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }
    


    @Override
    public String toString() {
        return "Room{" + "roomId=" + roomId + ", name=" + name + ", seatQuantity=" + seatQuantity + ", roomStatus=" + roomStatus + ", seats=" + '}';
    }
    
    
}
