/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enums.ObjectType;
import java.time.LocalDate;

/**
 *
 * @author daoducdanh
 */
public class VoucherRelease {
    private String voucherReleaseId;
    private String releaseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private double minPrice;
    private ObjectType objectType;
    
    public VoucherRelease(){
        
    }

    public VoucherRelease(String voucherReleaseId) {
        this.voucherReleaseId = voucherReleaseId;
    }

    public VoucherRelease(String voucherReleaseId, String releaseName, LocalDate startDate, LocalDate endDate, double price, double minPrice, ObjectType objectType) {
        this.voucherReleaseId = voucherReleaseId;
        this.releaseName = releaseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.minPrice = minPrice;
        this.objectType = objectType;
    }

    public String getVoucherReleaseId() {
        return voucherReleaseId;
    }

    public void setVoucherReleaseId(String voucherReleaseId) {
        this.voucherReleaseId = voucherReleaseId;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public String toString() {
        return "VoucherRelease{" + "voucherReleaseId=" + voucherReleaseId + ", releaseName=" + releaseName + ", startDate=" + startDate + ", finishDate=" + endDate + ", price=" + price + ", minPrice=" + minPrice + ", objectType=" + objectType + '}';
    }

    
    
}
