/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entity.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author daoducdanh
 */
public class CartProduct {
    private Map<Product,Integer> cartProduct;
    
    public CartProduct(){
        cartProduct = new LinkedHashMap<>();
    }
    
    public void add(Product p){
        if(cartProduct.containsKey(p)){
            cartProduct.put(p, cartProduct.get(p) + 1);
        }
        else cartProduct.put(p, 1);
    }
    
    public void update(Product p, int quantity){
        cartProduct.put(p,quantity);
    }
    
    public void remove(Product p){
        cartProduct.remove(p);
    }
    
    public void removeAll(){
        cartProduct.clear();
    }
    public Map<Product, Integer> getCartProduct() {
        return cartProduct;
    }

    
    public double sumTotal(){
        double sum = 0;
        for(Map.Entry<Product, Integer> entry : cartProduct.entrySet()){
            sum += entry.getKey().getSellPrice() * entry.getValue();
        }
        return sum;
    }
}
