/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components.exception;

/**
 *
 * @author yulia
 */
public class noTelpLengthException extends Exception{
    
    public String message(){
        return "Nomor Telepon harus berdigit 10 - 13";
    }
    
}
