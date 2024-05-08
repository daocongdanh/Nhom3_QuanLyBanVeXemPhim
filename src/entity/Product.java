/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author daoducdanh
 */
public class Product {
    private String productId;
    private String name;
    private double price;
    private int unitInStock;
    private String image;
    private boolean status;
    private Category category;
    
    public Product(){
        
    }
    public Product(String productId){
        this.productId = productId;
    }

    public Product(String productId, String name, double price, int unitInStock, String image, boolean status, Category category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.unitInStock = unitInStock;
        this.image = image;
        this.status = status;
        this.category = category;
    }

    

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public double getSellPrice(){
        return this.price * 1.2;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name=" + name + ", price=" + price + ", unitInStock=" + unitInStock + ", image=" + image + ", status=" + status + '}';
    }

    
    
    
}
