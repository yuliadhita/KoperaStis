/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components.exception;

/**
 *
 * @author yulia
 */
public class JumlahBarangKosongException extends Exception{
    public String message(){
        return "[!] JUMLAH BARANG TIDAK BOLEH KOSONG [!]";
    }
}
