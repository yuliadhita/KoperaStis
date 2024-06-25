/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yulia
 */
public class Role {
    private int idRole;
    private String namaRole;

    public Role(int idRole, String namaRole) {
        this.idRole = idRole;
        this.namaRole = namaRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getNamaRole() {
        return namaRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setNamaRole(String namaRole) {
        this.namaRole = namaRole;
    }

    //untuk dropdown combo box Role
    @Override
    public String toString() {
        return namaRole;
    }
}
