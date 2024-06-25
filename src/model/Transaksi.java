/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yulia
 */
public class Transaksi {
    private int idTransaksi, idBarang, idPengguna, jumlah, NIP;
    private String tanggalPembelian;
    private Staff staff;
    private Barang barang;
    private Pengguna pengguna;
    
    public Transaksi(int idTransaksi, int jumlah, String tanggalPembelian, Staff staff, Barang barang, Pengguna pengguna) {
        this.idTransaksi = idTransaksi;
        this.jumlah = jumlah;
        this.tanggalPembelian = tanggalPembelian;
        this.staff = staff;
        this.barang=barang;
        this.pengguna = pengguna;
    }
    
    public Transaksi(int jumlah, String tanggalPembelian, Staff staff, Barang barang, Pengguna pengguna) {
        this.jumlah = jumlah;
        this.tanggalPembelian = tanggalPembelian;
        this.staff = staff;
        this.barang = barang;
        this.pengguna = pengguna;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getNIP() {
        return NIP;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
    }

    public String getTanggalPembelian() {
        return tanggalPembelian;
    }

    public void setTanggalPembelian(String tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public void setPengguna(Pengguna pengguna) {
        this.pengguna = pengguna;
    }
    
    public double totalPembelian() {
        return barang.getHarga() * jumlah;
    }
    
}
