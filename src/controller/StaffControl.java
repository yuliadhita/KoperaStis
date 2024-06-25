/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Staff;
import java.util.List;
import model.User;
import database.StaffDAO;
import view.table.StaffTable;
/**
 *
 * @author yulia
 */
public class StaffControl {
    private StaffDAO sd = new StaffDAO();

    public void insertDataStaff(Staff s) {
        sd.insertStaff(s);
    }

    public StaffTable showDataStaff(String query) {
        List<Staff> dataStaff = sd.showStaff(query);
        StaffTable tabel = new StaffTable(dataStaff);

        return tabel;
    }

    public void updateDataStaff(Staff s) {
        sd.updateStaff(s);
    }

    public void deleteDataStaff(int id) {
        sd.deleteStaf(id);
    }

    public int findStaff(int idUser) {
        return sd.checkStaff(idUser);
    }

    public String returnName(int idUser) {
        return sd.returnName(idUser);
    }

    public Staff returnStaff(int idUser) {
        return sd.returnStaff(idUser);
    }

}
