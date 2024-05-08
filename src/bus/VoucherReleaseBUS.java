/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.VoucherReleaseDAL;
import entity.VoucherRelease;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class VoucherReleaseBUS {
    private VoucherReleaseDAL voucherReleaseDAL;
    public VoucherReleaseBUS(){
        voucherReleaseDAL = new VoucherReleaseDAL();
    }
    
    public List<VoucherRelease> getAllVoucherRelease(){
        return voucherReleaseDAL.findAll();
    }
    public boolean deleteVoucherRelease(String id){
        return voucherReleaseDAL.delete(id);
    }
    public boolean insertVoucherRelease(VoucherRelease voucherRelease){
        return voucherReleaseDAL.insert(voucherRelease);
    }
    public VoucherRelease getVoucherReleaseById(String id){
        return voucherReleaseDAL.findById(id);
    }
    public List<VoucherRelease> getVoucherByKeyword(String keyword){
        return voucherReleaseDAL.findByKeyword(keyword);
    }
    public boolean updateVoucherRelease(VoucherRelease voucherRelease){
        return voucherReleaseDAL.update(voucherRelease);
    }
    public boolean checkModify(String id){
        return voucherReleaseDAL.checkModify(id);
    }
}
