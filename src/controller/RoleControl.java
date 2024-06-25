/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import database.RoleDAO;
import java.util.List;
import model.Role;
/**
 *
 * @author yulia
 */
public class RoleControl {
    
    private RoleDAO pm = new RoleDAO();

    public List<Role> showRole() {
        return pm.showRole();
    }
}
