/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yulia
 */
public class DbConnection implements DatabaseConnection{
    private final String DB_URL = "jdbc:mysql://localhost:3306/koperastis"; // Change to your database URL
    private final String USER = "root"; // Change to your database username
    private final String PASS = ""; // Change to your database password
    private Connection con;

    public Connection makeConnection() {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return con;
    }

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
