package dal;

import entity.Category;
import entity.Product;
import connectDB.ConnectDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daoducdanh
 */
public class ProductDAL implements BaseDAL<Product> {

    private Connection con;

    public ProductDAL() {
        ConnectDB.getInstance();
        con = ConnectDB.getConnection();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "select * from Product";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                int unitInStock = rs.getInt("UnitInStock");
                String image = rs.getString("image");
                boolean status = rs.getBoolean("Status");
                Category category = new Category(rs.getString("CategoryId"));
                Product product = new Product(productId, name, price, unitInStock, image, status,category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public boolean existsByName(String name){
        try{
            String sql = "select * from Product where Name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public List<Product> findAllSell(){
        List<Product> products = new ArrayList<>();
        try{
            String sql = "select * from Product where UnitInStock > 0 and Status = 1";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                int unitInStock = rs.getInt("UnitInStock");
                String image = rs.getString("image");
                boolean status = rs.getBoolean("Status");
                Category category = new Category(rs.getString("CategoryId"));
                Product product = new Product(productId, name, price, unitInStock, image, status,category);
                products.add(product);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return products;
    }
    @Override
    public Product findById(String id) {
        try {
            String sql = "select * from Product where ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                int unitInStock = rs.getInt("UnitInStock");
                String image = rs.getString("image");
                boolean status = rs.getBoolean("Status");
                Category category = new Category(rs.getString("CategoryId"));
                Product product = new Product(productId, name, price, unitInStock, image, status,category);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findByIKeywordAndCategory(String keyword, String categoryName, boolean status) {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "select * from Product where\n"
                    + "ProductId in (\n"
                    + "	select p.ProductId from Product p \n"
                    + "	join Category c on p.CategoryId = c.CategoryId\n"
                    + "	where p.Name like ? and Status = ? ";
            if(!categoryName.equals("Tất cả")){
                sql += "and c.Name = ? ";
            }
            sql += ")";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setBoolean(2, status);
            if(!categoryName.equals("Tất cả")){
                pst.setString(3, categoryName);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                int unitInStock = rs.getInt("UnitInStock");
                String image = rs.getString("image");
                Category category = new Category(rs.getString("CategoryId"));
                Product product = new Product(productId, name, price, unitInStock, image, status,category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean insert(Product product) {
        try {
            String sql = "insert into Product values(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, generateId());
            pst.setString(2, product.getName());
            pst.setDouble(3, product.getPrice());
            pst.setInt(4, product.getUnitInStock());
            pst.setString(5, product.getImage());
            pst.setBoolean(6, product.isStatus());
            pst.setString(7, product.getCategory().getCategoryId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        try {
            String sql = "update Product set Name = ?, Price = ?, UnitInStock = ?, "
                    + "Image = ?, Status = ?,  CategoryId = ? where ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, product.getName());
            pst.setDouble(2, product.getPrice());
            pst.setInt(3, product.getUnitInStock());
            pst.setString(4, product.getImage());
            pst.setBoolean(5, product.isStatus());
            pst.setString(6, product.getCategory().getCategoryId());
            pst.setString(7, product.getProductId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "update Product set Status = 0 where ProductId = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String generateId() {
        String id = "";
        try {
            String sql = "select max(ProductId) from Product";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String maxProductId = rs.getString(1);
            if (maxProductId == null) {
                return "SP001";
            } else {
                int num = Integer.parseInt(maxProductId.substring(2)) + 1;
                id = String.format("SP%03d", num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
