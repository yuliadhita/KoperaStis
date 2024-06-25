package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection implements DatabaseConnection {
    private final String DB_URL = "jdbc:sqlite:C:/Users/yulia/Downloads/result/data"; // Change to your database URL
    private Connection con;

    @Override
    public Connection makeConnection() {
        try {
            con = DriverManager.getConnection(DB_URL);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return con;
    }

    @Override
    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed!");
            } catch (SQLException e) {
                System.out.println("Failed to close connection!");
                e.printStackTrace();
            }
        }
    }
}
