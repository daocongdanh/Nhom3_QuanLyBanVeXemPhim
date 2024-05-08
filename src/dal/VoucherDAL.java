/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Voucher;
import entity.VoucherRelease;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class VoucherDAL implements BaseDAL<Voucher>{
    private Connection con;
    public VoucherDAL(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }
    
    public Voucher findByCode(String code){
        try{
            String sql = "select * from Voucher where code = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, code);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String voucherId = rs.getString("VoucherId");
                boolean status = rs.getBoolean("Status");
                LocalDateTime usedAt = rs.getTimestamp("UsedAt") == null ?
                        null : rs.getTimestamp("UsedAt").toLocalDateTime();
                LocalDateTime releaseAt = rs.getTimestamp("ReleaseAt") == null ? 
                        null : rs.getTimestamp("ReleaseAt").toLocalDateTime();
                VoucherRelease voucherRelease = new VoucherRelease(rs.getString("VoucherReleaseId"));
                Voucher voucher = new Voucher(voucherId, code, status, usedAt, releaseAt, voucherRelease);
                return voucher;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean update(Voucher voucher){
        try{
            String sql = "update Voucher set Status = ?, UsedAt = ?, ReleaseAt = ? where VoucherId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setBoolean(1, voucher.isStatus());
            pst.setTimestamp(2, voucher.getUsedAt() != null ? Timestamp.valueOf(voucher.getUsedAt()) : null);
            pst.setTimestamp(3, voucher.getReleaseAt() != null ? Timestamp.valueOf(voucher.getReleaseAt()) : null);
            pst.setString(4, voucher.getVoucherId());
            return pst.executeUpdate() > 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public List<Voucher> findByVoucherReleaseAndStatus(String voucherReleaseId, boolean status){
        List<Voucher> vouchers = new ArrayList<>();
        try{
            String sql = "select * from Voucher where voucherReleaseId = ? and Status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, voucherReleaseId);
            pst.setInt(2, status ==true ? 1 : 0);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String voucherId = rs.getString("VoucherId");
                String code  = rs.getString("Code");
                LocalDateTime usedAt = rs.getTimestamp("UsedAt") == null ?
                        null : rs.getTimestamp("UsedAt").toLocalDateTime();
                LocalDateTime releaseAt = rs.getTimestamp("ReleaseAt") == null ? 
                        null : rs.getTimestamp("ReleaseAt").toLocalDateTime();
                VoucherRelease voucherRelease = new VoucherRelease(rs.getString("VoucherReleaseId"));
                Voucher voucher = new Voucher(voucherId, code, status, usedAt, releaseAt, voucherRelease);
                vouchers.add(voucher);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return vouchers;
    }
    public List<Voucher> findByVoucherReleaseAndReleaseAtIsNull(String voucherReleaseId){
        List<Voucher> vouchers = new ArrayList<>();
        try{
            String sql = "select * from Voucher where voucherReleaseId = ? and ReleaseAt is null";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, voucherReleaseId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String voucherId = rs.getString("VoucherId");
                String code  = rs.getString("Code");
                boolean status = rs.getBoolean("Status");
                LocalDateTime usedAt = rs.getTimestamp("UsedAt") == null ?
                        null : rs.getTimestamp("UsedAt").toLocalDateTime();
                LocalDateTime releaseAt = rs.getTimestamp("ReleaseAt") == null ? 
                        null : rs.getTimestamp("ReleaseAt").toLocalDateTime();
                VoucherRelease voucherRelease = new VoucherRelease(rs.getString("VoucherReleaseId"));
                Voucher voucher = new Voucher(voucherId, code, status, usedAt, releaseAt, voucherRelease);
                vouchers.add(voucher);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return vouchers;
    }
    @Override
    public List<Voucher> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Voucher findById(String id) {
        try{
            String sql = "select * from Voucher where VoucherId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String voucherId = rs.getString("VoucherId");
                String code = rs.getString("Code");
                boolean status = rs.getBoolean("Status");
                LocalDateTime usedAt = rs.getTimestamp("UsedAt") == null ?
                        null : rs.getTimestamp("UsedAt").toLocalDateTime();
                LocalDateTime releaseAt = rs.getTimestamp("ReleaseAt") == null ? 
                        null : rs.getTimestamp("ReleaseAt").toLocalDateTime();
                VoucherRelease voucherRelease = new VoucherRelease(rs.getString("VoucherReleaseId"));
                Voucher voucher = new Voucher(voucherId, code, status, usedAt, releaseAt, voucherRelease);
                return voucher;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Voucher voucher) {
        try{
            String sql = "insert into Voucher values (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, voucher.getCode());
            pst.setBoolean(3, voucher.isStatus());
            pst.setTimestamp(4, voucher.getUsedAt() == null ? null :Timestamp.valueOf(voucher.getUsedAt()));
            pst.setTimestamp(5, voucher.getReleaseAt() == null ? null :Timestamp.valueOf(voucher.getReleaseAt()));
            pst.setString(6, voucher.getVoucherRelease().getVoucherReleaseId());
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
            String sql = "delete Voucher where VoucherId = ?";
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
            String sql = "select max(VoucherId) from Voucher";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxVoucherId = rs.getString(1);
            if(maxVoucherId == null){
                return "KM0001";
            }
            else{
                int num = Integer.parseInt(maxVoucherId.substring(3)) + 1;
                id = String.format("KM%04d", num);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }
}
