/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yulia
 */
public class PengadaanBarang {
    private int idPengadaan, kuantitas, idBarang;
    private String supplier, tanggalPengadaan;
    private Barang barang;
    private Staff staff;

    public PengadaanBarang(int idPengadaan, int kuantitas, int idBarang, String supplier, String tanggalPengadaan, Barang barang, Staff staff) {
        this.idPengadaan = idPengadaan;
        this.kuantitas = kuantitas;
        this.idBarang = idBarang;
        this.supplier = supplier;
        this.tanggalPengadaan = tanggalPengadaan;
        this.barang = barang;
        this.staff = staff;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public int getIdPengadaan() {
        return idPengadaan;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getTanggalPengadaan() {
        return tanggalPengadaan;
    }

    public void setIdPengadaan(int idPengadaan) {
        this.idPengadaan = idPengadaan;
    }

    public void setQuantity(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setTanggalPengadaan(String tanggalPengadaan) {
        this.tanggalPengadaan = tanggalPengadaan;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

}
