/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Barang;
/**
 *
 * @author yulia
 */
public class BarangTable extends AbstractTableModel{
    private List<Barang> barang;

    public BarangTable(List<Barang> barang) {
        this.barang = barang;
    }
    
    public int getRowCount(){
        return barang.size();
    }
    
    public int getColumnCount(){
        return 4;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex){
        switch(columnIndex){
            case 0:
                return barang.get(rowIndex).getIdBarang();
            case 1:
                return barang.get(rowIndex).getNamaBarang();
            case 2:
                return barang.get(rowIndex).getKuantitas();
            case 3:
                return barang.get(rowIndex).getHarga();
            case 4:
                return barang.get(rowIndex);
            default:
                return null;
        }
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "ID Barang";
            case 1:
                return "Nama Barang";
            case 2:
                return "Kuantitas";
            case 3:
                return "Harga";
            default:
                return null;
        }
    }
}
