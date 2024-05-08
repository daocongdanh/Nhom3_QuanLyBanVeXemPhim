/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui.manager;

import bus.CustomerBUS;
import bus.VoucherBUS;
import bus.VoucherReleaseBUS;
import connectDB.ConnectDB;
import entity.Customer;
import entity.Voucher;
import entity.VoucherRelease;
import enums.ObjectType;
import gui.custom.CustomComboBoxUI;
import gui.custom.CustomScrollBarUI;
import gui.custom.CustomTabbedPaneBottomUI;
import gui.custom.TableActionCellEditor;
import gui.custom.TableActionCellRender;
import gui.custom.TableActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.GenerateVoucher;

/**
 *
 * @author Hoang
 */
public class TAB_Voucher extends javax.swing.JPanel {

    private DefaultTableModel modelTable;
    private DefaultTableModel modelTable1;
    private VoucherReleaseBUS voucherReleaseBUS;
    private VoucherBUS voucherBUS;
    private CustomerBUS customerBUS;
    private String voucherReleaseId;

    /**
     * Creates new form TAB_Voucher
     */
    public TAB_Voucher() {
        voucherReleaseBUS = new VoucherReleaseBUS();
        voucherBUS = new VoucherBUS();
        customerBUS = new CustomerBUS();
        initComponents();
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onDelete(int row) {
                tableVoucher.getCellEditor().stopCellEditing();
                int index = tableVoucher.getSelectedRow();
                VoucherRelease voucherRelease = voucherReleaseBUS.getVoucherReleaseById(tableVoucher.getValueAt(index, 0).toString());
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn xóa?", "Cảnh báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (!voucherReleaseBUS.checkModify(voucherRelease.getVoucherReleaseId())) {
                        if (voucherReleaseBUS.deleteVoucherRelease(voucherRelease.getVoucherReleaseId())) {
                            JOptionPane.showMessageDialog(null, "Xóa thành công");
                            List<VoucherRelease> voucherReleases = voucherReleaseBUS.getAllVoucherRelease();
                            loadData(voucherReleases);
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Không thể xóa tại vì đã phát hành cho khách hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            @Override
            public void onEdit(int row) {
                int index = tableVoucher.getSelectedRow();
                VoucherRelease voucherRelease = voucherReleaseBUS.getVoucherReleaseById(tableVoucher.getValueAt(index, 0).toString());
                txtTenPH.setText(voucherRelease.getReleaseName());
                txtMenhGia.setText(voucherRelease.getPrice() + "");
                txtToiThieu.setText(voucherRelease.getMinPrice() + "");
                dateTu.setDate(Date.valueOf(voucherRelease.getStartDate()));
                dateDen.setDate(Date.valueOf(voucherRelease.getEndDate()));
                String type = "";
                ObjectType objectType = voucherRelease.getObjectType();
                if (objectType == ObjectType.ALL) {
                    type = "Toàn bộ";
                } else if (objectType == ObjectType.PRODUCT) {
                    type = "Sản phẩm";
                } else {
                    type = "Vé xem phim";
                }
                cbNhomHang.setSelectedItem(type);

                lblDPH.setText("Đợt phát hành: " + voucherRelease.getReleaseName());
                voucherReleaseId = voucherRelease.getVoucherReleaseId();
                loadTableVoucher(voucherRelease, false);
                if (!voucherReleaseBUS.checkModify(voucherRelease.getVoucherReleaseId())) {
                    btnLuuVoucher.setEnabled(true);
                } else {
                    btnLuuVoucher.setEnabled(false);
                }
                if (voucherRelease.getEndDate().isBefore(LocalDate.now())) {
                    btnPhatMail.setEnabled(false);
                    btnThem.setEnabled(false);
                } else {
                    btnPhatMail.setEnabled(true);
                    btnThem.setEnabled(true);
                }
                jTabbedPane1.setSelectedIndex(0);
                modalSuaVoucherRelease.setVisible(true);
                tableVoucher.getCellEditor().stopCellEditing();
            }
        };
        tableVoucher.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRender());
        tableVoucher.getColumnModel().getColumn(7).setCellEditor(new TableActionCellEditor(event));
        List<VoucherRelease> voucherReleases = voucherReleaseBUS.getAllVoucherRelease();
        loadData(voucherReleases);
        jTabbedPane1.add("Thông tin", jPanel3);
        jTabbedPane1.add("Danh sách Voucher", jPanel4);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modalSuaVoucherRelease = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVC = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        lblDPH = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnPhatMail = new javax.swing.JButton();
        btnRemoveVoucher = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jcbTTVoucher = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        lblSelected = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblKqVoucher = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTenPH = new javax.swing.JTextField();
        txtMenhGia = new javax.swing.JTextField();
        txtToiThieu = new javax.swing.JTextField();
        dateTu = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        dateDen = new com.toedter.calendar.JDateChooser();
        btnLuuVoucher = new javax.swing.JButton();
        cbNhomHang = new javax.swing.JComboBox<>();
        modalThemVoucher = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtDoDai = new javax.swing.JTextField();
        txtBatDau = new javax.swing.JTextField();
        txtKetThuc = new javax.swing.JTextField();
        btnTaoVoucher = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        modalThemVoucherRelease = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtTenPH2 = new javax.swing.JTextField();
        txtMenhGia2 = new javax.swing.JTextField();
        txtToiThieu2 = new javax.swing.JTextField();
        dateTu2 = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        dateDen2 = new com.toedter.calendar.JDateChooser();
        btnThemDPH = new javax.swing.JButton();
        cbNhomHang2 = new javax.swing.JComboBox<>();
        modalPhatHanhVoucher = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jcbDoiTuong = new javax.swing.JComboBox<>();
        jcbSoLuong = new javax.swing.JComboBox<>();
        lblNPH = new javax.swing.JLabel();
        btnPhatHanh = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        pnRight = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableVoucher = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtSearchDPH = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        kqDPH = new javax.swing.JLabel();

        modalSuaVoucherRelease.setBackground(new java.awt.Color(255, 255, 255));
        modalSuaVoucherRelease.setMinimumSize(new java.awt.Dimension(1000, 650));
        modalSuaVoucherRelease.setModal(true);
        modalSuaVoucherRelease.setLocationRelativeTo(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 650));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Thông tin đợt phát hành");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTabbedPane1.setRequestFocusEnabled(false);
        jTabbedPane1.setUI(new CustomTabbedPaneBottomUI());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setMinimumSize(new java.awt.Dimension(1000, 540));
        jPanel4.setPreferredSize(new java.awt.Dimension(1000, 540));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 540));

        String[] header1 = {"Mã voucher","Code", "Ngày phát hành", "Ngày sử dụng","Trạng thái"};
        modelTable1 = new DefaultTableModel(header1,0){
            boolean[] canEdit = new boolean [] {
                false, false,false,false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        tableVC.setModel(modelTable1);
        tableVC.getTableHeader().setPreferredSize(new Dimension(tableVC.getColumnModel().getTotalColumnWidth(), 50));
        tableVC.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        tableVC.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                label.addMouseListener(null);

                return label;
            }
        });
        tableVC.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        tableVC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tableVC.setGridColor(new java.awt.Color(255, 255, 255));

        tableVC.setRowHeight(50);

        tableVC.setSelectionBackground(new java.awt.Color(54,107,177));

        //jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tableVC.getTableHeader().setReorderingAllowed(false);
        tableVC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableVC);
        //jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //jTable1.setRowSelectionAllowed(true);
        //jTable1.setColumnSelectionAllowed(false);
        //jTable1.setCellSelectionEnabled(false);

        tableVC.getTableHeader().setResizingAllowed(false);
        for (int i = 0; i < tableVC.getColumnCount(); i++) {
            tableVC.getColumnModel().getColumn(i).setResizable(false);
        }

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.setPreferredSize(new java.awt.Dimension(1070, 540));

        lblDPH.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDPH.setForeground(new java.awt.Color(51, 51, 51));
        lblDPH.setText("Đợt phát hành: Happy New Year");

        btnThem.setBackground(new java.awt.Color(11, 77, 163));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm voucher");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnPhatMail.setBackground(new java.awt.Color(11, 77, 163));
        btnPhatMail.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnPhatMail.setForeground(new java.awt.Color(255, 255, 255));
        btnPhatMail.setText("Phát hành qua Mail");
        btnPhatMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhatMailActionPerformed(evt);
            }
        });

        btnRemoveVoucher.setBackground(new java.awt.Color(11, 77, 163));
        btnRemoveVoucher.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnRemoveVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveVoucher.setText("Xóa voucher");
        btnRemoveVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveVoucherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDPH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnPhatMail)
                .addGap(18, 18, 18)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnRemoveVoucher)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDPH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem)
                    .addComponent(btnPhatMail)
                    .addComponent(btnRemoveVoucher))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField2.setToolTipText("");
        jTextField2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel14.setText("Trạng thái:");

        jcbTTVoucher.setFont(new java.awt.Font("Segoe UI", 0, 20));
        jcbTTVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa sử dụng", "Đã sử dụng"}));
        jcbTTVoucher.setUI(new CustomComboBoxUI());
        jcbTTVoucher.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        jcbTTVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTTVoucherActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel15.setText("Đang chọn:");

        lblSelected.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSelected.setText("0");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Kết quả:");

        lblKqVoucher.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblKqVoucher.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14)
                            .addGap(18, 18, 18)
                            .addComponent(jcbTTVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9))
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 973, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSelected)
                        .addGap(800, 800, 800)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblKqVoucher)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jcbTTVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblSelected)
                    .addComponent(jLabel21)
                    .addComponent(lblKqVoucher))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jScrollPane1.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane1.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        jTabbedPane1.addTab("tab1", jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(1000, 540));
        jPanel3.setPreferredSize(new java.awt.Dimension(1000, 540));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel5.setText("Tên đợt phát hành");
        jLabel5.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel7.setText("Mệnh giá(VNĐ)");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel8.setText("Hiệu lực từ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel9.setText("Nhóm hàng");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Tổng tối thiểu(VNĐ)");

        txtTenPH.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtTenPH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTenPH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenPHActionPerformed(evt);
            }
        });

        txtMenhGia.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtMenhGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMenhGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenhGiaActionPerformed(evt);
            }
        });

        txtToiThieu.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtToiThieu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtToiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtToiThieuActionPerformed(evt);
            }
        });

        dateTu.setBackground(new java.awt.Color(255, 255, 255));
        dateTu.setDateFormatString("dd/MM/yyyy");
        dateTu.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel11.setText("-->");

        dateDen.setBackground(new java.awt.Color(255, 255, 255));
        dateDen.setDateFormatString("dd/MM/yyyy");
        dateDen.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        btnLuuVoucher.setBackground(new java.awt.Color(11, 77, 163));
        btnLuuVoucher.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnLuuVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnLuuVoucher.setText("Lưu");
        btnLuuVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuVoucherActionPerformed(evt);
            }
        });

        cbNhomHang.setFont(new java.awt.Font("Segoe UI", 0, 20));
        cbNhomHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toàn bộ", "Sản phẩm", "Vé xem phim" }));
        cbNhomHang.setBorder(null);
        cbNhomHang.setUI(new CustomComboBoxUI());
        cbNhomHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(dateTu, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(dateDen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnLuuVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtMenhGia, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtTenPH, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel9)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtToiThieu, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(cbNhomHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(40, 40, 40))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(txtTenPH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNhomHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(txtMenhGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(dateTu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateDen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(btnLuuVoucher)
                .addGap(90, 90, 90))
        );

        jTabbedPane1.addTab("tab1", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        javax.swing.GroupLayout modalSuaVoucherReleaseLayout = new javax.swing.GroupLayout(modalSuaVoucherRelease.getContentPane());
        modalSuaVoucherRelease.getContentPane().setLayout(modalSuaVoucherReleaseLayout);
        modalSuaVoucherReleaseLayout.setHorizontalGroup(
            modalSuaVoucherReleaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        modalSuaVoucherReleaseLayout.setVerticalGroup(
            modalSuaVoucherReleaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        modalThemVoucher.setTitle("Thêm danh sách voucher");
        modalThemVoucher.setMinimumSize(new java.awt.Dimension(493, 370));
        modalThemVoucher.setModal(true);
        modalThemVoucher.setLocationRelativeTo(null);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMinimumSize(new java.awt.Dimension(493, 370));
        jPanel6.setName(""); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel16.setText("Số lượng voucher:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel17.setText("Độ dài mã:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel18.setText("Ký tự bắt đầu:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel19.setText("Ký tự kết thúc:");

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtSoLuong.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        txtDoDai.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtDoDai.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        txtBatDau.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtBatDau.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        txtKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtKetThuc.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        btnTaoVoucher.setBackground(new java.awt.Color(11, 77, 163));
        btnTaoVoucher.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnTaoVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoVoucher.setText("Lưu");
        btnTaoVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoVoucherActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Lưu ý:  Độ dài mã bằng 12, ký tự bắt đầu và ký tự kết thúc đều bằng 2");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTaoVoucher))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(24, 24, 24)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSoLuong)
                            .addComponent(txtDoDai)
                            .addComponent(txtBatDau)
                            .addComponent(txtKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtDoDai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel26)
                .addGap(40, 40, 40)
                .addComponent(btnTaoVoucher)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout modalThemVoucherLayout = new javax.swing.GroupLayout(modalThemVoucher.getContentPane());
        modalThemVoucher.getContentPane().setLayout(modalThemVoucherLayout);
        modalThemVoucherLayout.setHorizontalGroup(
            modalThemVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        modalThemVoucherLayout.setVerticalGroup(
            modalThemVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        modalThemVoucherRelease.setBackground(new java.awt.Color(255, 255, 255));
        modalThemVoucherRelease.setMinimumSize(new java.awt.Dimension(1000, 600));
        modalThemVoucherRelease.setResizable(false);
        modalThemVoucherRelease.setLocationRelativeTo(null);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMinimumSize(new java.awt.Dimension(1000, 600));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setText("Thêm mới đợt phát hành");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(713, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setMinimumSize(new java.awt.Dimension(1000, 540));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel32.setText("Tên đợt phát hành");
        jLabel32.setToolTipText("");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel33.setText("Mệnh giá(VNĐ)");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel34.setText("Hiệu lực từ");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel35.setText("Nhóm hàng");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel36.setText("Tổng tối thiểu(VNĐ)");

        txtTenPH2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtTenPH2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTenPH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenPH2ActionPerformed(evt);
            }
        });

        txtMenhGia2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtMenhGia2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtMenhGia2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenhGia2ActionPerformed(evt);
            }
        });

        txtToiThieu2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtToiThieu2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtToiThieu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtToiThieu2ActionPerformed(evt);
            }
        });

        dateTu2.setBackground(new java.awt.Color(255, 255, 255));
        dateTu2.setDateFormatString("dd/MM/yyyy");
        dateTu2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel37.setText("-->");

        dateDen2.setBackground(new java.awt.Color(255, 255, 255));
        dateDen2.setDateFormatString("dd/MM/yyyy");
        dateDen2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        btnThemDPH.setBackground(new java.awt.Color(11, 77, 163));
        btnThemDPH.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnThemDPH.setForeground(new java.awt.Color(255, 255, 255));
        btnThemDPH.setText("Thêm mới");
        btnThemDPH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDPHActionPerformed(evt);
            }
        });

        cbNhomHang2.setFont(new java.awt.Font("Segoe UI", 0, 20));
        cbNhomHang2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toàn bộ", "Sản phẩm", "Vé xem phim" }));
        cbNhomHang2.setBorder(null);
        cbNhomHang2.setUI(new CustomComboBoxUI());
        cbNhomHang2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addGap(53, 53, 53)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(dateTu2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addGap(18, 18, 18)
                        .addComponent(dateDen2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnThemDPH, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(txtMenhGia2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel36))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(txtTenPH2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel35)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtToiThieu2)
                                    .addComponent(cbNhomHang2, 0, 210, Short.MAX_VALUE))))
                        .addGap(40, 40, 40))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel35)
                    .addComponent(txtTenPH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNhomHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel36)
                    .addComponent(txtMenhGia2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtToiThieu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(dateTu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateDen2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemDPH)
                .addGap(70, 70, 70))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thông tin", jPanel3);

        javax.swing.GroupLayout modalThemVoucherReleaseLayout = new javax.swing.GroupLayout(modalThemVoucherRelease.getContentPane());
        modalThemVoucherRelease.getContentPane().setLayout(modalThemVoucherReleaseLayout);
        modalThemVoucherReleaseLayout.setHorizontalGroup(
            modalThemVoucherReleaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        modalThemVoucherReleaseLayout.setVerticalGroup(
            modalThemVoucherReleaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        modalPhatHanhVoucher.setMinimumSize(new java.awt.Dimension(600, 400));
        modalPhatHanhVoucher.setModal(true);
        modalPhatHanhVoucher.setLocationRelativeTo(null);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setMinimumSize(new java.awt.Dimension(600, 400));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(600, 70));
        jPanel10.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel22.setText("Phát hành voucher");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel22)
                .addGap(353, 353, 353))
        );

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel24.setText("Ngày phát hành:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel25.setText("Voucher/Khách:");

        jcbDoiTuong.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jcbDoiTuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Top 5 khách hàng trong tháng", "Tất cả khách hàng" }));
        jcbDoiTuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbDoiTuongActionPerformed(evt);
            }
        });
        jcbDoiTuong.setUI(new CustomComboBoxUI());
        jcbDoiTuong.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        jcbSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jcbSoLuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        jcbSoLuong.setUI(new CustomComboBoxUI());

        lblNPH.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblNPH.setText("30-12-2024");

        btnPhatHanh.setBackground(new java.awt.Color(11, 77, 163));
        btnPhatHanh.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnPhatHanh.setForeground(new java.awt.Color(255, 255, 255));
        btnPhatHanh.setText("Xác nhận");
        btnPhatHanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhatHanhActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel23.setText("Đối tượng:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jcbSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(321, 321, 321))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(lblNPH)
                        .addGap(279, 279, 279))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnPhatHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbDoiTuong, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbDoiTuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jcbSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lblNPH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(btnPhatHanh)
                .addGap(66, 66, 66))
        );

        jcbSoLuong.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        javax.swing.GroupLayout modalPhatHanhVoucherLayout = new javax.swing.GroupLayout(modalPhatHanhVoucher.getContentPane());
        modalPhatHanhVoucher.getContentPane().setLayout(modalPhatHanhVoucherLayout);
        modalPhatHanhVoucherLayout.setHorizontalGroup(
            modalPhatHanhVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        modalPhatHanhVoucherLayout.setVerticalGroup(
            modalPhatHanhVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1588, 920));

        pnRight.setBackground(new java.awt.Color(255, 255, 255));
        pnRight.setMinimumSize(new java.awt.Dimension(1083, 765));
        pnRight.setName(""); // NOI18N

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        String[] header = {"Mã phát hành","Tên đợt phát hành", "Từ ngày", "Đến ngày","Tối thiểu","Mệnh giá","Loại mặt hàng", "Thao tác"};
        modelTable = new DefaultTableModel(header,0){
            boolean[] canEdit = new boolean [] {
                false,false, false,false,false, false,false, true
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        tableVoucher.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        tableVoucher.setModel(modelTable);
        tableVoucher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableVoucher.setGridColor(new java.awt.Color(255, 255, 255));
        tableVoucher.setRowHeight(50);
        tableVoucher.setSelectionBackground(new java.awt.Color(54,107,177));
        tableVoucher.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableVoucher.getTableHeader().setReorderingAllowed(false);
        tableVoucher.getTableHeader().setPreferredSize(new Dimension(tableVoucher.getColumnModel().getTotalColumnWidth(), 50));
        tableVoucher.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        tableVoucher.getTableHeader().setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                label.addMouseListener(null);

                return label;
            }
        });
        jScrollPane2.setViewportView(tableVoucher);
        TableColumnModel columnModel = tableVoucher.getColumnModel();
        TableColumn id = columnModel.getColumn(0);
        TableColumn ten = columnModel.getColumn(1);
        TableColumn ngay1 = columnModel.getColumn(2);
        TableColumn ngay2 = columnModel.getColumn(3);
        TableColumn toithieu = columnModel.getColumn(4);
        TableColumn menhgia = columnModel.getColumn(5);
        TableColumn loaimathang = columnModel.getColumn(6);
        TableColumn thaotac = columnModel.getColumn(7);

        id.setPreferredWidth(50);
        ten.setPreferredWidth(200);
        ngay1.setPreferredWidth(100);
        ngay2.setPreferredWidth(100);
        toithieu.setPreferredWidth(100);
        menhgia.setPreferredWidth(100);
        loaimathang.setPreferredWidth(100);
        thaotac.setPreferredWidth(50);

        tableVoucher.getTableHeader().setResizingAllowed(false);
        for (int i = 0; i < tableVoucher.getColumnCount(); i++) {
            tableVoucher.getColumnModel().getColumn(i).setResizable(false);
        }

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N

        txtSearchDPH.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtSearchDPH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtSearchDPH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchDPHActionPerformed(evt);
            }
        });
        txtSearchDPH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchDPHKeyReleased(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(11, 77, 163));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thêm");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Kết quả:");

        kqDPH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        kqDPH.setText("0");

        javax.swing.GroupLayout pnRightLayout = new javax.swing.GroupLayout(pnRight);
        pnRight.setLayout(pnRightLayout);
        pnRightLayout.setHorizontalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnRightLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchDPH, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnRightLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnRightLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kqDPH))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1552, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(94, 94, 94))
        );
        pnRightLayout.setVerticalGroup(
            pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnRightLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchDPH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(kqDPH))
                .addContainerGap())
        );

        jScrollPane2.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane2.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane2.getViewport().setBackground(Color.WHITE);

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
            .addGap(0, 857, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
        txtTenPH2.setText("");
        txtMenhGia2.setText("");
        txtToiThieu2.setText("");
        dateTu2.setDate(Date.valueOf(LocalDate.now()));
        dateDen2.setDate(Date.valueOf(LocalDate.now()));
        cbNhomHang2.setSelectedIndex(0);
    }

    private void clearDataAddVoucher() {
        txtSoLuong.setText("");
        txtDoDai.setText("");
        txtBatDau.setText("");
        txtKetThuc.setText("");
    }

    private boolean checkValidVoucher() {
        String soLuong = txtSoLuong.getText();
        String doDai = txtDoDai.getText();
        String batDau = txtBatDau.getText();
        String ketThuc = txtKetThuc.getText();
        if (soLuong.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Số lượng không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int soLuong_parse = Integer.parseInt(soLuong);
            if (soLuong_parse <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải > 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (doDai.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Độ dài mã không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int doDai_parse = Integer.parseInt(doDai);
            if (doDai_parse != 12) {
                JOptionPane.showMessageDialog(null, "Độ dài mãi phải bằng 12", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Độ dài mãi phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (batDau.length() != 2) {
            JOptionPane.showMessageDialog(null, "Độ dài ký tự bắt đầu phải bằng 2", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ketThuc.length() != 2) {
            JOptionPane.showMessageDialog(null, "Độ dài ký tự kết thúc phải bằng 2", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadData(List<VoucherRelease> voucherReleases) {
        modelTable.setRowCount(0);
        kqDPH.setText(voucherReleases.size() + "");
        for (VoucherRelease vcr : voucherReleases) {
            String type = "";
            if (vcr.getObjectType() == ObjectType.ALL) {
                type = "Toàn bộ";
            } else if (vcr.getObjectType() == ObjectType.PRODUCT) {
                type = "Sản phẩm";
            } else {
                type = "Vé xem phim";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
            modelTable.addRow(new Object[]{vcr.getVoucherReleaseId(), vcr.getReleaseName(), formatter.format(vcr.getStartDate()), formatter.format(vcr.getEndDate()),
                decimalFormat.format(vcr.getMinPrice()), decimalFormat.format(vcr.getPrice()), type});
        }
    }

    private void loadTableVoucher(VoucherRelease voucherRelease, boolean status) {
        modelTable1.setRowCount(0);
        List<Voucher> vouchers = voucherBUS.getVoucherByVoucherReleaseAndStatus(voucherRelease.getVoucherReleaseId(), status);
        lblKqVoucher.setText(vouchers.size() + "");
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        for (Voucher vc : vouchers) {
            modelTable1.addRow(new Object[]{vc.getVoucherId(), vc.getCode(), vc.getReleaseAt() == null ? "" : fm.format(vc.getReleaseAt()),
                vc.getUsedAt() == null ? "" : fm.format(vc.getUsedAt()), vc.isStatus() == true ? "Đã sử dụng" : "Chưa sử dụng"});
        }
    }


    private void txtSearchDPHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchDPHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchDPHActionPerformed

    private void txtSearchDPHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDPHKeyReleased
        // TODO add your handling code here:
        String txtSearch = txtSearchDPH.getText();
        List<VoucherRelease> voucherReleases = voucherReleaseBUS.getVoucherByKeyword(txtSearch);
        loadData(voucherReleases);
    }//GEN-LAST:event_txtSearchDPHKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        jTabbedPane1.setEnabledAt(1, false);
        clearData();
        modalThemVoucherRelease.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableVCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVCMouseClicked
        // TODO add your handling code here:
        int indexs = tableVC.getSelectedRows().length;
        lblSelected.setText(indexs + "");
    }//GEN-LAST:event_tableVCMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        modalThemVoucher.setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnPhatMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhatMailActionPerformed
        // TODO add your handling code here:
        lblNPH.setText(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now()));
        modalPhatHanhVoucher.setVisible(true);
    }//GEN-LAST:event_btnPhatMailActionPerformed

    private void btnRemoveVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveVoucherActionPerformed
        // TODO add your handling code here:
        int[] indexs = tableVC.getSelectedRows();
        if (jcbTTVoucher.getSelectedItem().equals("Đã sử dụng")) {
            JOptionPane.showMessageDialog(null, "Không được xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (indexs.length == 0) {
            JOptionPane.showMessageDialog(jPanel4, "Vui lòng chọn voucher");
            return;
        }
        if (JOptionPane.showConfirmDialog(jPanel4, "Bạn có chắc chắn xóa danh sách voucher vừa chọn?", "Cảnh bảo",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            boolean check = true;
            for (int i : indexs) {
                Voucher voucher = voucherBUS.getVoucherById(tableVC.getValueAt(i, 0).toString());
                if (voucher.getReleaseAt() != null) {
                    check = false;
                    break;
                }
            }
            if (check) {
                for (int i : indexs) {
                    Voucher voucher = voucherBUS.getVoucherById(tableVC.getValueAt(i, 0).toString());
                    voucherBUS.deleteVoucherById(tableVC.getValueAt(i, 0).toString());
                }
                JOptionPane.showMessageDialog(null, "Xóa thành công");
                loadTableVoucher(new VoucherRelease(voucherReleaseId), false);
            } else {
                JOptionPane.showMessageDialog(null, "Không thể xóa voucher khi đã phát hành", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnRemoveVoucherActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jcbTTVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTTVoucherActionPerformed
        // TODO add your handling code here:
        boolean status = jcbTTVoucher.getSelectedItem().equals("Đã sử dụng");
        loadTableVoucher(new VoucherRelease(voucherReleaseId), status);
    }//GEN-LAST:event_jcbTTVoucherActionPerformed

    private void txtTenPHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenPHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenPHActionPerformed

    private void txtMenhGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenhGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenhGiaActionPerformed

    private void txtToiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToiThieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToiThieuActionPerformed

    private void btnLuuVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuVoucherActionPerformed
        // TODO add your handling code here:
        String releaseName = txtTenPH.getText();
        java.util.Date date1 = dateTu.getDate();
        LocalDate startDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.util.Date date2 = dateDen.getDate();
        LocalDate endDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String type = (String) cbNhomHang.getSelectedItem();
        if (checkValidDPH(releaseName, txtMenhGia.getText(), txtToiThieu.getText(), startDate, endDate, true)) {
            double price = Double.parseDouble(txtMenhGia.getText());
            double minPrice = Double.parseDouble(txtToiThieu.getText());
            ObjectType objectType;
            if (type.equals("Toàn bộ")) {
                objectType = ObjectType.ALL;
            } else if (type.equals("Sản phẩm")) {
                objectType = ObjectType.PRODUCT;
            } else {
                objectType = ObjectType.TICKET;
            }
            int index = tableVoucher.getSelectedRow();
            VoucherRelease voucherRelease = new VoucherRelease(tableVoucher.getValueAt(index, 0).toString(),
                    releaseName, startDate, endDate, price, minPrice, objectType);
            if (voucherReleaseBUS.updateVoucherRelease(voucherRelease)) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                List<VoucherRelease> voucherReleases = voucherReleaseBUS.getAllVoucherRelease();
                loadData(voucherReleases);
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
            }
            modalSuaVoucherRelease.setVisible(false);
        }
    }//GEN-LAST:event_btnLuuVoucherActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void btnTaoVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoVoucherActionPerformed
        // TODO add your handling code here:
        if (checkValidVoucher()) {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            int doDai = Integer.parseInt(txtDoDai.getText());
            String batDau = txtBatDau.getText();
            String ketThuc = txtKetThuc.getText();
            Set<String> coupons = GenerateVoucher.generatePromoCodes(batDau, ketThuc, doDai, soLuong);
            coupons.forEach(code -> {
                Voucher voucher = new Voucher(null, code, false, null, null, new VoucherRelease(voucherReleaseId));
                if (!voucherBUS.insertVoucher(voucher)) {
                    JOptionPane.showMessageDialog(null, "Thêm voucher thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            });
            JOptionPane.showMessageDialog(null, "Thêm voucher thành công");
            clearDataAddVoucher();
            modalThemVoucher.setVisible(false);
            loadTableVoucher(new VoucherRelease(voucherReleaseId), false);
        }
    }//GEN-LAST:event_btnTaoVoucherActionPerformed

    private void txtTenPH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenPH2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenPH2ActionPerformed

    private void txtMenhGia2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenhGia2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenhGia2ActionPerformed

    private void txtToiThieu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToiThieu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtToiThieu2ActionPerformed

    private boolean checkValidDPH(String releaseName, String price, String minPrice, LocalDate start, LocalDate end, boolean update) {
        if (releaseName.equals("")) {
            JOptionPane.showMessageDialog(null, "Tên đợt phát hành không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (price.equals("")) {
            JOptionPane.showMessageDialog(null, "Mệnh giá không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double price_parse = Double.parseDouble(price);
            if (price_parse < 0) {
                JOptionPane.showMessageDialog(null, "Mệnh giá phải > 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mệnh giá phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (minPrice.equals("")) {
            JOptionPane.showMessageDialog(null, "Tổng tối thểu không được rỗng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double minPrice_parse = Double.parseDouble(minPrice);
            if (minPrice_parse < 0) {
                JOptionPane.showMessageDialog(null, "Tổng tối thểu phải > 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tổng tối thểu phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (update) {
            if (end.isBefore(LocalDate.now()) || start.isAfter(end)) {
                JOptionPane.showMessageDialog(null, "Thời gian hiệu lực phải trước sau hiện tại và thời gian A < B", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            if (start.isBefore(LocalDate.now()) || end.isBefore(LocalDate.now()) || start.isAfter(end)) {
                JOptionPane.showMessageDialog(null, "Thời gian hiệu lực phải sau ngày hiện tại và thời gian A < B", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }
    private void btnThemDPHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDPHActionPerformed
        // TODO add your handling code here:
        String releaseName = txtTenPH2.getText();
        java.util.Date date1 = dateTu2.getDate();
        LocalDate startDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.util.Date date2 = dateDen2.getDate();
        LocalDate endDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String type = (String) cbNhomHang2.getSelectedItem();
        if (checkValidDPH(releaseName, txtMenhGia2.getText(), txtToiThieu2.getText(), startDate, endDate, false)) {
            double price = Double.parseDouble(txtMenhGia2.getText());
            double minPrice = Double.parseDouble(txtToiThieu2.getText());
            ObjectType objectType;
            if (type.equals("Toàn bộ")) {
                objectType = ObjectType.ALL;
            } else if (type.equals("Sản phẩm")) {
                objectType = ObjectType.PRODUCT;
            } else {
                objectType = ObjectType.TICKET;
            }
            VoucherRelease voucherRelease = new VoucherRelease(null, releaseName, startDate, endDate, price, minPrice, objectType);
            if (voucherReleaseBUS.insertVoucherRelease(voucherRelease)) {
                JOptionPane.showMessageDialog(null, "Thêm mới đợt phát hành thành công");
                List<VoucherRelease> voucherReleases = voucherReleaseBUS.getAllVoucherRelease();
                loadData(voucherReleases);
            } else {
                JOptionPane.showMessageDialog(null, "Thêm mới thất bại");
            }
            modalThemVoucherRelease.setVisible(false);
        }

    }//GEN-LAST:event_btnThemDPHActionPerformed

    private void jcbDoiTuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbDoiTuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbDoiTuongActionPerformed

    private void btnPhatHanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhatHanhActionPerformed
        // TODO add your handling code here:
        String object = (String) jcbDoiTuong.getSelectedItem();
        int soLuong = Integer.parseInt((String) jcbSoLuong.getSelectedItem());
        List<Voucher> vouchers = voucherBUS.getVoucherByReleaseAndReleaseAtIsNull(voucherReleaseId);
        VoucherRelease voucherRelease = voucherReleaseBUS.getVoucherReleaseById(voucherReleaseId);
        if (object.equals("Tất cả khách hàng")) {
            List<Customer> customers = customerBUS.getAllCustomer();
            if (customers.size() * soLuong > vouchers.size()) {
                JOptionPane.showMessageDialog(null, "Số lượng voucher không đủ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                voucherBUS.VoucherRelease(customers, vouchers, voucherRelease, soLuong);
            }
            JOptionPane.showMessageDialog(null, "Tri ân khách hàng thành công!");
            loadTableVoucher(voucherRelease, false);
            modalPhatHanhVoucher.setVisible(false);
        } else {
            List<Customer> customers = customerBUS.getTop5Customer(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
            if (customers.size() * soLuong > vouchers.size()) {
                JOptionPane.showMessageDialog(null, "Số lượng voucher không đủ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                voucherBUS.VoucherRelease(customers, vouchers, voucherRelease, soLuong);
            }
            JOptionPane.showMessageDialog(null, "Tri ân khách hàng thành công!");
            loadTableVoucher(voucherRelease, false);
            modalPhatHanhVoucher.setVisible(false);
        }
    }//GEN-LAST:event_btnPhatHanhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuuVoucher;
    private javax.swing.JButton btnPhatHanh;
    private javax.swing.JButton btnPhatMail;
    private javax.swing.JButton btnRemoveVoucher;
    private javax.swing.JButton btnTaoVoucher;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemDPH;
    private javax.swing.JComboBox<String> cbNhomHang;
    private javax.swing.JComboBox<String> cbNhomHang2;
    private com.toedter.calendar.JDateChooser dateDen;
    private com.toedter.calendar.JDateChooser dateDen2;
    private com.toedter.calendar.JDateChooser dateTu;
    private com.toedter.calendar.JDateChooser dateTu2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JComboBox<String> jcbDoiTuong;
    private javax.swing.JComboBox<String> jcbSoLuong;
    private javax.swing.JComboBox<String> jcbTTVoucher;
    private javax.swing.JLabel kqDPH;
    private javax.swing.JLabel lblDPH;
    private javax.swing.JLabel lblKqVoucher;
    private javax.swing.JLabel lblNPH;
    private javax.swing.JLabel lblSelected;
    private javax.swing.JDialog modalPhatHanhVoucher;
    private javax.swing.JDialog modalSuaVoucherRelease;
    private javax.swing.JDialog modalThemVoucher;
    private javax.swing.JDialog modalThemVoucherRelease;
    private javax.swing.JPanel pnRight;
    private javax.swing.JTable tableVC;
    private javax.swing.JTable tableVoucher;
    private javax.swing.JTextField txtBatDau;
    private javax.swing.JTextField txtDoDai;
    private javax.swing.JTextField txtKetThuc;
    private javax.swing.JTextField txtMenhGia;
    private javax.swing.JTextField txtMenhGia2;
    private javax.swing.JTextField txtSearchDPH;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenPH;
    private javax.swing.JTextField txtTenPH2;
    private javax.swing.JTextField txtToiThieu;
    private javax.swing.JTextField txtToiThieu2;
    // End of variables declaration//GEN-END:variables
}
