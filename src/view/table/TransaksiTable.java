/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.table;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import model.Transaksi;
/**
 *
 * @author yulia
 */
public class TransaksiTable extends AbstractTableModel {
    private List<Transaksi> list;

    public TransaksiTable(List<Transaksi> list) {
        this.list = list;
    }
    
    public int getRowCount(){
        return list.size();
    }
    
    public int getColumnCount(){
        return 8;
    }
  
    public Object getValueAt (int rowIndex, int columnIndex){
        switch (columnIndex){
            case 0:
                return list.get(rowIndex).getIdTransaksi();
            case 1:
                return list.get(rowIndex).getStaff().getNama();
            case 2:
                return list.get(rowIndex).getBarang().getNamaBarang();
            case 3:
                return list.get(rowIndex).getPengguna().getNama();
            case 4:
                return list.get(rowIndex).getTanggalPembelian();
            case 5:
                return list.get(rowIndex).getBarang().getHarga();
            case 6:
                return list.get(rowIndex).getJumlah();
            case 7:
                return list.get(rowIndex).totalPembelian();
                
            default :
                return null;
        }
    }
    
    public String getColumnName (int column) {
        switch(column){
            case 0:
                return "ID Transaksi";
            case 1:
                return "Nama Staff";
            case 2:
                return "Nama Barang";
            case 3:
                return "Nama Pengguna";
            case 4:
                return "Tanggal Pembelian";
            case 5:
                return "Harga Barang";
            case 6:
                return "Jumlah";
            case 7:
                return "Total Pembelian";
            default:
                return null;
        }
    }
    
}
