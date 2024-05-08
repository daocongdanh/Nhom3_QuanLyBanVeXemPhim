/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Customer;
import connectDB.ConnectDB;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class CustomerDAL implements BaseDAL<Customer> {

    private Connection con;

    public CustomerDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "select * from Customer";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String customerId = rs.getString("CustomerId");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                Customer customer = new Customer(customerId, name, phone, email);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findById(String id) {
        try {
            String sql = "select * from Customer where CustomerId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String customerId = rs.getString("CustomerId");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                Customer customer = new Customer(customerId, name, phone, email);
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Customer, Double> findByKeyword(String keyword) {
        Map<Customer, Double> result = new LinkedHashMap<>();
        try {
            if (keyword.trim().equals("")) {
                return this.getTop10Customer(0, LocalDate.now().getYear());
            }
            String sql = "select * from Customer where Name like ? or Phone like ? or Email like ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            pst.setString(3, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String customerId = rs.getString("CustomerId");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                Customer customer = new Customer(customerId, name, phone, email);
                String sql2 = "select c.CustomerId, Total = sum(b.TotalPrice) from Customer c\n"
                        + "join Bill b on c.CustomerId = b.CustomerId\n"
                        + "where c.CustomerId = ?\n"
                        + "group by c.CustomerId";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                pst2.setString(1, customerId);
                ResultSet rs2 = pst2.executeQuery();
                if(rs2.next()){
                    result.put(customer, rs2.getDouble("Total"));
                }
                else{
                    result.put(customer, 0d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<Customer, Double> getTop10Customer(int month, int year) {
        Map<Customer, Double> result = new LinkedHashMap<>();
        try {
            String sql = "select top 10 c.CustomerId, Total = sum(b.TotalPrice) from Customer c \n"
                    + "join Bill b on c.CustomerId = b.CustomerId \n"
                    + "where YEAR(b.CreatedAt) = ? ";
            if (month != 0) {
                sql += "and MONTH(b.CreatedAt) = ? \n";
            }
            sql += "group by c.CustomerId \n"
                    + "order by sum(b.TotalPrice) desc ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            if (month != 0) {
                pst.setInt(2, month);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Customer customer = findById(rs.getString("CustomerId"));
                result.put(customer, rs.getDouble("Total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<Customer> getTop5Customer(int month, int year) {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "select top 5 c.CustomerId, Total = sum(b.TotalPrice) from Customer c \n"
                    + "join Bill b on c.CustomerId = b.CustomerId \n"
                    + "where YEAR(b.CreatedAt) = ? ";
            if (month != 0) {
                sql += "and MONTH(b.CreatedAt) = ? \n";
            }
            sql += "group by c.CustomerId \n"
                    + "order by sum(b.TotalPrice) desc ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            if (month != 0) {
                pst.setInt(2, month);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Customer customer = findById(rs.getString("CustomerId"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public Customer findByPhone(String phone) {
        try {
            String sql = "select * from Customer where Phone = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, phone);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String customerId = rs.getString("CustomerId");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                Customer customer = new Customer(customerId, name, phone, email);
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Customer customer) {
        try {
            String sql = "insert into Customer values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getPhone());
            pst.setString(4, customer.getEmail());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        try {
            String sql = "update Customer set Name = ?, Phone = ?, Email = ? where CustomerId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, customer.getName());
            pst.setString(2, customer.getPhone());
            pst.setString(3, customer.getEmail());
            pst.setString(4, customer.getCustomerId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete Customer where CustomerId = ?";
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
            String sql = "select max(CustomerId) from Customer";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxCustomerId = rs.getString(1);
            if (maxCustomerId == null) {
                return "KH001";
            } else {
                int num = Integer.parseInt(maxCustomerId.substring(2)) + 1;
                id = String.format("KH%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
