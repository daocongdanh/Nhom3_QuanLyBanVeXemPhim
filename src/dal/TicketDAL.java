/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Bill;
import entity.Seat;
import entity.Showtime;
import entity.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class TicketDAL implements BaseDAL<Ticket> {

    private Connection con;

    public TicketDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public Ticket findById(String id) {
        try {
            String sql = "select * from Ticket where TicketId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String ticketId = rs.getString("TicketId");
                Bill bill = new Bill(rs.getString("BillId"));
                Showtime showtime = new Showtime(rs.getString("ShowtimeId"));
                Seat seat = new Seat(rs.getString("SeatId"));
                Ticket ticket = new Ticket(ticketId, bill, showtime, seat);
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> findByShowtime(String showtimeId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String sql = "select * from Ticket where ShowtimeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, showtimeId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String ticketId = rs.getString("TicketId");
                Bill bill = new Bill(rs.getString("BillId"));
                Showtime showtime = new Showtime(rs.getString("ShowtimeId"));
                Seat seat = new Seat(rs.getString("SeatId"));
                Ticket ticket = new Ticket(ticketId, bill, showtime, seat);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> getQuantityTicketByYearAndShowtime(int year, String showtimeId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String sql = "select t.TicketId, t.BillId, t.ShowtimeId, t.SeatId from Bill b\n"
                    + "join Ticket t on b.BillId = t.BillId\n"
                    + "where YEAR(b.CreatedAt) = ? and t.ShowtimeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            pst.setString(2, showtimeId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String ticketId = rs.getString("TicketId");
                Bill bill = new Bill(rs.getString("BillId"));
                Showtime showtime = new Showtime(rs.getString("ShowtimeId"));
                Seat seat = new Seat(rs.getString("SeatId"));
                Ticket ticket = new Ticket(ticketId, bill, showtime, seat);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public List<Ticket> getQuantityTicketByMonthAndYearAndShowtime(int month, int year, String showtimeId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String sql = "select t.TicketId, t.BillId, t.ShowtimeId, t.SeatId from Bill b\n"
                    + "join Ticket t on b.BillId = t.BillId\n"
                    + "where MONTH(b.CreatedAt) = ? and YEAR(b.CreatedAt) = ? and t.ShowtimeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, month);
            pst.setInt(2, year);
            pst.setString(3, showtimeId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String ticketId = rs.getString("TicketId");
                Bill bill = new Bill(rs.getString("BillId"));
                Showtime showtime = new Showtime(rs.getString("ShowtimeId"));
                Seat seat = new Seat(rs.getString("SeatId"));
                Ticket ticket = new Ticket(ticketId, bill, showtime, seat);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    @Override
    public boolean insert(Ticket ticket) {
        try {
            String sql = "insert into Ticket values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, ticket.getBill().getBillId());
            pst.setString(3, ticket.getShowtime().getShowtimeId());
            pst.setString(4, ticket.getSeat().getSeatId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Ticket t) {
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
            String sql = "select max(TicketId) from Ticket";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxTicketId = rs.getString(1);
            if (maxTicketId == null) {
                return "VE001";
            } else {
                int num = Integer.parseInt(maxTicketId.substring(2)) + 1;
                id = String.format("VE%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public LinkedHashMap<String, Integer> getSoLuongNhieuNhat() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        try {
            String sql = "select\n"
                    + "	m.Name,\n"
                    + "    count(1) as soVe\n"
                    + "from\n"
                    + "    Bill b\n"
                    + "    JOIN Ticket t ON b.BillId = t.BillId\n"
                    + "    JOIN Showtime st ON t.ShowtimeId = st.ShowtimeId\n"
                    + "    JOIN Seat s ON s.SeatId = t.SeatId\n"
                    + "	JOIN Movie m ON m.MovieId = st.MovieId\n"
                    + "group by m.Name";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("Name");
                int soVe = rs.getInt("soVe");
                result.put(name, soVe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
