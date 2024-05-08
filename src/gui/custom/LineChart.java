package gui.custom;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LineChart extends JPanel {

    private List<String> dataPointsX;
    private List<Integer> dataPointsY;
    private DecimalFormat decimal = new DecimalFormat("#,### đ");
    private final int paddingLeft = 90; // Padding bên trái

    public LineChart() {
        setPreferredSize(new Dimension(400, 300));
        dataPointsX = new ArrayList<>();
        dataPointsY = new ArrayList<>();
    }

    public void addDataPoint(String x, int y) {
        dataPointsX.add(x);
        dataPointsY.add(y);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        int width = getWidth();
        int height = getHeight();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw x and y axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(paddingLeft, height - 50, width - 50, height - 50); // x axis
        g2d.drawLine(paddingLeft, 50, paddingLeft, height - 50); // y axis

        // Calculate data points and scales
        int numPoints = dataPointsX.size();
        if (numPoints != dataPointsY.size()) {
            return;
        }

        // Determine the maximum value in the data
        int maxValue = getMaxValue();

        // Calculate the maximum y position within the chart, leaving some margin
        int maxY = height - 170;

        // Calculate the scale for y axis
        double yScale = (double) maxY / maxValue;

        // Draw horizontal lines and labels for y-axis
        for (int i = 1; i <= 11; i++) {
            int value = maxValue * i / 10;
            int y = height - 50 - (int) (i * yScale * maxValue / 10);

            g2d.setColor(new Color(200, 200, 200, 150)); // Gray with transparency
            g2d.drawLine(paddingLeft, y, width - 50, y);

            g2d.setColor(Color.BLACK);
            int labelX = 10; // Padding bên trái cho nhãn
            int labelY = y + g2d.getFontMetrics().getHeight() / 2;
            g2d.drawString(decimal.format(value), labelX, labelY);
        }

        // Calculate x scale
        double xScale = (width - 100 - paddingLeft) / (double) (numPoints - 1);

        // Draw smooth curve
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.setColor(Color.RED); // Set color to red
        GeneralPath path = new GeneralPath();
        path.moveTo(paddingLeft, height - 50 - dataPointsY.get(0) * yScale);
        for (int i = 1; i < numPoints; i++) {
            int x1 = (int) (paddingLeft + (i - 1) * xScale);
            int y1 = (int) (height - 50 - dataPointsY.get(i - 1) * yScale);
            int x2 = (int) (paddingLeft + i * xScale);
            int y2 = (int) (height - 50 - dataPointsY.get(i) * yScale);
            int ctrlX1 = (x1 + x2) / 2;
            int ctrlY1 = y1;
            int ctrlX2 = (x1 + x2) / 2;
            int ctrlY2 = y2;
            path.curveTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2);
        }
        g2d.draw(path);

        // Draw circular points for data points
        for (int i = 0; i < numPoints; i++) {
            int circleX = (int) (paddingLeft + i * xScale) - 2;
            int circleY = (int) (height - 50 - dataPointsY.get(i) * yScale) - 2;
            g2d.fillOval(circleX, circleY, 4, 4);
        }

        // Draw labels for x-axis
        for (int i = 0; i < numPoints; i++) {
            int x = (int) (paddingLeft + i * xScale);
            int y = height - 30;
            g2d.drawString(dataPointsX.get(i), x, y);
        }
    }

    private int getMaxValue() {
        int max = dataPointsY.stream().max(Integer::compare).orElse(0);
        if ( max == 0 ) max = 100000;
        return  max;
    }
}
