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
import model.Barang;
import model.PengadaanBarang;
import model.Staff;
import model.User;
import model.Role;
/**
 *
 * @author yulia
 */
public class PengadaanBarangDAO {
    private DatabaseConnection dbCon = new DbConnection();
    private Connection con;

    public void insertPengadaanBarang(PengadaanBarang pb) {
        con = dbCon.makeConnection();

        String sql
                = "INSERT INTO pengadaanbarang"
                + "(idBarang,nip,kuantitas,supplier,tanggalPengadaan) "
                + "VALUES ("
                + "'" + pb.getBarang().getIdBarang() + "', "
                + "'" + pb.getStaff().getNIP() + "', "
                + "'" + pb.getKuantitas() + "', "
                + "'" + pb.getSupplier() + "', "
                + "'" + pb.getTanggalPengadaan() + "')";

        System.out.println("Adding Stok Barang...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Stok Barang");
            statement.close();
        } catch (Exception e) {
            System.out.println("[!] Error Adding Stok Barang...");
            System.out.println(e);
        }
        dbCon.closeConnection();

    }

    public List<PengadaanBarang> showPengadaanBarang(String query) {
        con = dbCon.makeConnection();

        String sql
                = "SELECT pb.*, b.*, s.*, r.*, u.* "
                + "FROM pengadaanbarang AS pb "
                + "JOIN barang AS b "
                + "ON pb.idBarang = b.idBarang "
                + "JOIN staff AS s "
                + "ON pb.nip = s.nip "
                + "JOIN role AS r "
                + "ON s.idRole = r.idRole "
                + "JOIN user AS u "
                + "ON s.idUser = u.idUser "
                + "WHERE ( "
                + "pb.idPengadaan LIKE '%" + query + "%' "
                + "OR b.namaBarang LIKE '%" + query + "%' "
                + "OR s.nama LIKE '%" + query + "%' "
                + "OR pb.kuantitas LIKE '%" + query + "%' "
                + "OR pb.supplier LIKE '%" + query + "%' "
                + "OR pb.tanggalPengadaan LIKE '%" + query + "%')";

        System.out.println("Mengambil Data Pengadaan Barang...");
        List<PengadaanBarang> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    Barang barang = new Barang(
                            Integer.parseInt(rs.getString("b.idBarang")),
                            Integer.parseInt(rs.getString("b.kuantitas")),
                            rs.getString("b.namaBarang"),
                            Double.parseDouble(rs.getString("b.harga")));

                    Role role = new Role(
                            Integer.parseInt(rs.getString("r.idRole")),
                            rs.getString("r.namaRole"));

                    User user = new User(
                            Integer.parseInt(rs.getString("u.idUser")),
                            rs.getString("u.username"),
                            rs.getString("u.password"));

                    Staff staff = new Staff(
                            Integer.parseInt(rs.getString("s.nip")),
                            rs.getString("s.nama"),
                            rs.getString("s.tahunMasuk"),
                            rs.getString("s.noTelp"),
                            rs.getString("s.alamat"),
                            role, user);

                    PengadaanBarang pBarang = new PengadaanBarang(
                            Integer.parseInt(rs.getString("pb.idPengadaan")),
                            Integer.parseInt(rs.getString("pb.kuantitas")),
                            Integer.parseInt(rs.getString("pb.idBarang")),
                            rs.getString("pb.supplier"),
                            rs.getString("pb.tanggalPengadaan"),
                            barang, staff);
                    list.add(pBarang);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Reading Database...");
            System.out.println(e);
        }

        dbCon.closeConnection();
        return list;
    }

    public void updatePengadaanBarang(PengadaanBarang pb) {
        con = dbCon.makeConnection();

        String sql
                = "UPDATE pengadaanbarang SET "
                + "idBarang = '" + pb.getBarang().getIdBarang() + "', "
                + "kuantitas = '" + pb.getKuantitas() + "', "
                + "supplier = '" + pb.getSupplier() + "', "
                + "tanggalPengadaan = '" + pb.getTanggalPengadaan() + "' "
                + "WHERE idPengadaan = '" + pb.getIdPengadaan() + "'";

        System.out.println("Editing Pengadaan Barang...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " Pengadaan Barang " + pb.getIdPengadaan());
            statement.close();
        } catch (Exception e) {
            System.out.println("[!] Error Editing Pengadaan Barang...");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public void deletePengadaanBarang(int idPengadaanBarang) {
        con = dbCon.makeConnection();

        String sql = "DELETE FROM pengadaanbarang WHERE idPengadaan = " + idPengadaanBarang;

        System.out.println("Deleting Stok Barang...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Delete " + result + " Stok Barang " + idPengadaanBarang);
            statement.close();
        } catch (Exception e) {
            System.out.println("[!] Error Deleting Stok Barang...");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }
}
