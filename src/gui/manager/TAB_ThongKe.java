/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui.manager;

/**
 *
 * @author Hoang
 */
import gui.custom.ChartPanel;
import gui.custom.CustomComboBoxUI;
import gui.custom.CustomTabbedPaneUI;
import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import connectDB.ConnectDB;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.Product;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import entity.Movie;
import bus.StatisticBUS;
import entity.Bill;
import gui.custom.CustomTabbedPaneBottomUI;
import gui.custom.LineChart;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import javax.swing.border.Border;

public class TAB_ThongKe extends javax.swing.JPanel {

    private StatisticBUS statisticBUS;
    private DefaultTableModel modelTableSp;
    private DefaultTableModel modelTablePhim;
    private DecimalFormat decimal = new DecimalFormat("#,### đ");
    private DecimalFormat decimalTiLe = new DecimalFormat("###,##%");
    
    /**
     * Creates new form TAB_ThongKe
     */
    public TAB_ThongKe() {
        statisticBUS = new StatisticBUS();
        initComponents();
        if (tieuChiTK.getSelectedIndex() == 0) {
            chonThongKee.setSelectedItem(LocalDate.now().getYear() + "");
            chonThongKe.setSelectedItem(LocalDate.now().getYear() + "");
        } else {
            chonThongKee.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
            chonThongKe.setSelectedItem(LocalDate.now().getYear() + "");
        }
    }
    
    private void fillThongKeSanPhamTheoNam(int year) {
        chartThongKeSanPham.removeAll();
        modelTableSp.setRowCount(0);
        ChartPanel chartSp = new ChartPanel();
        Map<Product, Map<Integer, Double>> thongKe = statisticBUS.thongKeSanPhamTheoNam(year);
        int i = 1;
        for (Map.Entry<Product, Map<Integer, Double>> entry : thongKe.entrySet()) {
            Map<Integer, Double> map = entry.getValue();
            Object tongTien = map.values().toArray()[0];
            Object soLuong = map.keySet().toArray()[0];
            modelTableSp.addRow(new Object[]{i++, entry.getKey().getName(), soLuong, decimal.format(tongTien)});
            chartSp.addHistogramColumn((String) entry.getKey().getName(), (int) soLuong, new Color(96, 179, 206));
        }
        chartSp.layoutHistogramWithAnimation();
        chartThongKeSanPham.add(chartSp, BorderLayout.CENTER);
    }

    private void fillThongKeSanPhamTheoThang(int month, int year) {
        chartThongKeSanPham.removeAll();
        modelTableSp.setRowCount(0);
        ChartPanel chartSp = new ChartPanel();
        Map<Product, Map<Integer, Double>> thongKe = statisticBUS.thongKeSanPhamTheoThang(month, year);
        int i = 1;
        for (Map.Entry<Product, Map<Integer, Double>> entry : thongKe.entrySet()) {
            Map<Integer, Double> map = entry.getValue();
            Object tongTien = map.values().toArray()[0];
            Object soLuong = map.keySet().toArray()[0];
            modelTableSp.addRow(new Object[]{i++, entry.getKey().getName(), soLuong, decimal.format(tongTien)});
            chartSp.addHistogramColumn((String) entry.getKey().getName(), (int) soLuong, new Color(96, 179, 206));
        }
        chartSp.layoutHistogramWithAnimation();
        chartThongKeSanPham.add(chartSp, BorderLayout.CENTER);
    }

    private void fillThongKePhimTheoNam(int year) {
        chartThongKePhim.removeAll();
        modelTablePhim.setRowCount(0);
        ChartPanel chartPhim = new ChartPanel();
        Map<Movie, Map<Integer, Double>> thongKe = statisticBUS.thongKePhimTheoNam(year);
        int count = 0;
        for (Map.Entry<Movie, Map<Integer, Double>> entry : thongKe.entrySet()) {
            Map<Integer, Double> map = entry.getValue();
            Object tongTien = map.values().toArray()[0];
            Object soVe = map.keySet().toArray()[0];
            modelTablePhim.addRow(new Object[]{++count, entry.getKey().getName(), soVe, decimal.format(tongTien)});
            chartPhim.addHistogramColumn(entry.getKey().getName(), (int) soVe, new Color(96, 179, 206));
        }

        chartPhim.layoutHistogramWithAnimation();
        chartThongKePhim.add(chartPhim, BorderLayout.CENTER);

    }

