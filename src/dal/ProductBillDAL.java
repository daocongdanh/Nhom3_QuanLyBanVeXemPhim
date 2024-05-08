/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.ProductBill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class ProductBillDAL implements BaseDAL<ProductBill>{
    private Connection con;
    public ProductBillDAL(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<ProductBill> findAll() {
        return null;
    }

    @Override
    public ProductBill findById(String id) {
        return null;
    }

    @Override
    public boolean insert(ProductBill productBill) {
        try{
            String sql = "insert into ProductBill values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setInt(2, productBill.getQuantity());
            pst.setString(3, productBill.getProduct().getProductId());
            pst.setString(4, productBill.getBill().getBillId());
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(ProductBill t) {
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
            String sql = "select max(ProductBillId) from ProductBill";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxProductBillId = rs.getString(1);
            if(maxProductBillId == null){
                return "PB001";
            }
            else{
                int num = Integer.parseInt(maxProductBillId.substring(2)) + 1;
                id = String.format("PB%03d", num);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    public LinkedHashMap<String, Integer> getSoLuongNhieuNhatTheoNam( int year ) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        try {
            String sql = "select top 5 p.ProductId, SUM ( pb.Quantity ) as soLuong\n"
                    + "from Bill b \n"
                    + "join ProductBill pb on b.BillId = pb.BillId\n"
                    + "join Product p on p.ProductId = pb.ProductId\n"
                    + "where YEAR(CreatedAt) = ?\n"
                    + "group by p.ProductId\n"
                    + "order by soLuong desc";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String name = rs.getString("ProductID");
                int soLuong = rs.getInt("soLuong");
                result.put(name, soLuong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
     public LinkedHashMap<String, Integer> getSoLuongNhieuNhatTheoThang( int month, int year ) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        try {
            String sql = "select top 5 p.ProductId, SUM ( pb.Quantity ) as soLuong\n"
                    + "from Bill b \n"
                    + "join ProductBill pb on b.BillId = pb.BillId\n"
                    + "join Product p on p.ProductId = pb.ProductId\n"
                    + "where YEAR(CreatedAt) = ? and MONTH(CreatedAt) = ?\n"
                    + "group by p.ProductId\n"
                    + "order by soLuong desc";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            pst.setInt(2, month);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String name = rs.getString("ProductID");
                int soLuong = rs.getInt("soLuong");
                result.put(name, soLuong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
