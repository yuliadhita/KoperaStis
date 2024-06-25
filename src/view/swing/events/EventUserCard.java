/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package view.swing.events;

import model.Pengguna;
import model.Staff;

/**
 *
 * @author yulia
 */
public interface EventUserCard {

    public void runEvent();

    public void runEvent(Pengguna pengguna);

    public void runEvent(Staff staff);
}
