/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Role;
/**
 *
 * @author yulia
 */
public class RoleDAO {
    private DatabaseConnection dbCon = new DbConnection();
    private Connection con;

    public List<Role> showRole() {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM role";

        System.out.println(sql);
        System.out.println("Mengambil Data Role");

        List<Role> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Role role = new Role(
                            Integer.parseInt(rs.getString("idRole")),
                            rs.getString("namaRole"));
                    list.add(role);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Reading Databse");
            e.printStackTrace();
        }

        dbCon.closeConnection();
        return list;
    }
}
