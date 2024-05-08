/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Room;
import entity.Seat;
import enums.SeatType;
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
public class SeatDAL implements BaseDAL<Seat> {

    private Connection con;

    public SeatDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Seat> findAll() {
        return null;
    }

    @Override
    public Seat findById(String id) {
        try {
            String sql = "select * from Seat where SeatId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String seatId = rs.getString("SeatId");
                int seatNumber = rs.getInt("SeatNumber");
                String row = rs.getString("Row");
                SeatType seatType = SeatType.valueOf(rs.getString("SeatType"));
                Room room = new Room(rs.getString("RoomId"));
                Seat seat = new Seat(seatId, seatNumber, row, seatType, room);
                return seat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Seat> findByRoom(String roomId) {
        List<Seat> seats = new ArrayList<>();
        try {
            String sql = "select * from Seat where RoomId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, roomId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String seatId = rs.getString("SeatId");
                int seatNumber = rs.getInt("SeatNumber");
                String row = rs.getString("Row");
                SeatType seatType = SeatType.valueOf(rs.getString("SeatType"));
                Room room = new Room(rs.getString("RoomId"));
                Seat seat = new Seat(seatId, seatNumber, row, seatType, room);
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public int countByRoom(String roomId) {
        try {
            String sql = "select count(*) from Seat where RoomId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, roomId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean insert(Seat seat) {
        try {
            String sql = "insert into Seat values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setInt(2, seat.getSeatNumber());
            pst.setString(3, seat.getRow());
            pst.setString(4, seat.getSeatType().toString());
            pst.setString(5, seat.getRoom().getRoomId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Seat seat) {
        try {
            String sql = "update Seat set SeatType = ? where SeatId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, seat.getSeatType().toString());
            pst.setString(2, seat.getSeatId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
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
        try {
            String sql = "select max(SeatId) from Seat";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxSeatId = rs.getString(1);
            if (maxSeatId == null) {
                return "G0001";
            } else {
                int num = Integer.parseInt(maxSeatId.substring(1)) + 1;
                id = String.format("G%04d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
