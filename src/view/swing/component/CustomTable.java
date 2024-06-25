/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.swing.component;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import view.swing.ColorPallete;

/**
 *
 * @author yulia
 */
public class CustomTable extends JTable {

    public static ColorPallete cp = new ColorPallete();

    public CustomTable() {
        setShowHorizontalLines(true);
        setGridColor(new Color(210, 210, 210));
        setRowHeight(30);
        getTableHeader().setReorderingAllowed(false);
        setSelectionBackground(new Color(230, 230, 230));
        setSelectionForeground(cp.getBlack());
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                CustomTableHeader header = new CustomTableHeader(value + "");
                header.setHorizontalAlignment(JLabel.LEFT);
                return header;
            }

        });
    }

}