    private void fillThongKePhimTheoThang(int month, int year) {
        chartThongKePhim.removeAll();
        modelTablePhim.setRowCount(0);
        ChartPanel chartPhim = new ChartPanel();
        Map<Movie, Map<Integer, Double>> thongKe = statisticBUS.thongKePhimTheoThang(month, year);
        int count = 0;
        for (Map.Entry<Movie, Map<Integer, Double>> entry : thongKe.entrySet()) {
            Map<Integer, Double> map = entry.getValue();
            Object tongTien = map.values().toArray()[0];
            Object soVe = map.keySet().toArray()[0];
            modelTablePhim.addRow(new Object[]{++count, entry.getKey().getName(), soVe, decimal.format(tongTien)});
            chartPhim.addHistogramColumn(entry.getKey().getName(), (int) soVe, new Color(96, 179, 206));
        }

        chartPhim.layoutHistogramWithAnimation();
        chartThongKePhim.add(chartPhim, BorderLayout.CENTER);

    }

    private void fillThongKeDoanhThuTheoNam(int year) {
        chartThongKeDoanhThu.removeAll();
        LineChart chartDoanhThu = new LineChart();

        Map<Integer, Double> doanhThuTk = statisticBUS.getBillByMonthInYear(year);
        for (int i = 1; i <= 12; i++) {
            int monthRevenue = (int) (doanhThuTk.containsKey(i) ? (doanhThuTk.get(i)) : 0);
            chartDoanhThu.addDataPoint(getMonthName(i), monthRevenue);
        }
        chartThongKeDoanhThu.add(chartDoanhThu, BorderLayout.CENTER);

        int soHoaDon = statisticBUS.getQuantityBillInYear(year);
        setTextTK("Theo Năm", year + "", soHoaDon + "", decimal.format(statisticBUS.getTotalByYear(year)));

        txtNgayNam.setText("Tháng có nhiều hóa đơn nhất");
        Map<String, Integer> thangTotNhat = statisticBUS.getBillMaxMonthInYear(year);
        if (!thangTotNhat.isEmpty()) {
            Map.Entry<String, Integer> entry = thangTotNhat.entrySet().iterator().next();
            String thang = entry.getKey();
            txtTotNhat.setText("Tháng " + thang);
            txtSLTotNhat.setText(entry.getValue() + "");
        }
        else{
            txtTotNhat.setText("Không có dữ liệu");
            txtSLTotNhat.setText(0 + "");
        }
        
        List<Map<Double,Double>> percent = statisticBUS.totalPercent(0,year);
        txtVeTiLe.setText(Math.round((double) percent.get(0).values().toArray()[0]*10.0)/10.0 + "%");
        txtVeDT.setText(decimal.format(percent.get(0).keySet().toArray()[0]));
        
        txtSanPhamTiLe.setText(Math.round((double) percent.get(1).values().toArray()[0]*10.0)/10.0 + "%");
        txtSPDT.setText(decimal.format(percent.get(1).keySet().toArray()[0]));
        
        txtThueTiLe.setText(Math.round((double) percent.get(2).values().toArray()[0]*10.0)/10.0 + "%");
        txtThueDT.setText(decimal.format(percent.get(2).keySet().toArray()[0]));
        
        txtGGTiLe.setText("-"+(Math.round((double) percent.get(3).values().toArray()[0]*10.0)/10.0) + "%");
        txtGGDT.setText("-" + decimal.format(percent.get(3).keySet().toArray()[0]));
    }

