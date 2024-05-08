/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entity.Bill;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author daoducdanh
 */
public class GenerateExcel {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DecimalFormat df = new DecimalFormat("#,### đ");
    public void generateExcel(LocalDate start, LocalDate end, Map<Bill, Double> bills) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("DANH SÁCH HÓA ĐƠN");
        XSSFRow roww = null;
        Cell celll = null;
        roww = sheet.createRow(1);
        celll = roww.createCell(0, CellType.STRING);
        celll.setCellValue("DANH SÁCH HÓA ĐƠN");

        roww = sheet.createRow(2);
        celll = roww.createCell(0, CellType.STRING);
        celll.setCellValue("Từ ngày:");
        celll = roww.createCell(1, CellType.STRING);
        celll.setCellValue(dtf.format(start));
        celll = roww.createCell(2, CellType.STRING);
        celll.setCellValue("Đến ngày:");
        celll = roww.createCell(3, CellType.STRING);
        celll.setCellValue(dtf.format(end));

        XSSFRow headerRow = sheet.createRow(3);
        String[] header = {"Mã hóa đơn","Ngày lập", "Tổng tiền", "Khách hàng", "Nhân viên"};
        for(int i=0;i<header.length;i++){
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }

        int i=0;
        for(Map.Entry<Bill, Double> entry : bills.entrySet()){
            Bill bill = entry.getKey();
            double tongTien = entry.getValue();
            XSSFRow excelRow = sheet.createRow(i + 4);
            XSSFCell cell0 = excelRow.createCell(0);
            XSSFCell cell1 = excelRow.createCell(1);
            XSSFCell cell2 = excelRow.createCell(2);
            XSSFCell cell3 = excelRow.createCell(3);
            XSSFCell cell4 = excelRow.createCell(4);
            cell0.setCellValue(bill.getBillId());
            cell1.setCellValue(dtf.format(bill.getCreatedAt()));
            cell2.setCellValue(df.format(tongTien));
            cell3.setCellValue(bill.getCustomer().getName());
            cell4.setCellValue(bill.getStaff().getName());
            i++;
        }
        
        
        String fileName = "excel/HoaDon_" + dtf.format(start) + "_" + dtf.format(end) + ".xlsx";
        File f = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            workbook.write(fos);
            fos.close();
            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã tồn tại file Excel", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
