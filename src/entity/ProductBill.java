/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author daoducdanh
 */
public class ProductBill {
    private String productBillId;
    private int quantity;
    private Product product;
    private Bill bill;
    
    public ProductBill(){
        
    }

    public ProductBill(String productBillId) {
        this.productBillId = productBillId;
    }

    public ProductBill(String productBillId, int quantity, Product product, Bill bill) {
        this.productBillId = productBillId;
        this.quantity = quantity;
        this.product = product;
        this.bill = bill;
    }

    
    public String getProductBillId() {
        return productBillId;
    }

    public void setProductBillId(String productBillId) {
        this.productBillId = productBillId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    public double calcTotal(){
        return product.getSellPrice() * this.quantity;
    }

    @Override
    public String toString() {
        return "ProductBill{" + "productBillId=" + productBillId + ", quantity=" + quantity + '}';
    }
    
    
}
