/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.BillDAL;
import dal.CustomerDAL;
import dal.MovieDAL;
import dal.ProductBillDAL;
import dal.RoomDAL;
import dal.ShowtimeDAL;
import dal.StaffDAL;
import dal.TicketDAL;
import dal.VoucherDAL;
import dal.VoucherReleaseDAL;
import entity.Bill;
import entity.Customer;
import entity.Movie;
import entity.Product;
import entity.ProductBill;
import entity.Room;
import entity.Seat;
import entity.Staff;
import entity.Ticket;
import entity.Voucher;
import gui.GeneratePDF;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class BillBUS {
    
    private BillDAL billDAL;
    private ShowtimeDAL showtimeDAL;
    private CustomerDAL customerDAL;
    private VoucherDAL voucherDAL;
    private TicketDAL ticketDAL;
    private ProductBillDAL productBillDAL;
    private VoucherReleaseDAL voucherReleaseDAL;
    private StaffDAL staffDAL;
    private MovieDAL movieDAL;
    private RoomDAL roomDAL;
    
    public BillBUS(){
        billDAL = new BillDAL();
        showtimeDAL = new ShowtimeDAL();
        customerDAL = new CustomerDAL();
        staffDAL = new StaffDAL();
        voucherDAL = new VoucherDAL();
        ticketDAL = new TicketDAL();
        productBillDAL = new ProductBillDAL();
        voucherReleaseDAL = new VoucherReleaseDAL();
        movieDAL = new MovieDAL();
        roomDAL = new RoomDAL();
    }

    public boolean insertBill(Customer customer, Staff staff, String code, 
            List<Seat> cartSeat, Map<Product, Integer> cartProduct, String showtimeID) {
        Customer cus = customerDAL.findByPhone(customer.getPhone());
        if(cus == null){
            if(!customerDAL.insert(customer))
                return false;
        }
        cus = customerDAL.findByPhone(customer.getPhone());
        Voucher voucher = code.equals("") == true ? null : voucherDAL.findByCode(code);
        List<Ticket> tickets = cartSeat.stream()
                .map(s -> new Ticket(null, null, showtimeDAL.findById(showtimeID), s))
                .toList();
        List<ProductBill> productBills = new ArrayList<>();
        for(Map.Entry<Product, Integer> entry : cartProduct.entrySet()){
            Product product = entry.getKey();
            int qty = entry.getValue();
            productBills.add(new ProductBill(null, qty, product, null));
        }
        if(voucher != null){
            voucher.setVoucherRelease(voucherReleaseDAL.findById(voucher.getVoucherRelease().getVoucherReleaseId()));
        }
        Bill bill = new Bill(null, LocalDate.now(), cus, staff, productBills, tickets, voucher);
        Bill billNew = billDAL.insert(bill);
        if(billNew == null)
            return false;
        for(Ticket ticket : tickets){
            ticket.setBill(billNew);
            if(!ticketDAL.insert(ticket)){
                return false;
            }
        }
        for(ProductBill productBill : productBills){
            productBill.setBill(billNew);
            if(!productBillDAL.insert(productBill)){
                return false;
            }
        }
        if(voucher != null){
            voucher.setStatus(true);
            voucher.setUsedAt(LocalDateTime.now());
            if(!voucherDAL.update(voucher))
                return false;
        }
        Movie movie = movieDAL.findById( tickets.get(0).getShowtime().getMovie().getMovieId());
        Room room = roomDAL.findById(cartSeat.get(0).getRoom().getRoomId());
        try {
            GeneratePDF pdf = new GeneratePDF();
            if (productBills.size() == 0) {
                pdf.GeneratePDFOnlyMovie(bill,room , movie, voucher);
            } else {
                pdf.GeneratePDF(bill,room , movie, voucher );
            }
        }catch ( Exception e ){
            System.out.println(e);
        }
        return true;

    }
    public Map<Bill, Double> getBillByDate(LocalDate start, LocalDate end){
        Map<Bill, Double> bills = billDAL.findByDate(start, end);
        Map<Bill, Double> billNew = new LinkedHashMap<>();
        for(Map.Entry<Bill, Double> entry : bills.entrySet()){
            Customer customer = customerDAL.findById(entry.getKey().getCustomer().getCustomerId());
            Staff staff = staffDAL.findById(entry.getKey().getStaff().getStaffId());
            Bill bill = entry.getKey();
            bill.setCustomer(customer);
            bill.setStaff(staff);
            billNew.put(bill, entry.getValue());
        }
        return billNew;
    }

}
