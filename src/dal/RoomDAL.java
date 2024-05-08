/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Room;
import enums.RoomStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class RoomDAL implements BaseDAL<Room>{
    private Connection con;
    public RoomDAL(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try{
            String sql = "select * from Room";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String roomId = rs.getString("RoomId");
                String name = rs.getString("Name");
                int seatQuantity = rs.getInt("SeatQuantity");
                RoomStatus roomStatus = RoomStatus.valueOf(rs.getString("RoomStatus"));
                Room room = new Room(roomId, name, seatQuantity ,roomStatus);
                rooms.add(room);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rooms;
    }
    
    
    
    public List<Room> findByRoomStatus(String status){
        List<Room> rooms = new ArrayList<>();
        try{
            String sql = "select * from Room ";
            if(!status.equals("Tất cả")){
                sql += " where RoomStatus = ?";
            }
            PreparedStatement pst = con.prepareStatement(sql);
            if(!status.equals("Tất cả")){
                pst.setString(1, status.equals("Đang hoạt động") ? "ACTIVE" : "MAINTENANCE");
            }
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String roomId = rs.getString("RoomId");
                String name = rs.getString("Name");
                int seatQuantity = rs.getInt("SeatQuantity");
                RoomStatus roomStatus = RoomStatus.valueOf(rs.getString("RoomStatus"));
                Room room = new Room(roomId, name, seatQuantity ,roomStatus);
                rooms.add(room);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return rooms;
    }
    @Override
    public Room findById(String id) {
        try{
            String sql = "select * from Room where RoomId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String roomId = rs.getString("RoomId");
                String name = rs.getString("Name");
                int seatQuantity = rs.getInt("SeatQuantity");
                RoomStatus roomStatus = RoomStatus.valueOf(rs.getString("RoomStatus"));
                Room room = new Room(roomId, name, seatQuantity ,roomStatus);
                return room;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Room findByName(String name) {
        try{
            String sql = "select * from Room where Name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String roomId = rs.getString("RoomId");
                int seatQuantity = rs.getInt("SeatQuantity");
                RoomStatus roomStatus = RoomStatus.valueOf(rs.getString("RoomStatus"));
                Room room = new Room(roomId, name, seatQuantity ,roomStatus);
                return room;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Room room) {
        try{
            String sql = "insert into Room values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, room.getName());
            pst.setInt(3, room.getSeatQuantity());
            pst.setString(4, room.getRoomStatus().toString());
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public Room insertReturnRoom(Room room){
        try{
            String sql = "insert into Room values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            room.setRoomId(generateId());
            pst.setString(1, room.getRoomId());
            pst.setString(2, room.getName());
            pst.setInt(3, room.getSeatQuantity());
            pst.setString(4, room.getRoomStatus().toString());
            pst.executeUpdate();
            return room;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean update(Room room) {
        try{
            String sql = "update Room set Name = ?, SeatQuantity = ?, RoomStatus = ? where RoomId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, room.getName());
            pst.setInt(2, room.getSeatQuantity());
            pst.setString(3, room.getRoomStatus().toString());
            pst.setString(4, room.getRoomId());
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public String generateId() {
        String id = "";
        try{
            String sql = "select max(RoomId) from Room";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxRoomId = rs.getString(1);
            if(maxRoomId == null){
                return "P001";
            }
            else{
                int num = Integer.parseInt(maxRoomId.substring(1)) + 1;
                id = String.format("P%03d", num);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    
}
