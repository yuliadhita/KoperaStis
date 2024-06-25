/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yulia
 */
public class Barang {
    private int idBarang, kuantitas;
    private String namaBarang;
    private double harga;

    public Barang(int idBarang, int kuantitas, String namaBarang, double harga) {
        this.idBarang = idBarang;
        this.kuantitas = kuantitas;
        this.namaBarang = namaBarang;
        this.harga = harga;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
    
    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

     public double getHarga() {
        return harga;
    }
     
    public void setHarga(double harga) {
        this.harga = harga;
    }
    public String showData() {
        return "Nama Barang : " + this.namaBarang + "\n"
               + "Harga : " + this.harga + "\n"
               + "Kuantitas : " + this.kuantitas + "\n";
               
    }

    @Override
    public String toString() {
        return namaBarang;
    }
}
