/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author yulia
 */

import model.Barang;
import java.util.List;
import database.BarangDAO;
import view.table.BarangTable;

public class BarangControl {
    private BarangDAO bDao = new BarangDAO();

    public void insertDataBarang(Barang barang) {
        bDao.insertBarang(barang);
    }

    public BarangTable showDataBarang(String query) {
        List<Barang> dataBarang = bDao.showBarang(query);
        BarangTable to = new BarangTable(dataBarang);

        return to;
    }

    public List<Barang> showDataBarangAsList(String query) {
        List<Barang> data = bDao.showBarang(query);
        return data;
    }

    public void updateDataBarang(int id, Barang barang) {
        bDao.updateBarang(id, barang);
    }

    public void deleteDataBarang(int id) {
        bDao.deleteBarang(id);
    }

    //tambahan
    public int findDataKuantitasBarang(String namaBarang) {
        return bDao.findKuantitasBarang(namaBarang);
    }

    public List<Barang> showListBarang() {
        List<Barang> dataBarang = bDao.showBarangDropdown();
        return dataBarang;
    }


}
