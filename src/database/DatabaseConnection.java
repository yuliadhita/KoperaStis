/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package database;

import java.sql.Connection;
/**
 *
 * @author yulia
 */
public interface DatabaseConnection {
     Connection makeConnection();
     void closeConnection();
}
