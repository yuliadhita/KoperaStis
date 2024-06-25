/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import model.Barang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author yulia
 */
public class BarangDAO {
    private DatabaseConnection dbCon = new DbConnection();
    private Connection con;

    public List<Barang> showBarang(String query) {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM barang WHERE (idBarang LIKE "
                + "'%" + query + "%' "
                + "OR namaBarang LIKE '%" + query + "%' "
                + "OR harga LIKE '%" + query + "%' "
                + "OR kuantitas LIKE '%" + query + "%')";

        System.out.println(sql);
        System.out.println("Mengambil Data Barang");

        List<Barang> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Barang barang = new Barang(
                            Integer.parseInt(rs.getString("idBarang")),
                            Integer.parseInt(rs.getString("kuantitas")),
                            rs.getString("namaBarang"),
                            Double.parseDouble(rs.getString("harga"))
                    );
                    list.add(barang);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Reading Database");
            e.printStackTrace();
        }

        dbCon.closeConnection();
        return list;
    }

    public void insertBarang(Barang barang) {
        con = dbCon.makeConnection();
        String sql = null;

        sql = "INSERT INTO `barang` (`namaBarang`, `harga`, `kuantitas`) VALUES ('"
                + barang.getNamaBarang() + "','" + barang.getHarga()  + "'," + barang.getKuantitas() + ")";

        System.out.println("Menambah Barang");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Barang");
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Adding Barang");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public void updateBarang(int id, Barang barang) {
        con = dbCon.makeConnection();
        String sql = null;

        sql = "UPDATE barang SET namaBarang = '" + barang.getNamaBarang()
                + "', harga = '" + barang.getHarga()
                + "', kuantitas = '" + barang.getKuantitas()
                + "' WHERE idBarang = '" + barang.getIdBarang() + "'";
        System.out.println("Editing Barang");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " barang");
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Editing Barang");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public void deleteBarang(int id) {
        con = dbCon.makeConnection();
        String sql = null;

        sql = "DELETE FROM `barang` WHERE `idBarang` = " + id + "";
        System.out.println("Deleting Barang");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Deleted " + result + " barang");
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Deleting Barang");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    //tambahan
    public List<Barang> showBarangDropdown() {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM barang";
        System.out.println("Mengambil data Barang...");

        List<Barang> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Barang b = new Barang(
                            Integer.parseInt(rs.getString("idBarang")),
                            Integer.parseInt(rs.getString("kuantitas")),
                            rs.getString("namaBarang"),
                            Double.parseDouble(rs.getString("harga"))
                    );
                    list.add(b);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Eror reading database...");
            System.out.println(e);
        }
        dbCon.closeConnection();

        return list;
    }

    public int findKuantitasBarang(String namaBarang) {
        con = dbCon.makeConnection();
        String sql = "SELECT kuantitas FROM barang WHERE (namaBarang LIKE '%" + namaBarang + "%')";
        System.out.println(sql);
        System.out.println("Mengambil Kuantitas Barang");
        int kuantitas = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                kuantitas = Integer.parseInt(rs.getString("kuantitas"));
                return kuantitas;
            }
        } catch (Exception e) {
            System.out.println("Eror Reading Database");
            e.printStackTrace();
        }

        dbCon.closeConnection();
        return kuantitas;
    }

}