    private void fillThongKeDoanhThuTheoThang(int month, int year) {
        chartThongKeDoanhThu.removeAll();
        LineChart chartDoanhThu = new LineChart();
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        YearMonth yearMonth = YearMonth.of(currentYear, month);
        int dayInMonth = yearMonth.lengthOfMonth();

        Map<Integer, Double> doanhThuTKThang = statisticBUS.getBillByDayInMonth(month, year);
        for (int i = 1; i <= dayInMonth; i++) {
            int dayRevenue = (int) (doanhThuTKThang.containsKey(i) ? doanhThuTKThang.get(i) : 0);
            chartDoanhThu.addDataPoint(i + "", dayRevenue);
        }
        chartThongKeDoanhThu.add(chartDoanhThu, BorderLayout.CENTER);

        int soHoaDon = statisticBUS.getQuantityBillInMonthAndYear(month, year);
        setTextTK("Theo Tháng", "Tháng " + month, soHoaDon + "", decimal.format(statisticBUS.getTotalByMonth(month, year)));

        txtNgayNam.setText("Ngày có nhiều hóa đơn nhất");
        Map<String, Integer> ngayTotNhat = statisticBUS.getBillMaxDayInMonth(month, year);
        if (!ngayTotNhat.isEmpty()) {
            Map.Entry<String, Integer> entry = ngayTotNhat.entrySet().iterator().next();
            String ngay = entry.getKey();
            txtTotNhat.setText("Ngày " + ngay);
            txtSLTotNhat.setText(entry.getValue() + "");
        }
        else{
            txtTotNhat.setText("Không có dữ liệu");
            txtSLTotNhat.setText(0 + "");
        }
        List<Map<Double,Double>> percent = statisticBUS.totalPercent(month,year);
        txtVeTiLe.setText(Math.round((double) percent.get(0).values().toArray()[0]*10.0)/10.0 + "%");
        txtVeDT.setText(decimal.format(percent.get(0).keySet().toArray()[0]));
        
        txtSanPhamTiLe.setText(Math.round((double) percent.get(1).values().toArray()[0]*10.0)/10.0 + "%");
        txtSPDT.setText(decimal.format(percent.get(1).keySet().toArray()[0]));
        
        txtThueTiLe.setText(Math.round((double) percent.get(2).values().toArray()[0]*10.0)/10.0 + "%");
        txtThueDT.setText(decimal.format(percent.get(2).keySet().toArray()[0]));
        
        txtGGTiLe.setText("-"+(Math.round((double) percent.get(3).values().toArray()[0]*10.0)/10.0) + "%");
        txtGGDT.setText("-" + decimal.format(percent.get(3).keySet().toArray()[0]));
    }

    private String getMonthName(int month) {
        String[] monthNames = {"1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10",
            "11", "12"};
        return monthNames[month - 1];
    }

    private void setTextTK(String chuKy, String thoiGian, String soLuong, String doanhThu) {
        txtChuKy.setText(chuKy);
        txtThoiGian.setText(thoiGian);
        txtSoLuong.setText(soLuong);
        txtDoanhThu.setText(doanhThu);
    }

