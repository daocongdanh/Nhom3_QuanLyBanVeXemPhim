/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.custom;

/**
 *
 * @author Hoang
 */
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComponent;

public class CustomScrollBarUI extends BasicScrollBarUI {

    private final Dimension d = new Dimension();

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color;
        if (isDragging) {
            color = Color.GRAY;
        } else if (isThumbRollover()) {
            color = Color.DARK_GRAY;
        } else {
            color = Color.LIGHT_GRAY;
        }

        g2.setPaint(color);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);

        g2.setPaint(Color.WHITE);
        g2.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);

        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(Color.WHITE);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(d);
        button.setMinimumSize(d);
        button.setMaximumSize(d);
        return button;
    }
}