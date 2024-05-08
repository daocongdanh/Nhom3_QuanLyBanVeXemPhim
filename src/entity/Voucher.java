/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author daoducdanh
 */
public class Voucher {
    private String voucherId;
    private String code;
    private boolean status;
    private LocalDateTime usedAt;
    private LocalDateTime releaseAt;
    private VoucherRelease voucherRelease;
    public Voucher(){
        
    }

    public Voucher(String voucherId) {
        this.voucherId = voucherId;
    }

    public Voucher(String voucherId, String code, boolean status, LocalDateTime usedAt, LocalDateTime releaseAt, VoucherRelease voucherRelease) {
        this.voucherId = voucherId;
        this.code = code;
        this.status = status;
        this.usedAt = usedAt;
        this.releaseAt = releaseAt;
        this.voucherRelease = voucherRelease;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public LocalDateTime getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(LocalDateTime releaseAt) {
        this.releaseAt = releaseAt;
    }

    public VoucherRelease getVoucherRelease() {
        return voucherRelease;
    }

    public void setVoucherRelease(VoucherRelease voucherRelease) {
        this.voucherRelease = voucherRelease;
    }

    @Override
    public String toString() {
        return "Voucher{" + "voucherId=" + voucherId + ", code=" + code + ", status=" + status + ", usedAt=" + usedAt + ", releaseAt=" + releaseAt + '}';
    }

    

    
    
}
