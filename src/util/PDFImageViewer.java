/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Hoang
 */
import gui.custom.CustomScrollBarUI;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PDFImageViewer extends JDialog {

    private JLabel imageLabel;

    public PDFImageViewer(String pdfFilePath) {
        setTitle("Hóa đơn");
        setSize(634, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        imageLabel = new JLabel();
        JScrollPane scroll = new JScrollPane(imageLabel);
        scroll.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scroll.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        setLayout(new BorderLayout());
        add( scroll, BorderLayout.CENTER);

        displayPDFAsImage(pdfFilePath);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setVisible(true);
    }

    private void displayPDFAsImage(String pdfFilePath) {
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImage(0, 1); // Render the first page
            document.close();

            ImageIcon icon = new ImageIcon(image);
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
