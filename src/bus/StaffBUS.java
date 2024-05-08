/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.StaffDAL;
import entity.Staff;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class StaffBUS {

    
    private StaffDAL staffDAL;
    public StaffBUS(){
        staffDAL = new StaffDAL();
    }
    public Map<Staff, Double> getStaffByKeyword(String keyword) {
        return staffDAL.findByKeyword(keyword);
    }
    public Staff checkLogin(String username, String password){
        return staffDAL.findByUsernameAndPassword(username, password);
    }
    
    public Staff getStaffById(String id){
        return staffDAL.findById(id);
    }
    public Staff getStaffUsername(String username){
        return staffDAL.findByUsername(username);
    }
    public boolean updateStaff(Staff staff){
        return staffDAL.update(staff);
    }
    public boolean insertStaff(Staff staff){
        return staffDAL.insert(staff);
    }
    public Staff checkReset(String username, String mail) {
        return staffDAL.findByUsernameAndEmail(username, mail);
    }
    public Map<Staff, Double> getTop10Staff(int month, int year) {
        return staffDAL.getTop10Staff(month, year);
    }
}