    private void setTieuChiTK(JComboBox tieuChi, JComboBox chon) {
        chon.removeAllItems();
        if (tieuChi.getSelectedIndex() == 0) {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            for (int i = 2020; i <= currentYear; i++) {
                chon.addItem("" + i);
            }
            chon.setSelectedItem(currentYear + "");
        } else {
            for (int i = 1; i <= 12; i++) {
                chon.addItem("Tháng " + i);
            }
            chon.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnRight = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        tieuChiTKDoanhThu = new javax.swing.JComboBox<>();
        chonThongKe = new javax.swing.JComboBox<>();
        chartThongKeDoanhThu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtDoanhThu = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtThoiGian = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtChuKy = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtVeTiLe = new javax.swing.JLabel();
        txtVeDT = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSanPhamTiLe = new javax.swing.JLabel();
        txtSPDT = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtThueTiLe = new javax.swing.JLabel();
        txtThueDT = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtGGTiLe = new javax.swing.JLabel();
        txtGGDT = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtTotNhat = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSLTotNhat = new javax.swing.JLabel();
        txtNgayNam = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chartThongKeSanPham = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bangPhimTK = new javax.swing.JTable();
        chonThongKee = new javax.swing.JComboBox<>();
        tieuChiTK = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        bangSpTK = new javax.swing.JTable();
        chartThongKePhim = new javax.swing.JPanel();

        pnRight.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setUI(new CustomTabbedPaneBottomUI());
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tieuChiTKDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tieuChiTKDoanhThu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo năm", "Theo tháng"}));
        tieuChiTKDoanhThu.setUI( new CustomComboBoxUI());
        tieuChiTKDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tieuChiTKDoanhThuActionPerformed(evt);
            }
        });

        chonThongKe = new JComboBox<>();
        chonThongKe.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        chonThongKe.setUI( new CustomComboBoxUI());
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        List<String> tmp = new ArrayList<>();
        for (int i = 2020; i <= currentYear; i++) {
            tmp.add(String.valueOf(i));
        }
        chonThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(tmp.toArray(new String[0])));
        chonThongKe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chonThongKeItemStateChanged(evt);
            }
        });
        chonThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonThongKeActionPerformed(evt);
            }
        });

        chartThongKeDoanhThu.setPreferredSize(new java.awt.Dimension(251, 414));

        javax.swing.GroupLayout chartThongKeDoanhThuLayout = new javax.swing.GroupLayout(chartThongKeDoanhThu);
        chartThongKeDoanhThu.setLayout(chartThongKeDoanhThuLayout);
        chartThongKeDoanhThuLayout.setHorizontalGroup(
            chartThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1112, Short.MAX_VALUE)
        );
        chartThongKeDoanhThuLayout.setVerticalGroup(
            chartThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 818, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 20, 0, 0);
        Border borderCompound = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);
        jPanel3.setBorder(borderCompound);
        jPanel3.setPreferredSize(new java.awt.Dimension(306, 180));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(306, 45));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Doanh thu");
        jPanel12.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 70));

        txtDoanhThu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDoanhThu.setText("jLabel18");
        jPanel12.add(txtDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 140, 70));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(306, 45));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Số hóa đơn");
        jPanel13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 70));

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuong.setText("jLabel18");
        jPanel13.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 140, 70));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setPreferredSize(new java.awt.Dimension(306, 45));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Thời gian");
        jPanel14.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 70));

        txtThoiGian.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtThoiGian.setText("jLabel18");
        jPanel14.add(txtThoiGian, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 140, 70));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setPreferredSize(new java.awt.Dimension(306, 45));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Chu kỳ");
        jPanel15.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 70));

        txtChuKy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtChuKy.setText("jLabel18");
        jPanel15.add(txtChuKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 140, 70));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(borderCompound);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Doanh thu");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Vé");

        txtVeTiLe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtVeTiLe.setText("jLabel16");

        txtVeDT.setText("jLabel16");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Sản phẩm");

        txtSanPhamTiLe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtSanPhamTiLe.setText("jLabel16");

        txtSPDT.setText("jLabel17");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtVeDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtVeTiLe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSanPhamTiLe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSPDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVeTiLe, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSanPhamTiLe, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVeDT)
                    .addComponent(txtSPDT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Thuế");

        txtThueTiLe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtThueTiLe.setText("jLabel16");

        txtThueDT.setText("jLabel16");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Giảm giá");

        txtGGTiLe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtGGTiLe.setText("jLabel16");

        txtGGDT.setText("jLabel17");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtThueDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtThueTiLe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGGTiLe, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(txtGGDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThueTiLe, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGGTiLe, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThueDT)
                    .addComponent(txtGGDT)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(borderCompound);
        jPanel6.setPreferredSize(new java.awt.Dimension(0, 215));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setPreferredSize(new java.awt.Dimension(306, 45));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Tốt nhất");
        jPanel16.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 70));

        txtTotNhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel16.add(txtTotNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 140, 70));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(306, 45));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Số hóa đơn");
        jPanel17.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 70));

        txtSLTotNhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel17.add(txtSLTotNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 140, 70));

        txtNgayNam.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayNam.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtNgayNam.setText("Tháng có nhiều hóa đơn nhất");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtNgayNam, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtNgayNam, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(tieuChiTKDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(chonThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(chartThongKeDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 1112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chonThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tieuChiTKDoanhThu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chartThongKeDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tieuChiTKDoanhThu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        chonThongKe.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        chonThongKe.setMaximumRowCount(3);
        chartThongKeDoanhThu.setLayout(new BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Doanh thu", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Top 5 bộ phim có doanh thu cao nhất");

        chartThongKeSanPham.setPreferredSize(new java.awt.Dimension(680, 325));

        javax.swing.GroupLayout chartThongKeSanPhamLayout = new javax.swing.GroupLayout(chartThongKeSanPham);
        chartThongKeSanPham.setLayout(chartThongKeSanPhamLayout);
        chartThongKeSanPhamLayout.setHorizontalGroup(
            chartThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        chartThongKeSanPhamLayout.setVerticalGroup(
            chartThongKeSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane2.setPreferredSize(new java.awt.Dimension(450, 600));

        String[] header = {"STT", "Tên phim", "Số vé", "Doanh thu"};
        modelTablePhim = new DefaultTableModel(header,0){
            boolean[] canEdit = new boolean [] {
                false,false, false,false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        bangPhimTK.setFont(new java.awt.Font("Segoe UI", 0, 18));
        bangPhimTK.getTableHeader().setPreferredSize(new Dimension(bangPhimTK.getColumnModel().getTotalColumnWidth(), 50));
        bangPhimTK.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        bangPhimTK.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                label.addMouseListener(null);

                return label;
            }
        });
        bangPhimTK.setModel(modelTablePhim);
        bangPhimTK.setGridColor(new java.awt.Color(255, 255, 255));

        bangPhimTK.setRowHeight(59);

        bangPhimTK.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        bangPhimTK.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(bangPhimTK);
        TableColumnModel columnModel = bangPhimTK.getColumnModel();
        TableColumn sttColl = columnModel.getColumn(0);
        TableColumn tenPhimColl = columnModel.getColumn(1);
        TableColumn doanhThuColl = columnModel.getColumn(2);
        TableColumn soVeColl = columnModel.getColumn(3);

        sttColl.setPreferredWidth(50);
        tenPhimColl.setPreferredWidth(200);
        doanhThuColl.setPreferredWidth(130);
        soVeColl.setPreferredWidth(72);

        bangPhimTK.getTableHeader().setResizingAllowed(false);
        for (int i = 0; i < bangPhimTK.getColumnCount(); i++) {
            bangPhimTK.getColumnModel().getColumn(i).setResizable(false);
        }

        chonThongKee = new JComboBox<>();
        chonThongKee.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        chonThongKee.setModel(new javax.swing.DefaultComboBoxModel<>(tmp.toArray(new String[0])));
        chonThongKee.setUI( new CustomComboBoxUI());
        chonThongKee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chonThongKeeActionPerformed(evt);
            }
        });

        tieuChiTK.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tieuChiTK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo năm", "Theo tháng"}));
        tieuChiTK.setBorder(null);
        tieuChiTK.setUI( new CustomComboBoxUI());
        tieuChiTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tieuChiTKActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Top 5 sản phẩm có lượt sử dụng cao nhất");

        jScrollPane3.setPreferredSize(new java.awt.Dimension(450, 600));

        String[] header1 = {"STT", "Tên sản phẩm", "Số lượng", "Doanh thu"};
        modelTableSp = new DefaultTableModel(header1,0){
            boolean[] canEdit = new boolean [] {
                false,false, false,false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        bangSpTK.setFont(new java.awt.Font("Segoe UI", 0, 18));
        bangSpTK.getTableHeader().setPreferredSize(new Dimension(bangSpTK.getColumnModel().getTotalColumnWidth(), 50));
        bangSpTK.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        bangSpTK.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                label.addMouseListener(null);

                return label;
            }
        });
        bangSpTK.setModel(modelTableSp);
        bangSpTK.setGridColor(new java.awt.Color(255, 255, 255));

        bangSpTK.setRowHeight(59);

        bangSpTK.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        bangSpTK.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(bangSpTK);
        TableColumnModel columnModel1 = bangSpTK.getColumnModel();
        TableColumn sttColl1 = columnModel1.getColumn(0);
        TableColumn tenPhimColl1 = columnModel1.getColumn(1);
        TableColumn doanhThuColl1 = columnModel1.getColumn(2);
        TableColumn soVeColl1 = columnModel1.getColumn(3);

        sttColl1.setPreferredWidth(50);
        tenPhimColl1.setPreferredWidth(200);
        doanhThuColl1.setPreferredWidth(130);
        soVeColl1.setPreferredWidth(72);

        bangSpTK.getTableHeader().setResizingAllowed(false);
        for (int i = 0; i < bangSpTK.getColumnCount(); i++) {
            bangSpTK.getColumnModel().getColumn(i).setResizable(false);
        }

        chartThongKePhim.setPreferredSize(new java.awt.Dimension(680, 325));

        javax.swing.GroupLayout chartThongKePhimLayout = new javax.swing.GroupLayout(chartThongKePhim);
        chartThongKePhim.setLayout(chartThongKePhimLayout);
        chartThongKePhimLayout.setHorizontalGroup(
            chartThongKePhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartThongKePhimLayout.setVerticalGroup(
            chartThongKePhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chartThongKePhim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE))
                        .addGap(66, 66, 66))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tieuChiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chonThongKee, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartThongKeSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(152, 152, 152))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chonThongKee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tieuChiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chartThongKePhim, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(chartThongKeSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        chartThongKeSanPham.setLayout(new BorderLayout());
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        chonThongKee.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        chonThongKee.setMaximumRowCount(3);
        tieuChiTK.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        chartThongKePhim.setLayout(new BorderLayout());

        jTabbedPane1.addTab("Phim/Sản phẩm", jPanel1);

        javax.swing.GroupLayout pnRightLayout = new javax.swing.GroupLayout(pnRight);
        pnRight.setLayout(pnRightLayout);
        pnRightLayout.setHorizontalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        pnRightLayout.setVerticalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnRightLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1690, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1074, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tieuChiTKDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tieuChiTKDoanhThuActionPerformed
        // TODO add your handling code here:
        setTieuChiTK(tieuChiTKDoanhThu, chonThongKe);
    }//GEN-LAST:event_tieuChiTKDoanhThuActionPerformed

    private void chonThongKeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chonThongKeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chonThongKeItemStateChanged

    private void chonThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonThongKeActionPerformed
        // TODO add your handling code here:
        int select = chonThongKe.getSelectedIndex();
        if (select == -1) {
            return;
        }
        if (tieuChiTKDoanhThu.getSelectedIndex() == 0) {
            int year = Integer.parseInt(chonThongKe.getItemAt(select));
            fillThongKeDoanhThuTheoNam(year);
        } else {
            int year = LocalDate.now().getYear();
            int month = chonThongKe.getSelectedIndex() + 1;
            fillThongKeDoanhThuTheoThang(month, year);
        }
    }//GEN-LAST:event_chonThongKeActionPerformed

    private void chonThongKeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chonThongKeeActionPerformed
        // TODO add your handling code here:
        int select = chonThongKee.getSelectedIndex();
        if (select == -1) {
            return;
        }
        if (tieuChiTK.getSelectedIndex() == 0) {
            int year = Integer.parseInt(chonThongKee.getItemAt(select));
            fillThongKeSanPhamTheoNam(year);
            fillThongKePhimTheoNam(year);
        } else {
            int year = LocalDate.now().getYear();
            int month = chonThongKee.getSelectedIndex() + 1;
            fillThongKeSanPhamTheoThang(month, year);
            fillThongKePhimTheoThang(month, year);
        }
    }//GEN-LAST:event_chonThongKeeActionPerformed

    private void tieuChiTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tieuChiTKActionPerformed
        // TODO add your handling code here:
        setTieuChiTK(tieuChiTK, chonThongKee);
    }//GEN-LAST:event_tieuChiTKActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        // In ra thông tin về tab được chọn
        if (selectedIndex == 0) {
            int select = chonThongKe.getSelectedIndex();
            if (select == -1) {
                return;
            }
            if (tieuChiTKDoanhThu.getSelectedIndex() == 0) {
                int year = Integer.parseInt(chonThongKe.getItemAt(select));
                fillThongKeDoanhThuTheoNam(year);
            } else {
                int year = LocalDate.now().getYear();
                int month = chonThongKe.getSelectedIndex() + 1;
                fillThongKeDoanhThuTheoThang(month, year);
            }

        } else {
            if (tieuChiTK.getSelectedIndex() == 0) {
                chonThongKee.setSelectedItem(LocalDate.now().getYear() + "");
            } else {

                chonThongKee.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
            }
            int select = chonThongKee.getSelectedIndex();
            if (select == -1) {
                return;
            }
            if (tieuChiTK.getSelectedIndex() == 0) {
                int year = Integer.parseInt(chonThongKee.getItemAt(select));
                fillThongKeSanPhamTheoNam(year);
                fillThongKePhimTheoNam(year);
            } else {
                int year = LocalDate.now().getYear();
                int month = chonThongKee.getSelectedIndex() + 1;
                fillThongKeSanPhamTheoThang(month, year);
                fillThongKePhimTheoThang(month, year);
            }
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bangPhimTK;
    private javax.swing.JTable bangSpTK;
    private javax.swing.JPanel chartThongKeDoanhThu;
    private javax.swing.JPanel chartThongKePhim;
    private javax.swing.JPanel chartThongKeSanPham;
    private javax.swing.JComboBox<String> chonThongKe;
    private javax.swing.JComboBox<String> chonThongKee;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnRight;
    private javax.swing.JComboBox<String> tieuChiTK;
    private javax.swing.JComboBox<String> tieuChiTKDoanhThu;
    private javax.swing.JLabel txtChuKy;
    private javax.swing.JLabel txtDoanhThu;
    private javax.swing.JLabel txtGGDT;
    private javax.swing.JLabel txtGGTiLe;
    private javax.swing.JLabel txtNgayNam;
    private javax.swing.JLabel txtSLTotNhat;
    private javax.swing.JLabel txtSPDT;
    private javax.swing.JLabel txtSanPhamTiLe;
    private javax.swing.JLabel txtSoLuong;
    private javax.swing.JLabel txtThoiGian;
    private javax.swing.JLabel txtThueDT;
    private javax.swing.JLabel txtThueTiLe;
    private javax.swing.JLabel txtTotNhat;
    private javax.swing.JLabel txtVeDT;
    private javax.swing.JLabel txtVeTiLe;
    // End of variables declaration//GEN-END:variables
}
