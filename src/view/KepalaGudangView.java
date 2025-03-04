/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import model.Staff;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import view.panel.DashboardMainPanel;
import view.kepalagudang.PengadaanBarangForm;
import view.swing.component.dashboard.Header;
import view.swing.component.dashboard.Menu;
import view.swing.events.EventMenuPanelResize;
import view.swing.events.EventMenuSelected;

/**
 *
 * @author yulia
 */
public class KepalaGudangView extends javax.swing.JFrame {

    private MigLayout layout;
    private Menu menu;
    private Header header;
    private DashboardMainPanel mainPanel;
    private Animator animator;
    private Staff staff;

    /** Creates new form SuperAdminView */
    public KepalaGudangView(Staff s) {
        //inisialisasi variabel (disarankan berada diatas init components)

        //inisialisasi komponen
        initComponents();
        setWindowTitleAndIcon();

        if (s != null) {
            this.staff = s;
            initKepalaGudangView("Kepala Gudang", s.getNama());
        } else {
            initKepalaGudangView("Kepala Gudang", "KepalaGudangTheGod");
        }
    }

    private void setWindowTitleAndIcon() {
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/logo/logo-app.png"));
        this.setIconImage(icon);
    }

    private void initKepalaGudangView(String roleView, String usernameView) {

        //membuat layout bertipe miglayout
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        background.setLayout(layout);
        //buat objek komponen lain
        menu = new Menu("K. Gudang");
        header = new Header(usernameView, roleView);
        mainPanel = new DashboardMainPanel();
        menu.addEventSelected(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int submenuIndex) {
                System.out.println("Menu index " + menuIndex + " | Submenu index " + submenuIndex);
                if (menuIndex == 0) {
                     //menggunakan async agar tidak lag animasinya saat dipencet
                    Thread newThread = new Thread(() -> {
                        try {
                            mainPanel.showForm(new PengadaanBarangForm(staff));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    newThread.start();

                } else if (menuIndex == 1) {
                    //menu logout
                    //asynchronous process
                    Thread newThread = new Thread(() -> {
                        try {
                            Thread.sleep(250);
                            LoginRegisterView lview = new LoginRegisterView();
                            dispose();
                            lview.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    newThread.start();
                }
            }
        });
        //set menu panel kiri dengan beberapa menu item khusus untuk superadmin
        menu.initMenuItemForKepalaGudang();
        //set show menu, true berarti menu terbuka, false berarti menu minimize 
        //(harus ganti width saat diadd juga!)
        menu.setShowMenu(false);
        //add component ke background
        background.add(menu, "w 62!, spanY 2"); //width = 230 | span Y == 2 cells
        background.add(header, "h 53!, wrap");
        background.add(mainPanel, "w 100%, h 100%");
        //animasi untuk hide show dashboard menu di kiri
        TimingTarget target = new TimingTargetAdapter() {

            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 62 + (170 * (1f - fraction));
                } else {
                    width = 62 + (170 * (fraction));
                }
                menu.revalidate();
                layout.setComponentConstraints(menu, "w " + width + "!, spanY 2");
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addHamburgerMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                menu.setEnableMenu(false);
                if (menu.isShowMenu()) {
                    menu.hideAllSubmenu();
                }
            }
        });
        menu.addEventResize(new EventMenuPanelResize() {
            @Override
            public void resizeMenuPanel() {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
        //saat app dijalankan, mulai dengan menampilkan panel form ini
        //view read data pembeli (view table, user card, dll)
        mainPanel.showForm(new PengadaanBarangForm(staff));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setOpaque(true);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KepalaGudangView(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane background;
    // End of variables declaration//GEN-END:variables
}
