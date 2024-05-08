/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.RoomDAL;
import entity.Room;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class RoomBUS {
    private RoomDAL roomDAL;
    public RoomBUS(){
        roomDAL = new RoomDAL();
    }
    
    public List<Room> getAllRoom(){
        return roomDAL.findAll();
    }
    public Room getRoomById(String id){
        return roomDAL.findById(id);
    }
    public Room getRoomByName(String name){
        return roomDAL.findByName(name);
    }
    public Room insertRoom(Room room){
        return roomDAL.insertReturnRoom(room);
    }
    
    public boolean updateRoom(Room room){
        return roomDAL.update(room);
    }
    public List<Room> getRoomByStatus(String status){
        return roomDAL.findByRoomStatus(status);
    }
}
