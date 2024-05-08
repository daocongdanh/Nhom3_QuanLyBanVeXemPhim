/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entity.*;

/**
 *
 * @author daoducdanh
 */
public class CurrentStaff {
    private static Staff staff;

    public static Staff getStaff() {
        return staff;
    }

    public static void setStaff(Staff staff) {
        CurrentStaff.staff = staff;
    }
    
}
