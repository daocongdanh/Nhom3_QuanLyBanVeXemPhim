
package dal;

import entity.Category;

import connectDB.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class CategoryDAL implements BaseDAL<Category>{
    private Connection con;
    public CategoryDAL(){
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try{
            String sql = "select * from Category";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String categoryId = rs.getString("CategoryId");
                String name = rs.getString("Name");
                Category category = new Category(categoryId,name);
                categories.add(category);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findById(String id) {
        try{
            String sql = "select * from Category where CategoryId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String categoryId = rs.getString("CategoryId");
                String name = rs.getString("Name");
                Category category = new Category(categoryId,name);
                return category;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Category findByName(String name) {
        try{
            String sql = "select * from Category where Name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String categoryId = rs.getString("CategoryId");
                Category category = new Category(categoryId,name);
                return category;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean insert(Category t) {
        return true;
    }

    @Override
    public boolean update(Category t) {
        return true;
    }

    @Override
    public boolean delete(String id) {
        return true;
    }

    @Override
    public String generateId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
