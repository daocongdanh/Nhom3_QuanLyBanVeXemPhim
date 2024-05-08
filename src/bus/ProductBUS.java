/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dal.CategoryDAL;
import dal.ProductDAL;
import entity.Category;
import entity.Product;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class ProductBUS {
    private ProductDAL productDAL;
    public ProductBUS(){
        productDAL = new ProductDAL();
    }
    
    public Product getProductById(String id){
        return productDAL.findById(id);
    }
    public List<Product> getAllProduct(){
        return productDAL.findAll();
    }
    public List<Product> getAllProductSell(){
        return productDAL.findAllSell();
    }
    public List<Product> getProductByKeywordAndCategory(String keyword, String category, boolean status){
        return productDAL.findByIKeywordAndCategory(keyword, category, status);
    }
    public boolean existsByName(String name){
        return productDAL.existsByName(name);
    }
    public boolean insertProduct(Product product){
        return productDAL.insert(product);
    }
    
    public boolean updateProduct(Product product){
        return productDAL.update(product);
    }
    public boolean deteleProduct(String id){
        return productDAL.delete(id);
    }

}
