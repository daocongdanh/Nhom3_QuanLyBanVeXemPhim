/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

/**
 *
 * @author Hoang
 */
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class CustomComboBoxUI extends BasicComboBoxUI {
    
    @Override
    protected JButton createArrowButton() {
        return new ArrowButton();
    }

    @Override
    protected ComboPopup createPopup() {
        return new BasicComboPopup(comboBox) {
            @Override
            protected JScrollPane createScroller() {
                JScrollPane scroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scroller.setBorder(null);
                scroller.getVerticalScrollBar().setUI(new CustomScrollBarUI());
                return scroller;
            }
        };
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        g.setColor(Color.WHITE);  // Thiết lập màu nền trắng
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    protected ListCellRenderer<Object> createRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBackground(Color.WHITE);  // Thiết lập màu nền trắng cho cell
                return c;
            }
        };
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        comboBox.setRenderer(new CustomListCellRenderer());
    }

    private class ArrowButton extends JButton {

        public ArrowButton() {  
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(5, 5, 5, 5));
            setBackground(new Color(150, 150, 150));
        }

        @Override
        public void paint(Graphics grphcs) {
            super.paint(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            int size = 10;
            int x = (width - size) / 2;
            int y = (height - size) / 2 + 5;
            int px[] = {x, x + size, x + size / 2};
            int py[] = {y, y, y + size};
            g2.setColor(getBackground());
            g2.fillPolygon(px, py, px.length);
            g2.dispose();
        }
    }

    private class CustomListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                       boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBackground(isSelected ? new Color(11, 77, 163) : Color.WHITE);
            label.setForeground(isSelected ? Color.WHITE : Color.BLACK);
            label.setPreferredSize(new Dimension(label.getPreferredSize().width, 40)); // Đặt chiều cao của cell là 30px
            return label;
        }
    }
}



