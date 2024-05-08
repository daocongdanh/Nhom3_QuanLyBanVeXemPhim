/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author Hoang
 */
 public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

    private static final int PADDING = 10; // Padding cho mỗi bên của tab
    

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return 45;  // Trả về chiều cao tùy chỉnh cho tab
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 2 * PADDING; // Thêm padding cho chiều rộng của tab
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // Không vẽ border nếu không có isSelected
        if (!isSelected) {
            return;
        }

        // Vẽ border bên trái của tab
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.blue);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x, y, x , y + h );
        g2d.dispose();
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Không có vẽ nào trong phương thức này
    }
}
