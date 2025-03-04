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
import model.Pengguna;
import model.Transaksi;
import model.User;
import model.Staff;
import model.Role;
import model.Barang;
/**
 *
 * @author yulia
 */
public class TransaksiDAO {
    private DatabaseConnection dbCon = new DbConnection();
    private Connection con;

    public void insertTransaksi(Transaksi t) {
        con = dbCon.makeConnection();

        String sql = "INSERT INTO transaksi(NIP, idBarang, idPengguna, tanggalPembelian, jumlah) VALUES ('"
                + t.getStaff().getNIP() + "', '"
                + t.getBarang().getIdBarang() + "', '"
                + t.getPengguna().getIdPengguna() + "', '"
                + t.getTanggalPembelian() + "', '"
                + t.getJumlah() + "')";
        System.out.println("Adding Transaksi...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Transaksi");
            statement.close();
        } catch (Exception e) {
            System.out.println("Error adding Transaksi...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }

    public List<Transaksi> showTransaksi(String query) {
        con = dbCon.makeConnection();

        String sql = "SELECT t.*, s.*, b.*, p.*, r.*, u.* "
                + "FROM transaksi as t "
                + "JOIN staff as s ON t.NIP = s.NIP "
                + "JOIN barang as b ON t.idBarang = b.idBarang "
                + "JOIN pengguna as p ON t.idPengguna = p.idPengguna "
                + "JOIN role as r ON r.idRole = s.idRole "
                + "JOIN user as u ON u.idUser = s.idUser "
                + "WHERE (t.idTransaksi LIKE "
                + "'%" + query + "%'"
                + "OR t.tanggalPembelian LIKE '%" + query + "%'"
                + "OR p.nama LIKE '%" + query + "%'"
                + "OR s.nama LIKE '%" + query + "%'"
                + "OR b.namaBarang LIKE '%" + query + "%'"
                + "OR b.harga LIKE '%" + query + "%'"
                + "OR t.jumlah LIKE '%" + query + "%')";

        System.out.println("Mengambil data Transaksi...");
        System.out.println(sql);
        List<Transaksi> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    Role r = new Role(rs.getInt("r.idRole"),  rs.getString("r.namaRole"));

                    User u = new User(
                            Integer.parseInt(rs.getString("u.idUser")),
                            rs.getString("u.username"),
                            rs.getString("u.password")
                    );

                    Staff s = new Staff(
                            rs.getInt("s.NIP"),
                            rs.getString("s.nama"),
                            rs.getString("s.tahunMasuk"),
                            rs.getString("s.NoTelp"),
                            rs.getString("s.alamat"), r, u
                    );

                    Barang b = new Barang(
                            Integer.parseInt(rs.getString("idBarang")),
                            Integer.parseInt(rs.getString("kuantitas")),
                            rs.getString("namaBarang"),
                            Double.parseDouble(rs.getString("harga"))
                    );

                    Pengguna p = new Pengguna(
                            Integer.parseInt(rs.getString("p.idPengguna")),
                            rs.getString("p.nama"),
                            rs.getString("p.noTelp"),
                            rs.getString("p.alamat"), u);

                    Transaksi t = new Transaksi(
                            Integer.parseInt(rs.getString("t.idTransaksi")),
                            Integer.parseInt(rs.getString("t.jumlah")),
                            rs.getString("t.tanggalPembelian"),
                            s, b, p);

                    list.add(t);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error reading database...");
            System.out.println(e);
        }
        dbCon.closeConnection();

        return list;

    }

    //cek null pengguna
    public int cekNull(int idPengguna) {
        con = dbCon.makeConnection();

        String sql = "SELECT idTransaksi from transaksi WHERE (idPengguna LIKE '%" + idPengguna + "%')";
        System.out.println(sql);
        System.out.println("Mengambil id transaksi");
        int cek = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    cek = 1;
                    return cek;
                }
            }
        } catch (Exception e) {
            System.out.println("Eror Reading Database");
            e.printStackTrace();
        }
        dbCon.closeConnection();
        return cek;
    }

    //cek null staff
    public int cekStaffNull(int nip) {
        con = dbCon.makeConnection();

        String sql = "SELECT idTransaksi from transaksi WHERE (nip LIKE '%" + nip + "%')";
        System.out.println(sql);
        System.out.println("Mengambil id transaksi");
        int cek = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    cek = 1;
                    return cek;
                }
            }
        } catch (Exception e) {
            System.out.println("Eror Reading Database");
            e.printStackTrace();
        }
        dbCon.closeConnection();
        return cek;
    }

    //cek null barang
    public int cekBarangNull(int idBarang) {
        con = dbCon.makeConnection();

        String sql = "SELECT idTransaksi from transaksi WHERE (idBarang = " + idBarang + ")";
        System.out.println(sql);
        System.out.println("Mengambil id transaksi");
        int cek = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    cek = 1;
                    return cek;
                }
            }
        } catch (Exception e) {
            System.out.println("Eror Reading Database");
            e.printStackTrace();
        }
        dbCon.closeConnection();
        return cek;
    }

}
