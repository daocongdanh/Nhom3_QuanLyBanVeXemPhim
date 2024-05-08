/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author Hoang
 */
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.itextpdf.layout.property.Property.FONT;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.draw.LineSeparator;
import entity.Bill;
import entity.Movie;
import entity.Product;
import entity.ProductBill;
import entity.Room;
import entity.Ticket;
import entity.Voucher;
import enums.SeatType;
import gui.custom.CustomDashedLineSeparator;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import util.PDFImageViewer;

public class GeneratePDF {

    private DecimalFormat decimal = new DecimalFormat("#,### đ");
    private DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    public GeneratePDF() {

    }

    ;
    public void GeneratePDF(Bill bill, Room room, Movie movie, Voucher voucher) throws IOException, DocumentException {
        String billName = bill.getBillId();
        String path = "bill/hoaDon" + billName + ".pdf";
        File fontFile = new File("data/font/vuArial.ttf");
        BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Document document = new Document();
//        float newHeight = 900; // Chiều cao mới của tài liệu (đơn vị là pixel)
//        document.setPageSize(new Rectangle(document.getPageSize().getWidth(), newHeight));
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            CustomDashedLineSeparator dotLine = new CustomDashedLineSeparator();
            dotLine.setDash(10);
            dotLine.setGap(7);
            dotLine.setLineWidth(1);

            // Font
            Font titleFont = new Font(bf, 18, Font.BOLD);
            Font titleFontMini = new Font(bf, 11, Font.BOLD);
            Font normalFont = new Font(bf, 12, Font.NORMAL);
            Font italicBoldFont = new Font(bf, 11, Font.BOLDITALIC);

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
            creatorInfo.add(new Chunk("Nhân viên: ", normalFont));
            creatorInfo.add(new Chunk(bill.getStaff().getName(), titleFontMini));
            Paragraph creatorInfo2 = new Paragraph();
            creatorInfo2.add(new Chunk("Ngày lập: ", normalFont));
            creatorInfo2.add(new Chunk(formatTime.format(bill.getCreatedAt()), normalFont));
            creatorInfo.setAlignment(Element.ALIGN_LEFT);
            creatorInfo2.setAlignment(Element.ALIGN_LEFT);

            // Buyer details
            Paragraph buyerInfo = new Paragraph();
            buyerInfo.add(new Chunk("Khách hàng: ", normalFont));
            buyerInfo.add(new Chunk(bill.getCustomer().getName(), titleFontMini)); // Thay bằng tên người mua thực tế
            Paragraph buyerInfo2 = new Paragraph();
            buyerInfo2.add(new Chunk("Điện thoại: ", normalFont));
            buyerInfo2.add(new Chunk(bill.getCustomer().getPhone(), normalFont)); // Thay bằng số điện thoại thực tế
            buyerInfo.setAlignment(Element.ALIGN_LEFT);
            buyerInfo2.setAlignment(Element.ALIGN_LEFT);

            PdfPTable inforTable = new PdfPTable(2);
            inforTable.setSpacingBefore(10f);
            inforTable.setSpacingAfter(15f);
            inforTable.setWidthPercentage(100);
            PdfPCell inforNV = new PdfPCell(creatorInfo);
            inforNV.setBorder(Rectangle.NO_BORDER);
            PdfPCell inforKH = new PdfPCell(buyerInfo);
            inforKH.setBorder(Rectangle.NO_BORDER);
            inforKH.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell inforNV2 = new PdfPCell(creatorInfo2);
            inforNV2.setBorder(Rectangle.NO_BORDER);
            PdfPCell inforKH2 = new PdfPCell(buyerInfo2);
            inforKH2.setBorder(Rectangle.NO_BORDER);
            inforKH2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            inforTable.addCell(inforNV);
            inforTable.addCell(inforKH);
            inforTable.addCell(inforNV2);
            inforTable.addCell(inforKH2);
            document.add(inforTable);
            document.add(dotLine);
            // Movie details
            Paragraph movieTitle = new Paragraph(" Vé phim", titleFontMini);
            movieTitle.setSpacingBefore(10f);
            movieTitle.setSpacingAfter(5f);
            Paragraph movieDetails = new Paragraph();
            movieDetails.add(new Chunk(" Phim: ", normalFont));
            movieDetails.add(new Chunk(movie.getName(), italicBoldFont));
            movieDetails.add(Chunk.NEWLINE);
            LocalDateTime showtimeStartTime = bill.getTickets().get(0).getShowtime().getStartTime();
            movieDetails.add(new Chunk(" Ngày chiếu: ", normalFont));
            movieDetails.add(new Chunk(showtimeStartTime.getDayOfMonth() + "/"
                    + showtimeStartTime.getMonthValue() + "/"
                    + showtimeStartTime.getYear(), normalFont));
            movieDetails.add(Chunk.TABBING);
            movieDetails.add(Chunk.TABBING);
            movieDetails.add(new Chunk(" Suất chiếu: ", normalFont));
            movieDetails.add(new Chunk(dtf.format(bill.getTickets().get(0).getShowtime().getStartTime()), normalFont));
            movieDetails.add(Chunk.NEWLINE);
            movieDetails.add(new Chunk(" Phòng: ", normalFont));
            movieDetails.add(new Chunk(room.getName(), normalFont));
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

            // Add ticket data
            ArrayList<String> gheVip = new ArrayList<String>();
            ArrayList<String> gheThuong = new ArrayList<String>();
            double giaGheVip = 0;
            double giaGheThuong = 0;
            for (Ticket item : bill.getTickets()) {
                if (item.getSeat().getSeatType().equals(SeatType.STANDARD)) {
                    gheThuong.add(item.getSeat().getName());
                    giaGheThuong = item.calcTotal();
                } else {
                    gheVip.add(item.getSeat().getName());
                    giaGheVip = item.calcTotal();
                }
            }
            if (gheThuong.size() != 0) {
                String listGheThuong = "";
                for (int i = 0; i < gheThuong.size(); i++) {
                    if (i + 1 == gheThuong.size()) {
                        listGheThuong += gheThuong.get(i);
                    } else {
                        listGheThuong += gheThuong.get(i) + ", ";
                    }
                }
                PdfPCell seatTypeCell = new PdfPCell(new Phrase("STANDARD", normalFont));
                seatTypeCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell seatNameCell = new PdfPCell(new Phrase(listGheThuong, normalFont));
                seatNameCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell unitPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheThuong), normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell totalPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheThuong * gheThuong.size()), normalFont));
                totalPriceCell.setBorder(Rectangle.NO_BORDER);

                ticketTable.addCell(seatTypeCell);
                ticketTable.addCell(seatNameCell);
                ticketTable.addCell(unitPriceCell);
                ticketTable.addCell(totalPriceCell);
            }

            if (gheVip.size() != 0) {
                String listGheVip = "";
                for (int i = 0; i < gheVip.size(); i++) {
                    if (i + 1 == gheVip.size()) {
                        listGheVip += gheVip.get(i);
                    } else {
                        listGheVip += gheVip.get(i) + ", ";
                    }
                }
                PdfPCell seatTypeCell = new PdfPCell(new Phrase("VIP", normalFont));
                seatTypeCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell seatNameCell = new PdfPCell(new Phrase(listGheVip, normalFont));
                seatNameCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell unitPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheVip), normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell totalPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheVip * gheVip.size()), normalFont));
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

            PdfPCell totalTicketPriceValueCell = new PdfPCell(new Phrase(decimal.format(bill.calcTicketBill()), normalFont));
            totalTicketPriceValueCell.setBorder(Rectangle.NO_BORDER);
            ticketTable.addCell(totalTicketPriceValueCell);

            document.add(ticketTable);
            document.add(Chunk.NEWLINE);
            document.add(dotLine);

            // Product details
            Paragraph productDetails = new Paragraph(" Sản phẩm mua kèm", titleFontMini);
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
            for (ProductBill item : bill.getProductBills()) {
                PdfPCell productNameCell = new PdfPCell(new Phrase(item.getProduct().getName(), normalFont));
                productNameCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(productNameCell);

                PdfPCell quantityCell = new PdfPCell(new Phrase(item.getQuantity() + "", normalFont));
                quantityCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(quantityCell);

                PdfPCell unitPriceCell = new PdfPCell(new Phrase(decimal.format(item.getProduct().getSellPrice()) + "", normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(unitPriceCell);

                PdfPCell totalPriceCell = new PdfPCell(new Phrase(decimal.format(item.calcTotal()) + "", normalFont));
                totalPriceCell.setBorder(Rectangle.NO_BORDER);
                productTable.addCell(totalPriceCell);
            }

            PdfPCell totalProductPriceCell = new PdfPCell(new Phrase("Tổng tiền sản phẩm", normalFont));
            totalProductPriceCell.setColspan(3);
            totalProductPriceCell.setBorder(Rectangle.NO_BORDER);
            productTable.addCell(totalProductPriceCell);

            PdfPCell totalProductPriceValueCell = new PdfPCell(new Phrase(decimal.format(bill.calcProductBill()), normalFont));
            totalProductPriceValueCell.setBorder(Rectangle.NO_BORDER);
            productTable.addCell(totalProductPriceValueCell);

            document.add(productTable);
            document.add(Chunk.NEWLINE);
            document.add(dotLine);
            // Total

            Paragraph total = new Paragraph();
            total.add(new Chunk("Tổng hóa đơn: ", normalFont));
            total.add(new Chunk(decimal.format(bill.calcProductBill() + bill.calcTicketBill()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            double tienGiam = 0;
            if (voucher != null) {
                tienGiam = voucher.getVoucherRelease().getPrice();
            }
            total.add(new Chunk("Giảm giá: ", normalFont));
            total.add(new Chunk(decimal.format(tienGiam), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Thuế: ", normalFont));
            total.add(new Chunk(decimal.format(bill.getVAT()
                    * (bill.calcProductBill() + bill.calcTicketBill())), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Tổng tiền: ", normalFont));
            total.add(new Chunk(decimal.format(bill.getTotalPrice()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
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
        PDFImageViewer pdfView = new PDFImageViewer("bill/hoaDon" + billName + ".pdf");

    }

    public void GeneratePDFOnlyMovie(Bill bill, Room room, Movie movie, Voucher voucher) throws IOException, DocumentException {
     String billName = bill.getBillId();
        String path = "bill/hoaDon" + billName + ".pdf";
        File fontFile = new File("data/font/vuArial.ttf");
        BaseFont bf = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Document document = new Document();
//        float newHeight = 900; // Chiều cao mới của tài liệu (đơn vị là pixel)
//        document.setPageSize(new Rectangle(document.getPageSize().getWidth(), newHeight));
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            CustomDashedLineSeparator dotLine = new CustomDashedLineSeparator();
            dotLine.setDash(10);
            dotLine.setGap(7);
            dotLine.setLineWidth(1);

            // Font
            Font titleFont = new Font(bf, 18, Font.BOLD);
            Font titleFontMini = new Font(bf, 11, Font.BOLD);
            Font normalFont = new Font(bf, 12, Font.NORMAL);
            Font italicBoldFont = new Font(bf, 11, Font.BOLDITALIC);

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
            creatorInfo.add(new Chunk("Nhân viên: ", normalFont));
            creatorInfo.add(new Chunk(bill.getStaff().getName(), titleFontMini));
            Paragraph creatorInfo2 = new Paragraph();
            creatorInfo2.add(new Chunk("Ngày lập: ", normalFont));
            creatorInfo2.add(new Chunk(formatTime.format(bill.getCreatedAt()), normalFont));
            creatorInfo.setAlignment(Element.ALIGN_LEFT);
            creatorInfo2.setAlignment(Element.ALIGN_LEFT);

            // Buyer details
            Paragraph buyerInfo = new Paragraph();
            buyerInfo.add(new Chunk("Khách hàng: ", normalFont));
            buyerInfo.add(new Chunk(bill.getCustomer().getName(), titleFontMini)); // Thay bằng tên người mua thực tế
            Paragraph buyerInfo2 = new Paragraph();
            buyerInfo2.add(new Chunk("Điện thoại: ", normalFont));
            buyerInfo2.add(new Chunk(bill.getCustomer().getPhone(), normalFont)); // Thay bằng số điện thoại thực tế
            buyerInfo.setAlignment(Element.ALIGN_LEFT);
            buyerInfo2.setAlignment(Element.ALIGN_LEFT);

            PdfPTable inforTable = new PdfPTable(2);
            inforTable.setSpacingBefore(10f);
            inforTable.setSpacingAfter(15f);
            inforTable.setWidthPercentage(100);
            PdfPCell inforNV = new PdfPCell(creatorInfo);
            inforNV.setBorder(Rectangle.NO_BORDER);
            PdfPCell inforKH = new PdfPCell(buyerInfo);
            inforKH.setBorder(Rectangle.NO_BORDER);
            inforKH.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell inforNV2 = new PdfPCell(creatorInfo2);
            inforNV2.setBorder(Rectangle.NO_BORDER);
            PdfPCell inforKH2 = new PdfPCell(buyerInfo2);
            inforKH2.setBorder(Rectangle.NO_BORDER);
            inforKH2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            inforTable.addCell(inforNV);
            inforTable.addCell(inforKH);
            inforTable.addCell(inforNV2);
            inforTable.addCell(inforKH2);
            document.add(inforTable);
            document.add(dotLine);
            // Movie details
            Paragraph movieTitle = new Paragraph(" Vé phim", titleFontMini);
            movieTitle.setSpacingBefore(10f);
            movieTitle.setSpacingAfter(5f);
            Paragraph movieDetails = new Paragraph();
            movieDetails.add(new Chunk(" Phim: ", normalFont));
            movieDetails.add(new Chunk(movie.getName(), italicBoldFont));
            movieDetails.add(Chunk.NEWLINE);
            LocalDateTime showtimeStartTime = bill.getTickets().get(0).getShowtime().getStartTime();
            movieDetails.add(new Chunk(" Ngày chiếu: ", normalFont));
            movieDetails.add(new Chunk(showtimeStartTime.getDayOfMonth() + "/"
                    + showtimeStartTime.getMonthValue() + "/"
                    + showtimeStartTime.getYear(), normalFont));
            movieDetails.add(Chunk.TABBING);
            movieDetails.add(Chunk.TABBING);
            movieDetails.add(new Chunk(" Suất chiếu: ", normalFont));
            movieDetails.add(new Chunk(dtf.format(bill.getTickets().get(0).getShowtime().getStartTime()), normalFont));
            movieDetails.add(Chunk.NEWLINE);
            movieDetails.add(new Chunk(" Phòng: ", normalFont));
            movieDetails.add(new Chunk(room.getName(), normalFont));
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

            // Add ticket data
            ArrayList<String> gheVip = new ArrayList<String>();
            ArrayList<String> gheThuong = new ArrayList<String>();
            double giaGheVip = 0;
            double giaGheThuong = 0;
            for (Ticket item : bill.getTickets()) {
                if (item.getSeat().getSeatType().equals(SeatType.STANDARD)) {
                    gheThuong.add(item.getSeat().getName());
                    giaGheThuong = item.calcTotal();
                } else {
                    gheVip.add(item.getSeat().getName());
                    giaGheVip = item.calcTotal();
                }
            }
            if (gheThuong.size() != 0) {
                String listGheThuong = "";
                for (int i = 0; i < gheThuong.size(); i++) {
                    if (i + 1 == gheThuong.size()) {
                        listGheThuong += gheThuong.get(i);
                    } else {
                        listGheThuong += gheThuong.get(i) + ", ";
                    }
                }
                PdfPCell seatTypeCell = new PdfPCell(new Phrase("STANDARD", normalFont));
                seatTypeCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell seatNameCell = new PdfPCell(new Phrase(listGheThuong, normalFont));
                seatNameCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell unitPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheThuong), normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell totalPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheThuong * gheThuong.size()), normalFont));
                totalPriceCell.setBorder(Rectangle.NO_BORDER);

                ticketTable.addCell(seatTypeCell);
                ticketTable.addCell(seatNameCell);
                ticketTable.addCell(unitPriceCell);
                ticketTable.addCell(totalPriceCell);
            }

            if (gheVip.size() != 0) {
                String listGheVip = "";
                for (int i = 0; i < gheVip.size(); i++) {
                    if (i + 1 == gheVip.size()) {
                        listGheVip += gheVip.get(i);
                    } else {
                        listGheVip += gheVip.get(i) + ", ";
                    }
                }
                PdfPCell seatTypeCell = new PdfPCell(new Phrase("VIP", normalFont));
                seatTypeCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell seatNameCell = new PdfPCell(new Phrase(listGheVip, normalFont));
                seatNameCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell unitPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheVip), normalFont));
                unitPriceCell.setBorder(Rectangle.NO_BORDER);
                PdfPCell totalPriceCell = new PdfPCell(new Phrase(decimal.format(giaGheVip * gheVip.size()), normalFont));
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

            PdfPCell totalTicketPriceValueCell = new PdfPCell(new Phrase(decimal.format(bill.calcTicketBill()), normalFont));
            totalTicketPriceValueCell.setBorder(Rectangle.NO_BORDER);
            ticketTable.addCell(totalTicketPriceValueCell);

            document.add(ticketTable);
            document.add(Chunk.NEWLINE);
            document.add(dotLine);

            // Total

            Paragraph total = new Paragraph();
            total.add(new Chunk("Tổng hóa đơn: ", normalFont));
            total.add(new Chunk(decimal.format(bill.calcProductBill() + bill.calcTicketBill()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            double tienGiam = 0;
            if (voucher != null) {
                tienGiam = voucher.getVoucherRelease().getPrice();
            }
            total.add(new Chunk("Giảm giá: ", normalFont));
            total.add(new Chunk(decimal.format(tienGiam), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Thuế: ", normalFont));
            total.add(new Chunk(decimal.format(bill.getVAT()
                    * (bill.calcProductBill() + bill.calcTicketBill())), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            total.add(Chunk.NEWLINE);
            total.add(new Chunk("Tổng tiền: ", normalFont));
            total.add(new Chunk(decimal.format(bill.getTotalPrice()), FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
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
        PDFImageViewer pdfView = new PDFImageViewer("bill/hoaDon" + billName + ".pdf");

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] pdfData = byteArrayOutputStream.toByteArray();
//        try {
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfData);
//            File previewFile = new File("bill/hoaDon" + billName + ".pdf");
//            Desktop.getDesktop().open(previewFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}
