/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

/**
 *
 * @author Hoang
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class SpinnerUI extends BasicSpinnerUI {

    @Override
    protected Component createNextButton() {
        ArrowButton cmd = new ArrowButton(true);
        installNextButtonListeners(cmd);
        return cmd;
    }

    @Override
    protected Component createPreviousButton() {
        ArrowButton cmd = new ArrowButton(false);
        installPreviousButtonListeners(cmd);
        return cmd;
    }

    private class ArrowButton extends JButton {

        private final boolean next;

        public ArrowButton(boolean next) {
            this.next = next;
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(new EmptyBorder(5, 9, 5, 9));
            setBackground(new Color(231, 231, 231));
            setForeground(new Color(150, 150, 150));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    setSelected(true);
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    setSelected(false);
                }
            });
        }

        @Override
        public void paint(Graphics grphcs) {
            super.paint(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 1, getWidth(), getHeight() - 2, 5, 5);
            if (next) {
                int width = getWidth();
                int height = getHeight();
                int size = 10;
                int x = (width - size) / 2;
                int y = (height - size) / 2;
                int px[] = {x + size / 2, x + size, x};
                int py[] = {y, y + size, y + size};
                if (isSelected()) {
                    g2.setColor(new Color(181, 181, 181));
                } else {
                    g2.setColor(getForeground());
                }
                g2.fillPolygon(px, py, px.length);
            } else {
                int width = getWidth();
                int height = getHeight();
                int size = 10;
                int x = (width - size) / 2;
                int y = (height - size) / 2;
                int px[] = {x, x + size, x + size / 2};
                int py[] = {y, y, y + size};
                if (isSelected()) {
                    g2.setColor(new Color(181, 181, 181));
                } else {
                    g2.setColor(getForeground());
                }
                g2.fillPolygon(px, py, px.length);
            }
            g2.dispose();
        }
    }
}
