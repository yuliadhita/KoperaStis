/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import database.PengadaanBarangDAO;
import java.util.List;
import model.PengadaanBarang;
import view.table.PengadaanBarangTable;
/**
 *
 * @author yulia
 */
public class PengadaanBarangControl {
    private PengadaanBarangDAO PBDao = new PengadaanBarangDAO();

    public void insertPengadaanBarang(PengadaanBarang pb) {
        PBDao.insertPengadaanBarang(pb);
    }

    public PengadaanBarangTable showPengadaanBarang(String query) {
        List<PengadaanBarang> dataPengadaanBarang = PBDao.showPengadaanBarang(query);
        PengadaanBarangTable tablePengadaanBarang = new PengadaanBarangTable(dataPengadaanBarang);
        return tablePengadaanBarang;
    }

    public void updatePengadaanBarang(PengadaanBarang pb) {
        PBDao.updatePengadaanBarang(pb);
    }

    public void deletePengadaanBarang(int id) {
        PBDao.deletePengadaanBarang(id);
    }
}
