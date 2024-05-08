/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Hoang
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import gui.custom.CustomDashedLineSeparator;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MovieTicketPDF {

    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("movie_ticket_invoice.pdf"));
            document.open();

            File fontFile = new File("data/font/vuArial.ttf");
            BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            CustomDashedLineSeparator dotLine = new CustomDashedLineSeparator();
            dotLine.setDash(10);
            dotLine.setGap(7);
            dotLine.setLineWidth(1);

            // Font
            Font titleFont = new Font(bf, 18, Font.BOLD);
            Font titleFontMini = new Font(bf, 11, Font.BOLD);
            Font normalFont = new Font(bf, 12, Font.NORMAL);

            // Title
            Paragraph title = new Paragraph("VÉ XEM PHIM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Line separator
            LineSeparator line = new LineSeparator();
            document.add(line);

            //Infor
            Paragraph creatorInfo = new Paragraph();
            creatorInfo.setSpacingBefore(10f);
            creatorInfo.add(new Chunk("Nhân viên: ", normalFont));
            creatorInfo.add(new Chunk("Việt Hoàng", titleFontMini));
            creatorInfo.add(Chunk.NEWLINE);
            creatorInfo.add(new Chunk("Ngày lập: ", normalFont));
            creatorInfo.add(new Chunk(new Date().toString(), normalFont));
            creatorInfo.setAlignment(Element.ALIGN_LEFT);
            document.add(creatorInfo);

            // Buyer details
            Paragraph buyerInfo = new Paragraph();
            buyerInfo.add(new Chunk("Khách hàng: ", normalFont));
            buyerInfo.add(new Chunk("John Doe", titleFontMini)); // Thay bằng tên người mua thực tế
            buyerInfo.add(Chunk.NEWLINE);
            buyerInfo.add(new Chunk("Điện thoại: ", normalFont));
            buyerInfo.add(new Chunk("1234567890", normalFont)); // Thay bằng số điện thoại thực tế
            buyerInfo.setAlignment(Element.ALIGN_LEFT);
            document.add(buyerInfo);
            document.add(Chunk.NEWLINE);
            document.add(dotLine);

            // Movie details
            Paragraph movieTitle = new Paragraph("Vé phim", titleFontMini);
            movieTitle.setSpacingBefore(10f);
            movieTitle.setSpacingAfter(5f);
            Paragraph movieDetails = new Paragraph();
            movieDetails.add(new Chunk("Phim: ", normalFont));
            movieDetails.add(new Chunk("Avenger 2: Infinitive War", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 13)));
            movieDetails.add(Chunk.NEWLINE);
            movieDetails.add(new Chunk("Suất chiếu: ", normalFont));
            movieDetails.add(new Chunk("April 30, 2024", normalFont));
            movieDetails.add(Chunk.TABBING);
            movieDetails.add(Chunk.TABBING);
            movieDetails.add(new Chunk("Giờ: ", normalFont));
            movieDetails.add(new Chunk("6:00 PM", normalFont));
            movieDetails.add(Chunk.NEWLINE);
            movieDetails.add(new Chunk("Phòng: ", normalFont));
            movieDetails.add(new Chunk("01", normalFont));
            movieDetails.setAlignment(Element.ALIGN_LEFT);
