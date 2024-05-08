/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author daoducdanh
 */
public class ConnectDB {
    
    private static ConnectDB instance = new ConnectDB();
    public static Connection con = null;
    public static ConnectDB getInstance() {
	return instance;
    }
    public void connect() throws SQLException{
	String url = "jdbc:sqlserver://localhost:1433;databasename=Cinemav123";
	String user = "sa";
	String password = "123";
	con = DriverManager.getConnection(url, user, password);
    }
    public void disconnect() {
	if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}
    }
    public static Connection getConnection() {
	return con;
    }
}
