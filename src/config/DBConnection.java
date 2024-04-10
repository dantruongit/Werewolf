package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;
    private static DBConnection db = new DBConnection();
    private DBConnection(){}
    
    public static DBConnection gI(){
        return db;
    }
    
    public void getConnection() {
        String url = "jdbc:mysql://localhost:3306/Ecommerce";
        String username = "root";
        String password = "admin";
        try {
            // Kết nối MySQL
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công đến MySQL!");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại! Lỗi: " + e.getMessage());
            System.exit(0);
        }
    }
    
    public void closeConnection(){
        try {
            connection.close();
        } catch (Exception e) {
        }
    }
}