/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.CustomerDAL;
import entity.Customer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class CustomerBUS {
    private CustomerDAL customerDAL;
    
    public CustomerBUS(){
        customerDAL = new CustomerDAL();
    }
    
    public Customer getCustomerByPhone(String phone){
        return customerDAL.findByPhone(phone);
    }
    public Customer getCustomerById(String id){
        return customerDAL.findById(id);
    }
    public List<Customer> getAllCustomer(){
        return customerDAL.findAll();
    }
    public Map<Customer, Double> getTop10Customer(int month, int year) {
        return customerDAL.getTop10Customer(month, year);
    }
    public Map<Customer, Double> getCustomerByKeyword(String keyword){
        return customerDAL.findByKeyword(keyword);
    }
    public boolean updateCustomer(Customer customer){
        return customerDAL.update(customer);
    }
    public List<Customer> getTop5Customer(int month, int year) {
        return customerDAL.getTop5Customer(month, year);
    }
}
