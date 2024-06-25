package view.pengguna;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class WinButton extends javax.swing.JPanel {

    public WinButton() {
        initComponents();
        setOpaque(false);
    }

    public void initEvent(JFrame frame, Background panel) {
        cmdClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        cmdMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.setState(JFrame.ICONIFIED);
            }
        });
        cmdRe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    panel.setRound(20);
                    frame.setExtendedState(JFrame.NORMAL);
                } else {
                    panel.setRound(0);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdMi = new view.pengguna.Button();
        cmdRe = new view.pengguna.Button();
        cmdClose = new view.pengguna.Button();

        setPreferredSize(new java.awt.Dimension(60, 18));

        cmdMi.setBackground(new java.awt.Color(227, 226, 68));

        cmdRe.setBackground(new java.awt.Color(102, 255, 51));

        cmdClose.setBackground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdMi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdMi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.pengguna.Button cmdClose;
    private view.pengguna.Button cmdMi;
    private view.pengguna.Button cmdRe;
    // End of variables declaration//GEN-END:variables
}
