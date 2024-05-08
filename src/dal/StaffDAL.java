/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.SQLException;
import connectDB.ConnectDB;
import entity.Staff;
import enums.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class StaffDAL implements BaseDAL<Staff>{
    private Connection con;
    public StaffDAL(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }
    @Override
    public List<Staff> findAll() {
        List<Staff> staffs = new ArrayList<>();
        try{
            String sql = "select * from Staff";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String staffId = rs.getString("StaffId");
                String name = rs.getString("Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                boolean gender = rs.getBoolean("Gender");
                LocalDate startingDate = rs.getDate("StartingDate").toLocalDate();
                Role role = Role.valueOf(rs.getString("Role"));
                String email = rs.getString("Email");
                Staff staff = new Staff(staffId, name, username, password, phone, gender, startingDate, role, email);
                staffs.add(staff);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return staffs;
    }

    @Override
    public Staff findById(String id) {
        try{
            String sql = "select * from Staff where StaffId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String staffId = rs.getString("StaffId");
                String name = rs.getString("Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                boolean gender = rs.getBoolean("Gender");
                LocalDate startingDate = rs.getDate("StartingDate").toLocalDate();
                Role role = Role.valueOf(rs.getString("Role"));
                String email = rs.getString("Email");
                Staff staff = new Staff(staffId, name, username, password, phone, gender, startingDate, role, email);
                return staff;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Staff staff) {
        try{
            String sql = "insert into Staff values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, staff.getName());
            pst.setString(3, staff.getUsername());
            pst.setString(4, staff.getPassword());
            pst.setString(5, staff.getPhone());
            pst.setBoolean(6, staff.isGender());
            pst.setDate(7, Date.valueOf(staff.getStartingDate()));
            pst.setString(8, staff.getEmail());
            pst.setString(9, staff.getRole().toString());
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            
        }
        return false;
    }

    @Override
    public boolean update(Staff staff) {
        try{
            String sql = "update Staff set Name = ?, UserName = ?, Password = ?, Phone = ? "
                    + ", Gender = ?, StartingDate = ?, Role = ?, Email = ? where StaffId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, staff.getName());
            pst.setString(2, staff.getUsername());
            pst.setString(3, staff.getPassword());
            pst.setString(4, staff.getPhone());
            pst.setBoolean(5, staff.isGender());
            pst.setDate(6, Date.valueOf(staff.getStartingDate()));
            pst.setString(7, staff.getRole().toString());
            pst.setString(8, staff.getEmail());
            pst.setString(9, staff.getStaffId());
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try{
            String sql = "delete Staff where StaffId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String generateId() {
        String id = "";
        try{
            String sql = "select max(StaffId) from Staff";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxStaffId = rs.getString(1);
            if(maxStaffId == null){
                return "NV001";
            }
            else{
                int num = Integer.parseInt(maxStaffId.substring(2)) + 1;
                id = String.format("NV%03d", num);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    public Staff findByUsernameAndPassword(String username, String password){
        try{
            String sql = "select * from Staff where Username = ? and Password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String staffId = rs.getString("StaffId");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                boolean gender = rs.getBoolean("Gender");
                LocalDate startingDate = rs.getDate("StartingDate").toLocalDate();
                Role role = Role.valueOf(rs.getString("Role"));
                String email = rs.getString("Email");
                Staff staff = new Staff(staffId, name, username, password, phone, gender, startingDate, role, email);
                return staff;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Staff findByUsername(String username){
         try{
            String sql = "select * from Staff where Username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String staffId = rs.getString("StaffId");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                boolean gender = rs.getBoolean("Gender");
                LocalDate startingDate = rs.getDate("StartingDate").toLocalDate();
                Role role = Role.valueOf(rs.getString("Role"));
                Staff staff = new Staff(staffId, name, username, password, phone, gender, startingDate, role, email);
                return staff;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Staff findByUsernameAndEmail(String username, String email){
        try{
            String sql = "select * from Staff where Username = ? and Email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String staffId = rs.getString("StaffId");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                boolean gender = rs.getBoolean("Gender");
                LocalDate startingDate = rs.getDate("StartingDate").toLocalDate();
                Role role = Role.valueOf(rs.getString("Role"));
                Staff staff = new Staff(staffId, name, username, password, phone, gender, startingDate, role, email);
                return staff;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Map<Staff, Double> getTop10Staff(int month, int year) {
        Map<Staff, Double> result = new LinkedHashMap<>();
        try {
            String sql = "select top 10 s.StaffId, Total = sum(b.TotalPrice) from Staff s \n"
                    + "join Bill b on s.StaffId = b.StaffId \n"
                    + "where Role = 'STAFF' and YEAR(b.CreatedAt) = ? ";
            if (month != 0) {
                sql += "and MONTH(b.CreatedAt) = ? \n";
            }
            sql += "group by s.StaffId \n"
                    + "order by sum(b.TotalPrice) desc ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            if (month != 0) {
                pst.setInt(2, month);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Staff staff = findById(rs.getString("StaffId"));
                result.put(staff, rs.getDouble("Total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<Staff, Double> findByKeyword(String keyword) {
        Map<Staff, Double> result = new LinkedHashMap<>();
        try {
            if (keyword.trim().equals("")) {
                return this.getTop10Staff(0, LocalDate.now().getYear());
            }
            String sql = "select * from Staff where (Name like ? or Phone like ? or Email like ?) and Role = 'STAFF'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            pst.setString(3, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String staffId = rs.getString("StaffId");
                String name = rs.getString("Name");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                boolean gender = rs.getBoolean("Gender");
                LocalDate startingDate = rs.getDate("StartingDate").toLocalDate();
                Role role = Role.valueOf(rs.getString("Role"));
                String email = rs.getString("Email");
                Staff staff = new Staff(staffId, name, username, password, phone, gender, startingDate, role, email);
                String sql2 = "select s.StaffId, Total = sum(b.TotalPrice) from Staff s\n"
                        + "join Bill b on s.StaffId = b.StaffId\n"
                        + "where s.StaffId = ?\n"
                        + "group by s.StaffId";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                pst2.setString(1, staffId);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    result.put(staff, rs2.getDouble("Total"));
                }
                else{
                    result.put(staff, 0d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
