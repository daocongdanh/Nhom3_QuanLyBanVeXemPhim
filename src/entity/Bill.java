/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author daoducdanh
 */
public class Bill {
    private String billId;
    private LocalDate createdAt;
    private Customer customer;
    private Staff staff;
    private List<ProductBill> productBills;
    private List<Ticket> tickets;
    private Voucher voucher;
    private double totalPrice;
    private final double VAT = 0.1;
    public Bill(){
        
    }

    public Bill(String billId) {
        this.billId = billId;
    }

    public Bill(String billId, LocalDate createdAt, Customer customer, Staff staff, List<ProductBill> productBills, List<Ticket> tickets, Voucher voucher) {
        this.billId = billId;
        this.createdAt = createdAt;
        this.customer = customer;
        this.staff = staff;
        this.productBills = productBills;
        this.tickets = tickets;
        this.voucher = voucher;
        setTotalPrice();
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = (calcProductBill() + calcTicketBill())*(1+VAT) 
                - ((voucher == null) ? 0 : (voucher.getVoucherRelease().getPrice()));
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ProductBill> getProductBills() {
        return productBills;
    }

    public void setProductBills(List<ProductBill> productBills) {
        this.productBills = productBills;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public double getVAT() {
        return VAT;
    }


    public double calcProductBill(){
        return productBills == null ? 0 : productBills.stream()
                .collect(Collectors.summingDouble(ProductBill::calcTotal));
    }
    public double calcTicketBill(){
        return tickets.stream()
                .collect(Collectors.summingDouble(Ticket::calcTotal));
    }

    @Override
    public String toString() {
        return "Bill{" + "billId=" + billId + ", createdAt=" + createdAt + ", VAT=" + VAT + '}';
    }

    
}
