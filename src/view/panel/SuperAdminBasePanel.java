/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import view.swing.ColorPallete;

/**
 *
 * @author yulia
 */
public class SuperAdminBasePanel extends javax.swing.JPanel {

    private static ColorPallete colorPallete = new ColorPallete();

    /** Creates new form SuperAdminPanel */
    public SuperAdminBasePanel() {
        initComponents();
        setOpaque(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        //mendefine variabel warna gradient
        Color white = colorPallete.getWhite();

        //mendefine variabel width dan height panel
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;

        //memberi efek antialiasing pada gradient yang akan dibuat
        //agar transisi warna gradient lebih smooth
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(white);

        //untuk memberi border radius
        //parameter: startX, startY, endX, endY, borderArcWidth, borderArcHeight
        int borderRadius = 0;
        g2.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);

        super.paintComponent(g);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