//            movieDetails.setSpacingAfter(10f);
            document.add(movieTitle);
            document.add(movieDetails);

            // Ticket details
            PdfPTable ticketTable = new PdfPTable(4);
            ticketTable.setWidthPercentage(100);
            ticketTable.setSpacingBefore(10f);
            ticketTable.setSpacingAfter(10f);
            // Define relative column widths
            float[] columnWidths = {1.5f, 2f, 1.5f, 1.5f}; // Adjust the width of the second column (seat names)

            // Set column widths
            ticketTable.setWidths(columnWidths);

            PdfPCell seatTypeHeader = new PdfPCell(new Phrase("Loại ghế", normalFont));
            seatTypeHeader.setBorder(Rectangle.NO_BORDER);
            PdfPCell seatNameHeader = new PdfPCell(new Phrase("Tên ghế", normalFont));
            seatNameHeader.setBorder(Rectangle.NO_BORDER);
            PdfPCell unitPriceHeader = new PdfPCell(new Phrase("Đơn giá", normalFont));
            unitPriceHeader.setBorder(Rectangle.NO_BORDER);
            PdfPCell totalPriceHeader = new PdfPCell(new Phrase("Thành tiền", normalFont));
            totalPriceHeader.setBorder(Rectangle.NO_BORDER);

            ticketTable.addCell(seatTypeHeader);
            ticketTable.addCell(seatNameHeader);
            ticketTable.addCell(unitPriceHeader);
            ticketTable.addCell(totalPriceHeader);

            // Sample ticket data
            String[] seatTypes = {"Standard", "VIP"};
            String[] seatNames = {"A12,B17,D21,D22,D23", "B5"};
            double[] unitPrice = {12.50, 25.00};
            double[] totalPrice = {12.50, 25.00};
            for (int i = 0; i < seatTypes.length; i++) {
                PdfPCell seatTypeCell = new PdfPCell(new Phrase(seatTypes[i], normalFont));
                seatTypeCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell seatNameCell = new PdfPCell(new Phrase(seatNames[i], normalFont));
                seatNameCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell unitPriceCell = new PdfPCell(new Phrase(String.valueOf(unitPrice[i]), normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell totalPriceCell = new PdfPCell(new Phrase(String.valueOf(totalPrice[i]), normalFont));
                totalPriceCell.setBorder(Rectangle.NO_BORDER);

                ticketTable.addCell(seatTypeCell);
                ticketTable.addCell(seatNameCell);
                ticketTable.addCell(unitPriceCell);
                ticketTable.addCell(totalPriceCell);
            }

            PdfPCell totalTicketPriceCell = new PdfPCell(new Phrase("Tổng giá vé", normalFont));
            totalTicketPriceCell.setColspan(3);
            totalTicketPriceCell.setBorder(Rectangle.NO_BORDER);
            ticketTable.addCell(totalTicketPriceCell);

            double totalTicketPrice = 0;
            for (double price : totalPrice) {
                totalTicketPrice += price;
            }
            PdfPCell totalTicketPriceValueCell = new PdfPCell(new Phrase(String.valueOf(totalTicketPrice), normalFont));
            totalTicketPriceValueCell.setBorder(Rectangle.NO_BORDER);
            ticketTable.addCell(totalTicketPriceValueCell);

            document.add(ticketTable);
            document.add(Chunk.NEWLINE);
            document.add(dotLine);

            // Product details
            Paragraph productDetails = new Paragraph("Sản phẩm mua kèm", titleFontMini);
//            productDetails.setAlignment(Element.ALIGN_LEFT);
            productDetails.setSpacingBefore(10f);
            productDetails.setSpacingAfter(5f);

            document.add(productDetails);

            PdfPTable productTable = new PdfPTable(4);
            productTable.setWidthPercentage(100);
            productTable.setSpacingBefore(10f);
            productTable.setSpacingAfter(10f);
            // Set column widths
            productTable.setWidths(columnWidths);

            PdfPCell productNameHeader = new PdfPCell(new Phrase("Tên sản phẩm", normalFont));
            productNameHeader.setBorder(Rectangle.NO_BORDER);
            PdfPCell quantityHeader = new PdfPCell(new Phrase("Số lượng", normalFont));
            quantityHeader.setBorder(Rectangle.NO_BORDER);
            PdfPCell unitPricePHeader = new PdfPCell(new Phrase("Đơn giá", normalFont));
            unitPricePHeader.setBorder(Rectangle.NO_BORDER);
            PdfPCell totalPriceHeader2 = new PdfPCell(new Phrase("Thành tiền", normalFont));
            totalPriceHeader2.setBorder(Rectangle.NO_BORDER);

            productTable.addCell(productNameHeader);
            productTable.addCell(quantityHeader);
            productTable.addCell(unitPricePHeader);
            productTable.addCell(totalPriceHeader2);

            // Sample product data
            String[] productNames = {"Popcorn", "Soda", "Candy"};
            int[] quantities = {2, 1, 3};
            double[] unitPrices = {5.00, 3.00, 2.50};

            for (int i = 0; i < productNames.length; i++) {
                PdfPCell productNameCell = new PdfPCell(new Phrase(productNames[i], normalFont));
                productNameCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(productNameCell);

                PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(quantities[i]), normalFont));
                quantityCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(quantityCell);

                PdfPCell unitPriceCell = new PdfPCell(new Phrase(String.valueOf(unitPrices[i]), normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(unitPriceCell);

                PdfPCell totalPriceCell = new PdfPCell(new Phrase(String.valueOf(quantities[i] * unitPrices[i]), normalFont));
                totalPriceCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(totalPriceCell);
            }
            PdfPCell totalProductPriceCell = new PdfPCell(new Phrase("Tổng giá vé", normalFont));
            totalProductPriceCell.setColspan(3);
            totalProductPriceCell.setBorder(Rectangle.NO_BORDER);
            productTable.addCell(totalProductPriceCell);

            PdfPCell totalProductPriceValueCell = new PdfPCell(new Phrase(String.valueOf(totalTicketPrice), normalFont));
            totalProductPriceValueCell.setBorder(Rectangle.NO_BORDER);
            productTable.addCell(totalProductPriceValueCell);

            document.add(productTable);
            document.add(Chunk.NEWLINE);
            document.add(dotLine);
            // Total
            double ticketTotal = 0;
            for (double price : totalPrice) {
                ticketTotal += price;
            }

            double productTotal = 0;
            for (int i = 0; i < quantities.length; i++) {
                productTotal += quantities[i] * unitPrices[i];
            }

            double grandTotal = ticketTotal + productTotal;

            Paragraph total = new Paragraph();
            total.add(new Chunk("Tổng hóa đơn: $", normalFont));
            total.add(new Chunk(String.valueOf(ticketTotal), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Giảm giá: $", normalFont));
            total.add(new Chunk(String.valueOf(productTotal), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Thuế: $", normalFont));
            total.add(new Chunk(String.valueOf(productTotal), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Tổng tiền: $", normalFont));
            total.add(new Chunk(String.valueOf(grandTotal), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(20f);
            total.setSpacingAfter(10f);
            document.add(total);

            // QR Code (you need to implement QR code generation)
            // Image qrCode = Image.getInstance("path_to_your_qr_code.png");
            // qrCode.scaleAbsolute(100f, 100f);
            // qrCode.setAlignment(Element.ALIGN_CENTER);
            // document.add(qrCode);
            // Creator info
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
