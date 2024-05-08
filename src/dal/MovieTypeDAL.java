/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import java.util.List;
import entity.MovieType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author daoducdanh
 */
public class MovieTypeDAL implements BaseDAL<MovieType>{
    private Connection con;
    public MovieTypeDAL(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }
    public List<MovieType> findAll(){
        List<MovieType> movieTypes = new ArrayList<>();
        try{
            String sql = "select * from MovieType";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String movieTypeId = rs.getString("MovieTypeId");
                String name = rs.getString("Name");
                MovieType movieType = new MovieType(movieTypeId,name);
                movieTypes.add(movieType);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return movieTypes;
    }

    @Override
    public MovieType findById(String id) {
        try{
            String sql = "select * from MovieType where MovieTypeId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String movieTypeId = rs.getString("MovieTypeId");
                String name = rs.getString("Name");
                MovieType movieType = new MovieType(movieTypeId,name);
                return movieType;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public MovieType findByName(String name) {
        try{
            String sql = "select * from MovieType where Name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String movieTypeId = rs.getString("MovieTypeId");
                MovieType movieType = new MovieType(movieTypeId,name);
                return movieType;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(MovieType t) {
        return true;
    }

    @Override
    public boolean update(MovieType t) {
        return true;
    }

    @Override
    public boolean delete(String id) {
        return true;
    }

    @Override
    public String generateId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
