/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.CategoryDAL;
import entity.Category;

/**
 *
 * @author daoducdanh
 */
public class CategoryBUS {
    private CategoryDAL categoryDAL;
    
    public CategoryBUS(){
        categoryDAL = new CategoryDAL();
    }
    
    public Category getCategoryByName(String name){
        return categoryDAL.findByName(name);
    }
    public Category getCategoryById(String id){
        return categoryDAL.findById(id);
    }
}
