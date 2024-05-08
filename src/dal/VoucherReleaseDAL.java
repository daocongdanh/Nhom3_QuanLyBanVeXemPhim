/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Room;
import entity.VoucherRelease;
import enums.ObjectType;
import enums.RoomStatus;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class VoucherReleaseDAL implements BaseDAL<VoucherRelease> {

    private Connection con;

    public VoucherReleaseDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<VoucherRelease> findAll() {
        List<VoucherRelease> voucherReleases = new ArrayList<>();
        try {
            String sql = "select * from VoucherRelease";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String voucherReleaseId = rs.getString("VoucherReleaseId");
                String releaseName = rs.getString("ReleaseName");
                LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                double price = rs.getDouble("Price");
                double minPrice = rs.getDouble("MinPrice");
                ObjectType objectType = ObjectType.valueOf(rs.getString("ObjectType"));
                VoucherRelease voucherRelease = new VoucherRelease(voucherReleaseId, releaseName, startDate, endDate, price, minPrice, objectType);
                voucherReleases.add(voucherRelease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherReleases;
    }

    @Override
    public VoucherRelease findById(String id) {
        try {
            String sql = "select * from VoucherRelease where VoucherReleaseId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String voucherReleaseId = rs.getString("VoucherReleaseId");
                String releaseName = rs.getString("releaseName");
                LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                double price = rs.getDouble("Price");
                double minPrice = rs.getDouble("MinPrice");
                ObjectType objectType = ObjectType.valueOf(rs.getString("objectType"));
                VoucherRelease voucherRelease = new VoucherRelease(voucherReleaseId, releaseName, startDate,
                         endDate, price, minPrice, objectType);
                return voucherRelease;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(VoucherRelease voucherRelease) {
        try {
            String sql = "insert into VoucherRelease values (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, voucherRelease.getReleaseName());
            pst.setDate(3, Date.valueOf(voucherRelease.getStartDate()));
            pst.setDate(4, Date.valueOf(voucherRelease.getEndDate()));
            pst.setDouble(5, voucherRelease.getPrice());
            pst.setDouble(6, voucherRelease.getMinPrice());
            pst.setString(7, voucherRelease.getObjectType().toString());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkModify(String id) {
        try {
            String sql = "select * from VoucherRelease vc\n"
                    + "join Voucher v on vc.VoucherReleaseId = v.VoucherReleaseId\n"
                    + "where vc.VoucherReleaseId = ? and (v.ReleaseAt is not null or v.UsedAt is not null)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<VoucherRelease> findByKeyword(String keyword) {
        List<VoucherRelease> voucherReleases = new ArrayList<>();
        try {
            String sql = "select * from VoucherRelease";
            if (!keyword.trim().equals("")) {
                sql += "  where ReleaseName like ?";
            }
            PreparedStatement pst = con.prepareStatement(sql);
            if (!keyword.trim().equals("")) {
                pst.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String voucherReleaseId = rs.getString("VoucherReleaseId");
                String releaseName = rs.getString("ReleaseName");
                LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                double price = rs.getDouble("Price");
                double minPrice = rs.getDouble("MinPrice");
                ObjectType objectType = ObjectType.valueOf(rs.getString("ObjectType"));
                VoucherRelease voucherRelease = new VoucherRelease(voucherReleaseId, releaseName, startDate, endDate, price, minPrice, objectType);
                voucherReleases.add(voucherRelease);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherReleases;
    }

    @Override
    public boolean update(VoucherRelease voucherRelease) {
        try {
            String sql = "update VoucherRelease set ReleaseName = ?, StartDate = ?, EndDate = ?, Price = ?, MinPrice = ?, ObjectType = ? "
                    + " where VoucherReleaseId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, voucherRelease.getReleaseName());
            pst.setDate(2, Date.valueOf(voucherRelease.getStartDate()));
            pst.setDate(3, Date.valueOf(voucherRelease.getEndDate()));
            pst.setDouble(4, voucherRelease.getPrice());
            pst.setDouble(5, voucherRelease.getMinPrice());
            pst.setString(6, voucherRelease.getObjectType().toString());
            pst.setString(7, voucherRelease.getVoucherReleaseId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete VoucherRelease where VoucherReleaseId = ?";
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
            String sql = "select max(VoucherReleaseId) from VoucherRelease";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxVoucherReleaseId = rs.getString(1);
            if (maxVoucherReleaseId == null) {
                return "DPH001";
            } else {
                int num = Integer.parseInt(maxVoucherReleaseId.substring(3)) + 1;
                id = String.format("DPH%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
