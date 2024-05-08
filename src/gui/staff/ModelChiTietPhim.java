/*
 * @ (#) ModelChiTietPhim.java		1.0		Apr 9, 2024
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package gui.staff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import gui.custom.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * @description:
 * @author: Đào Đức Danh
 * @date: Apr 9, 2024
 * @version:	1.0
 */
public class ModelChiTietPhim extends JFrame {

    public ModelChiTietPhim() {
        setSize(700, 450);
        setLocationRelativeTo(null);

        JPanel container = new JPanel();
        container.setBackground(Color.WHITE);
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnTop.setPreferredSize(new Dimension(700, 70));
        pnTop.setBackground(new java.awt.Color(163, 187, 191));
        pnTop.setBorder(BorderFactory.createEmptyBorder(16, 22, 0, 0));
        JLabel lblTop = new JLabel("Squadin Cinema");
        lblTop.setFont(new java.awt.Font("Segoe UI", 1, 16));
        pnTop.add(lblTop);

        JPanel pnMid = new JPanel();
        pnMid.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnMid.setBackground(Color.WHITE);

        JPanel pnLeft = new JPanel();
        pnLeft.setBackground(Color.WHITE);
        pnLeft.setLayout(new BoxLayout(pnLeft, BoxLayout.Y_AXIS));
        pnLeft.add(Box.createRigidArea(new Dimension(10, 10)));
        pnLeft.setPreferredSize(new Dimension(200, 300));
        JPanel pnImg = new JPanel();
        pnImg.setBackground(Color.WHITE);
        JLabel img = new JLabel();
        JLabel name = new JLabel("John Wick: Chapter 4");
        img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/johnWick.png")));
        name.setFont(new java.awt.Font("Segoe UI", 1, 14));
        pnImg.setPreferredSize(new Dimension(180, 250));
        pnImg.add(img);
        pnLeft.add(pnImg);
        JPanel pnName = new JPanel();
        pnName.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnName.setBorder(BorderFactory.createEmptyBorder(10, 3, 0, 0));
        pnName.setBackground(Color.WHITE);
        pnName.add(name);
        pnLeft.add(pnName);

        JScrollPane scroll = new JScrollPane();
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(470, 280));
        scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        JPanel pnTable = new JPanel();
        pnTable.setBackground(Color.WHITE);
        pnTable.setPreferredSize(new Dimension(480, 280));
        scroll.setViewportView(pnTable);
        for (int i = 1; i <= 3; i++) {
            JPanel pnRoom = new JPanel();
            pnRoom.setBackground(Color.WHITE);
            pnRoom.setPreferredSize(new Dimension(450, 70));
            pnRoom.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            JLabel lblRoom = new JLabel("Phòng" + i);
            lblRoom.setFont(new Font("Segoe UI", 1, 14));
            lblRoom.setPreferredSize(new Dimension(64, 20));
            pnRoom.add(lblRoom);
            pnRoom.add(Box.createRigidArea(new Dimension(10, 10)));
            JPanel pnRight = new JPanel();
            pnRight.setBackground(Color.WHITE);
            pnRight.setLayout(new FlowLayout(FlowLayout.LEFT));
            pnRight.setPreferredSize(new Dimension(336, 44));
            pnRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            pnRoom.add(pnRight);
            for (int j = 1; j <= 2; j++) {
                JPanel pnSuatChieu = new JPanel();
                pnSuatChieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                pnSuatChieu.setBackground(new java.awt.Color(204, 204, 0));
                pnSuatChieu.setPreferredSize(new java.awt.Dimension(70, 40));
                JLabel lblSuatChieu = new JLabel("19:00:00");
                lblSuatChieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                pnSuatChieu.add(lblSuatChieu);
                pnRight.add(pnSuatChieu);
            }
            pnTable.add(pnRoom);
        }

        pnMid.add(pnLeft);
        pnMid.add(scroll);

        container.add(pnTop);
        container.add(pnMid);
        add(container);
    }

}
