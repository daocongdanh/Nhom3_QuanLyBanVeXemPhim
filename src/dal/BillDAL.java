/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import connectDB.ConnectDB;
import entity.Bill;
import entity.Customer;
import entity.Staff;
import entity.Voucher;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class BillDAL {

    private Connection con;

    public BillDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    public Bill insert(Bill bill) {
        try {
            String id = generateId();
            String sql = "insert into Bill values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            pst.setDate(2, Date.valueOf(bill.getCreatedAt()));
            pst.setDouble(3, bill.getTotalPrice());
            pst.setString(4, bill.getCustomer().getCustomerId());
            pst.setString(5, bill.getStaff().getStaffId());
            pst.setString(6, bill.getVoucher() == null ? null : bill.getVoucher().getVoucherId());
            if (pst.executeUpdate() > 0) {
                bill.setBillId(id);
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Bill, Double> findByDate(LocalDate start, LocalDate end) {
        Map<Bill, Double> bills = new LinkedHashMap<>();
        try {
            String sql = "select * from Bill where CreatedAt between ? and ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(start));
            pst.setDate(2, Date.valueOf(end));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("BillId"));
                bill.setCreatedAt(rs.getDate("CreatedAt").toLocalDate());
                bill.setCustomer(new Customer(rs.getString("CustomerId")));
                bill.setStaff(new Staff(rs.getString("StaffId")));
                bills.put(bill, rs.getDouble("TotalPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public String generateId() {
        String id = "";
        try {
            String sql = "select max(BillId) from Bill";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxBillId = rs.getString(1);
            if (maxBillId == null) {
                return "HD001";
            } else {
                int num = Integer.parseInt(maxBillId.substring(2)) + 1;
                id = String.format("HD%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public double getDiscountByMonthAndYear(int month, int year) {
        try {
            String sql = "select Total = sum(vc.Price) from Bill b\n"
                    + "join Voucher v on b.VoucherId = v.VoucherId\n"
                    + "join VoucherRelease vc on vc.VoucherReleaseId = v.VoucherReleaseId\n"
                    + "where YEAR(b.CreatedAt) = ? ";
            if(month != 0){
                sql += " and MONTH(b.CreatedAt) = ?";
            }
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            if(month != 0){
                pst.setInt(2, month);
            }
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getDouble("Total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double findTotalPriceById(String id) {
        try {
            String sql = "select TotalPrice from Bill where BillId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getDouble("TotalPrice");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Bill> getBillByYear(int year) {
        List<Bill> billList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Bill WHERE YEAR(CreatedAt) = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("BillId"));
                bill.setCreatedAt(rs.getDate("CreatedAt").toLocalDate());
                Customer customer = new Customer(rs.getString("CustomerId"));
                Staff staff = new Staff(rs.getString("StaffId"));
                Voucher voucher = new Voucher(rs.getString("voucherId"));
                bill.setCustomer(customer);
                bill.setStaff(staff);
                bill.setVoucher(voucher);

                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }

    public Map<Integer, Double> getBillByMonthInYear(int year) {
        Map<Integer, Double> billCountByYear = new HashMap<>();
        try {
            String sql = "SELECT MONTH(CreatedAt) AS month, SUM(TotalPrice) AS count \n"
                    + "FROM Bill \n"
                    + "where YEAR(CreatedAt) = ?\n"
                    + "GROUP BY MONTH(CreatedAt)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                double count = rs.getDouble("count");
                billCountByYear.put(month, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billCountByYear;
    }

    public Map<Integer, Double> getBillByDayInMonth(int month, int year) {
        Map<Integer, Double> billCountByMonth = new HashMap<>();
        try {
            String sql = "SELECT DAY(CreatedAt) AS day, SUM(TotalPrice) AS count \n"
                    + "FROM Bill\n"
                    + "where YEAR(CreatedAt) = ?\n"
                    + "and MONTH( CreatedAt ) = ?\n"
                    + "GROUP BY DAY(CreatedAt)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            pst.setInt(2, month);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int day = rs.getInt("day");
                double count = rs.getDouble("count");
                billCountByMonth.put(day, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billCountByMonth;
    }

    public int getQuantityBillInYear(int year) {
        try {
            String sql = "select COUNT(*) as tongSo\n"
                    + "from Bill\n"
                    + "where YEAR( CreatedAt ) = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int soLuong = rs.getInt("tongSo");
                return soLuong;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getQuantityBillInMonthAndYear(int month, int year) {
        try {
            String sql = "select COUNT(*) as tongSo\n"
                    + "from Bill\n"
                    + "where YEAR( CreatedAt ) = ?\n"
                    + "and MONTH( CreatedAt ) = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            pst.setInt(2, month);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int soLuong = rs.getInt("tongSo");
                return soLuong;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Integer> getBillMaxMonthInYear(int year) {
        Map<String, Integer> billMaxByYear = new HashMap<>();
        try {
            String sql = "select TOP 1 MONTH ( CreatedAt) as thang , COUNT(*) as tongHoaDon\n"
                    + "from Bill\n"
                    + "where YEAR( CreatedAt ) = ?\n"
                    + "group by MONTH( CreatedAt)\n"
                    + "order by tongHoaDon desc";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String month = rs.getString("thang");
                int tien = rs.getInt("tongHoaDon");
                billMaxByYear.put(month, tien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billMaxByYear;
    }

    public Map<String, Integer> getBillMaxDayInMonth(int month, int year) {
        Map<String, Integer> billMaxInMonth = new HashMap<>();
        try {
            String sql = "select TOP 1 DAY ( CreatedAt) as ngay , COUNT(*) as tongHoaDon\n"
                    + "from Bill\n"
                    + "where YEAR( CreatedAt ) = ?\n"
                    + "and MONTH (CreatedAt ) = ?\n"
                    + "group by DAY( CreatedAt)\n"
                    + "order by tongHoaDon desc";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, year);
            pst.setInt(2, month);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String day = rs.getString("ngay");
                int tien = rs.getInt("tongHoaDon");
                billMaxInMonth.put(day, tien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billMaxInMonth;
    }

}
