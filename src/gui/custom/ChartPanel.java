package gui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ChartPanel extends JPanel {

    private int histogramHeight = 200;
    private int barWidth = 50;
    private int barGap = 10;
    private int animationDelay = 1; // milliseconds
    private Timer animationTimer;
    private int currentHeight = 0;

    private JPanel barPanel;
    private JPanel labelPanel;

    private List<Bar> bars = new ArrayList<Bar>();

    public ChartPanel() {
        setBorder(new EmptyBorder(1, 1, 1, 1));
        setLayout(new BorderLayout());

        barPanel = new JPanel(new GridLayout(1, 0, barGap, 0));
        Border outer = new MatteBorder(0, 0, 1, 0, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        barPanel.setBorder(compound);
        barPanel.setBackground(Color.WHITE);

        labelPanel = new JPanel(new GridLayout(1, 0, barGap, 0));
        labelPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
        labelPanel.setBackground(Color.WHITE);

        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }

    public void addHistogramColumn(String label, int value, Color color) {
        Bar bar = new Bar(label, value, color);
        bars.add(bar);
    }

    public void layoutHistogramWithAnimation() {
        animationTimer = new Timer(animationDelay, e -> {
            if (currentHeight < histogramHeight) {
                currentHeight += 10;
                updateBarsWithCurrentHeight();
            } else {
                animationTimer.stop();
            }
        });
        animationTimer.start();
    }

    private void updateBarsWithCurrentHeight() {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = getMaxValue();

        for (Bar bar : bars) {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = 0;
            if (maxValue != 0) {
                barHeight = (bar.getValue() * currentHeight ) / maxValue;
            }
            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);
            label.setIcon(icon);
            barPanel.add(label);

            JLabel barLabel = new JLabel(bar.getLabel());
            barLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add(barLabel);
        }
        revalidate();
        repaint();
    }

    private int getMaxValue() {
        int maxValue = 0;
        for (Bar bar : bars) {
            maxValue = Math.max(maxValue, bar.getValue());
        }
        return maxValue;
    }

    private class Bar {

        private String label;
        private int value;
        private Color color;

        public Bar(String label, int value, Color color) {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }

    private class ColorIcon implements Icon {

        private int shadow = 0;

        private Color color;
        private int width;
        private int height;

        public ColorIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y + (height - height ), width - shadow, height);
            g.setColor(Color.GRAY);
            g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }


    public void removeAllBars() {
        bars.clear(); 
        barPanel.removeAll(); 
        labelPanel.removeAll(); 
        revalidate();
        repaint();
    }

}
