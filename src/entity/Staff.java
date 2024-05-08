/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import enums.Role;
import java.time.LocalDate;

/**
 *
 * @author daoducdanh
 */
public class Staff {
    private String staffId;
    private String name;
    private String username;
    private String password;
    private String phone;
    private boolean gender;
    private LocalDate startingDate;
    private Role role;
    private String email;
    public Staff(){
        
    }

    public Staff(String staffId) {
        this.staffId = staffId;
    }

    public Staff(String staffId, String name, String username, String password, String phone, boolean gender, LocalDate startingDate, Role role,
    String email) {
        this.staffId = staffId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.startingDate = startingDate;
        this.role = role;
        this.email = email;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffId=" + staffId + ", name=" + name + ", username=" + username + ", password=" + password + ", phone=" + phone + ", gender=" + gender + ", startingDate=" + startingDate + ", role=" + role + ", email=" + email + '}';
    }
    
}
