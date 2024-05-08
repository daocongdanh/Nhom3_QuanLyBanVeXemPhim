/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Movie;
import entity.Room;
import entity.Showtime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class ShowtimeDAL implements BaseDAL<Showtime> {

    private Connection con;

    public ShowtimeDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Showtime> findAll() {
        return null;
    }

    public List<Showtime> findAllByRoomAndMovie(String roomId, String movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        try {
            String sql = "select * from Showtime where RoomId = ? and MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, roomId);
            pst.setString(2, movieId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String showtimeId = rs.getString("ShowtimeId");
                double ticketPrice = rs.getDouble("TicketPrice");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                Room room = new Room(rs.getString("RoomId"));
                Movie movie = new Movie(rs.getString("MovieId"));
                Showtime showtime = new Showtime(showtimeId, ticketPrice, startTime, room, movie);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }
    
    public List<Showtime> findAllByRoomAndMovieAndTime(String roomId, LocalDate date) {
        List<Showtime> showtimes = new ArrayList<>();
        try {
            String sql = "select * from Showtime where RoomId = ? "
                    + "and FORMAT(?, 'yyyy-MM-dd') =  FORMAT(StartTime, 'yyyy-MM-dd')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, roomId);
            pst.setDate(2, Date.valueOf(date));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String showtimeId = rs.getString("ShowtimeId");
                double ticketPrice = rs.getDouble("TicketPrice");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                Room room = new Room(rs.getString("RoomId"));
                Movie movie = new Movie(rs.getString("MovieId"));
                Showtime showtime = new Showtime(showtimeId, ticketPrice, startTime, room, movie);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }
    public List<Showtime> findByMovie( String movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        try {
            String sql = "select * from Showtime where MovieId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, movieId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String showtimeId = rs.getString("ShowtimeId");
                double ticketPrice = rs.getDouble("TicketPrice");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                Room room = new Room(rs.getString("RoomId"));
                Movie movie = new Movie("MovieId");
                Showtime showtime = new Showtime(showtimeId, ticketPrice, startTime, room, movie);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }
    public List<Showtime> findByRoomAndTimeAndKeyword(String roomId, LocalDate date, String keyword) {
        List<Showtime> showtimes = new ArrayList<>();
        try {
            String sql = "select * from Showtime st \n"
                    + "join Movie m on st.MovieId = m.MovieId\n"
                    + "where st.RoomId = ? and FORMAT(?, 'yyyy-MM-dd') =  FORMAT(StartTime, 'yyyy-MM-dd') ";
            if (!keyword.trim().equals("")) {
                sql += "and m.Name like ?";
            }
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, roomId);
            pst.setDate(2, Date.valueOf(date));
            if (!keyword.trim().equals("")) {
                pst.setString(3, "%" + keyword + "%");
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String showtimeId = rs.getString("ShowtimeId");
                double ticketPrice = rs.getDouble("TicketPrice");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                Room room = new Room(rs.getString("RoomId"));
                Movie movie = new Movie(rs.getString("MovieId"));
                Showtime showtime = new Showtime(showtimeId, ticketPrice, startTime, room, movie);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    @Override
    public Showtime findById(String id) {
        try {
            String sql = "select * from Showtime where ShowtimeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String showtimeId = rs.getString("ShowtimeId");
                double ticketPrice = rs.getDouble("TicketPrice");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                Room room = new Room(rs.getString("RoomId"));
                Movie movie = new Movie(rs.getString("MovieId"));
                Showtime showtime = new Showtime(showtimeId, ticketPrice, startTime, room, movie);
                return showtime;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkShowTimeByPhong(String roomId) {
        try {
            String sql = "select count(*) from Showtime where RoomId = ? and StartTime >= GETDATE()";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, roomId);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Showtime> findByMovieAndTime(String movieId, LocalDate date) {
        List<Showtime> showtimes = new ArrayList<>();
        try {
            String sql = "select * from Showtime\n"
                    + "where MovieId = ? "
                    + "and FORMAT(?, 'yyyy-MM-dd') =  FORMAT(StartTime, 'yyyy-MM-dd')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, movieId);
            pst.setDate(2, Date.valueOf(date));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String showtimeId = rs.getString("ShowtimeId");
                double ticketPrice = rs.getDouble("TicketPrice");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                Room room = new Room(rs.getString("RoomId"));
                Movie movie = new Movie(rs.getString("MovieId"));
                Showtime showtime = new Showtime(showtimeId, ticketPrice, startTime, room, movie);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    @Override
    public boolean insert(Showtime showtime) {
        try {
            String sql = "insert into Showtime values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setDouble(2, showtime.getTicketPrice());
            pst.setTimestamp(3, Timestamp.valueOf(showtime.getStartTime()));
            pst.setString(4, showtime.getRoom().getRoomId());
            pst.setString(5, showtime.getMovie().getMovieId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Showtime showtime) {
        try {
            String sql = "update Showtime set TicketPrice = ?, StartTime = ? where ShowtimeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, showtime.getTicketPrice());
            pst.setTimestamp(2, Timestamp.valueOf(showtime.getStartTime()));
            pst.setString(3, showtime.getShowtimeId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete Showtime where ShowtimeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String generateId() {
        String id = "";
        try {
            String sql = "select max(ShowtimeId) from Showtime";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxShowtimeId = rs.getString(1);
            if (maxShowtimeId == null) {
                return "SC001";
            } else {
                int num = Integer.parseInt(maxShowtimeId.substring(2)) + 1;
                id = String.format("SC%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


}
