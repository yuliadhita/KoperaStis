/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.kepalagudang;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.PengadaanBarangControl;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import view.swing.component.dashboard.NoUserCard;
import controller.PenggunaControl;
import controller.RoleControl;
import controller.StaffControl;
import controller.UserControl;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.SwingUtilities;
import model.Barang;
import model.PengadaanBarang;
import model.Pengguna;
import model.Role;
import model.Staff;
import view.swing.ColorPallete;
import view.swing.component.dashboard.PengadaanBarangCard;
import view.table.PengadaanBarangTable;
import components.exception.InputKosongException;
import controller.BarangControl;
import components.exception.InputNegatifException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author yulia
 */
public class PengadaanBarangForm extends javax.swing.JPanel {

    private Staff staffLogin;

    private PengadaanBarang pengadaanBarang;
    private PengadaanBarangControl pbc;

    //untuk menyimpan info Pengguna yang sedang dipilih
    private Pengguna pengguna;

    private PenggunaControl pc;
    private StaffControl sc;
    private UserControl uc;
    private RoleControl rc;
    private BarangControl bc;

    private static ColorPallete cp = new ColorPallete();
    private PengadaanBarangCard pengadaanBarangCard;
    private NoUserCard noUserCard = new NoUserCard();
    private String namaUserGroup;
    private CardLayout cardLayout;

    private String searchInput = "";

    private List<Barang> listBarang;
    int selectedid = 0;

    //Konstruktor
    
    public PengadaanBarangForm(Staff s) {

        if (s != null) {
            this.staffLogin = s;
        } else {
            JOptionPane.showMessageDialog(null, "[WARNING] tidak ada parameter staff / null! View ini harus diakses melalui view Login!");
            System.out.println("[WARNING] tidak ada parameter staff / null!");
        }

        this.namaUserGroup = "Pengadaan Barang";

        this.pbc = new PengadaanBarangControl();

        this.pc = new PenggunaControl();
        this.uc = new UserControl();
        this.rc = new RoleControl();
        this.sc = new StaffControl();
        this.bc = new BarangControl();

        initComponents();

        showUserCard(noUserCard);

        setTableModel(pbc.showPengadaanBarang(""));

        setJudulForm(namaUserGroup);
        fieldSearch.setHint("Cari berdasarkan nama, kategori, dan lain-lain");
        btnTambah.setIcon(new FlatSVGIcon("img/icon/addnote.svg", 0.7f));
        btnTambah.setText("");

        btnBack.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancel.setBackground(new Color(240, 240, 240));
        btnCancel.setForeground(cp.getColor(0));
        btnCancel.setColorEffectRGB(220, 220, 220);

        btnBackTbh.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancelTbh.setBackground(new Color(240, 240, 240));
        btnCancelTbh.setForeground(cp.getColor(0));
        btnCancelTbh.setColorEffectRGB(220, 220, 220);

        btnBackMS.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancelMS.setBackground(new Color(240, 240, 240));
        btnCancelMS.setForeground(cp.getColor(0));
        btnCancelMS.setColorEffectRGB(220, 220, 220);

        containerCreate.setBackground(new Color(255, 255, 255));

        cardLayout = (CardLayout) cardPanel.getLayout();

        //action listeners
        initTableListener();

        //other listeners
        //action listener pada read
        //btn tambah
        addBtnTambahActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setBarangToDropdownCreate();
                cardLayout.show(cardPanel, "create");
            }
        });
        //action listener pada update
        //button back action listener pada panel update
        //fungsinya untuk mengembalikan panel UpdateDataPembeli ke ReadDataPembeli lagi
        addBtnBackActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //reset data dan pindah ke panel read
                resetUpdatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        //button save action listener untuk save update di database
        addBtnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                try {
                    inputKosongExceptionUpdate();
                    inputNegatifExceptionUpdate();

                    PengadaanBarang pengadaanBarangNew = pengadaanBarang;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String inputTglPengadaanBarang = formatter.format(tanggalTransaksiDateChooser1.getDate());
                    pengadaanBarangNew.setTanggalPengadaan(inputTglPengadaanBarang);
                    Barang barangPilihan = (Barang) namaBarangComboBox1.getModel().getSelectedItem();
                    pengadaanBarangNew.setBarang(barangPilihan);
                    pengadaanBarangNew.setKuantitas(Integer.parseInt(inputKuantitas1.getText()));
                    pengadaanBarangNew.setSupplier(inputSupplier1.getText());

                    pbc.updatePengadaanBarang(pengadaanBarangNew);

                    resetUpdatePanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Harus Angka");
                } catch (InputNegatifException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Tidak Boleh 0 Atau Kurang Dari 0");
                }
            }
        });
        //button cancel action listener fungsinya sama seperti button back
        addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //reset data dan pindah panel
                resetUpdatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        //action listener pada tambah panel
        //
        btnBackTbh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                resetCreatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        btnCancelTbh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                resetCreatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        btnSaveTbh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    inputKosongExceptionCreate();
                    inputNegatifExceptionCreate();
                   
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String inputTglPengadaanBarang = formatter.format(tanggalTransaksiDateChooser.getDate());

                    Barang barangPilihan = (Barang) namaBarangComboBox.getModel().getSelectedItem();
                    PengadaanBarang PengadaanBarangBaru = new PengadaanBarang(-1, Integer.parseInt(inputKuantitas.getText()),
                            barangPilihan.getIdBarang(), inputSupplier.getText(),
                            inputTglPengadaanBarang, barangPilihan, staffLogin);//LoginRegisterView.sLogin);
                    pbc.insertPengadaanBarang(PengadaanBarangBaru);
                    resetCreatePanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi Terlebih Dahulu!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Harus Angka");
                } catch (InputNegatifException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Tidak Boleh 0 Atau Kurang Dari 0");
                }
            }
        });
        //action listener untuk search
        //tanpa button
        addFieldSearchActionListener();
    }

    public void inputNegatifExceptionCreate() throws InputNegatifException {
        if (Integer.parseInt(inputKuantitas.getText()) == 0 || Integer.parseInt(inputKuantitas.getText()) < 0) {
            throw new InputNegatifException();
        }
    }

    public void inputNegatifExceptionUpdate() throws InputNegatifException {
        if (Integer.parseInt(inputKuantitas1.getText()) == 0 || Integer.parseInt(inputKuantitas1.getText()) < 0) {
            throw new InputNegatifException();
        }
    }

    //Method-method pada Make Staff Panel
    private void resetMakeStaffPanel() {
        tanggalMasukDateChooser.setDate(null);
    }

    private void insertRoleToComboBox() {
        List<Role> listRole = rc.showRole();
        roleComboBox.removeAllItems();
        for (Role role : listRole) {
            roleComboBox.addItem(role);
        }
    }

    public void inputKosongExceptionCreate() throws InputKosongException {
        if (tanggalTransaksiDateChooser.getDate() == null || namaBarangComboBox.getSelectedIndex() == -1 || inputKuantitas.getText().isEmpty() || inputSupplier.getText().isEmpty()) {
            throw new InputKosongException();
        }
    }

    public void inputKosongExceptionUpdate() throws InputKosongException {
        if (tanggalTransaksiDateChooser1.getDate() == null || namaBarangComboBox1.getModel().getSelectedItem() == null || inputKuantitas1.getText().isEmpty() || inputSupplier1.getText().isEmpty()) {
            throw new InputKosongException();
        }
    }

    private void initTanggalMasuk() {
        LocalDate dateToday = LocalDate.now();
        LocalDate dateThreeMonthsBeforeToday = dateToday.minusMonths(3);
        //Maksimal input adalah hari ini, dan minimal input adalah 3 bulan sebelum hari ini
        //Fungsi dibawah adalah untuk konversi dari LocalDate ke Date
        tanggalMasukDateChooser.setMinSelectableDate(Date.from(dateThreeMonthsBeforeToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        Date today = Date.from(dateToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        tanggalMasukDateChooser.setMaxSelectableDate(today);
        //Default value awal untuk textField tanggal masuk adalah today alias hari ini
        //Sehingga disetting sebagai berikut:
        tanggalMasukDateChooser.setDate(today);
    }

    //Method-method pada Create/Tambah Panel
    private void resetCreatePanel() {
        tanggalTransaksiDateChooser.setDate(null);
        namaBarangComboBox.setSelectedIndex(0);
        inputKuantitas.setText("");
        inputSupplier.setText("");
    }

    private void setBarangToDropdownCreate() {
        namaBarangComboBox.removeAllItems();
        listBarang = bc.showListBarang();
        for (int i = 0; i < listBarang.size(); i++) {
            namaBarangComboBox.addItem(listBarang.get(i));
        }
    }

    private void insertAndSetBarangToComboBox(PengadaanBarang pb) {
        List<Barang> listBarangt = bc.showListBarang();
        namaBarangComboBox1.removeAllItems();
        for (Barang barang : listBarang) {
            namaBarangComboBox1.addItem(barang);
            if (barang.getNamaBarang().equals(pb.getBarang().getNamaBarang())) {
                namaBarangComboBox1.setSelectedItem(barang);
            }
        }
    }

    //Method-method pada Update Panel
    private void setTextToComponent(int kuantitas, String supplier) {

        Date tanggalPengadaan;
        try {
            tanggalPengadaan = new SimpleDateFormat("yyyy-MM-dd").parse(pengadaanBarang.getTanggalPengadaan());
            tanggalTransaksiDateChooser1.setDate(tanggalPengadaan);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        inputKuantitas1.setText(String.valueOf(kuantitas));
        inputSupplier1.setText(supplier);
    }

    private void addBtnBackActionListener(ActionListener event) {
        btnBack.addActionListener(event);
    }

    private void addBtnSaveActionListener(ActionListener event) {
        btnSave.addActionListener(event);
    }

    private void addBtnCancelActionListener(ActionListener event) {
        btnCancel.addActionListener(event);
    }

    private void resetUpdatePanel() {
        tanggalTransaksiDateChooser1.setDate(null);
        namaBarangComboBox1.setSelectedIndex(0);
        inputKuantitas1.setText("");
        inputSupplier1.setText("");
    }

    //Method-method pada Read Panel
    private void addBtnTambahActionListener(ActionListener event) {
        btnTambah.addActionListener(event);
    }

    private void resetReadPanel() {
        searchInput = "";
        fieldSearch.setText(searchInput);
        showUserCard(noUserCard);
        setTableModel(pbc.showPengadaanBarang(""));
    }

    //untuk melakukan search segera setelah keyboard direlease
    private void addFieldSearchActionListener() {
        fieldSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                searchInput = fieldSearch.getText();
                //asynchronous process
                Thread newThread = new Thread(() -> {
                    try {
                        Thread.sleep(200);
                        PengadaanBarangTable poTable = pbc.showPengadaanBarang(searchInput);
                        SwingUtilities.invokeLater(() -> {
                            customTable.setModel(poTable);
                            customTable.revalidate();
                        });
                    } catch (Exception e) {
                    }
                });
                newThread.start();
            }
        });
    }

    private void setJudulForm(String text) {
        judulForm.setForeground(cp.getColor(0));
        judulForm.setText("Tabel Data " + text);
        judulDataPilihan.setForeground(cp.getColor(0));
        judulDataPilihan.setText("Kelola Data " + text);
        judulUpdate.setForeground(cp.getColor(0));
        judulUpdate.setText("Update Data " + text);
        judulCreate.setForeground(cp.getColor(0));
        judulCreate.setText("Tambah Data " + text);
    }

    private void setTableModel(AbstractTableModel tableModel) {
        this.customTable.setModel((PengadaanBarangTable) tableModel);
    }

    private void showUserCard(Component panel) {
        panelCard.removeAll();
        panelCard.add(panel);
//        panelCard.repaint();
        panelCard.revalidate();
    }

    //listener untuk table clicked pada panel ReadDataPembeli 
    //serta memiliki listener untuk komponen2 pada panel selanjutnya
    private void initTableListener() {

        customTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                //get value dari table clicked
                if (namaUserGroup.equalsIgnoreCase("Pengadaan Barang")) {
                    int clickedRow = customTable.getSelectedRow();
                    TableModel tableModel = customTable.getModel();
                    //untuk reset pengguna
                    pengadaanBarang = null;
                    //assign pengguna sesuai row yang di klik pada tabel
                    pengadaanBarang = (PengadaanBarang) tableModel.getValueAt(clickedRow, 6);
                    //show user card panel
                    pengadaanBarangCard = new PengadaanBarangCard(pengadaanBarang);
                    showUserCard(pengadaanBarangCard);

                    //user card button edit action listener
                    pengadaanBarangCard.addBtnEditActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //melempar event untuk dieksekusi ke panel/frame diatasnya yaitu SuperAdminView
                            //fungsinya untuk mengganti panel ReadDataPembeli ke panel UpdateDataPembeli
                            //setTextToComponent(pengguna.getNama(), pengguna.getAlamat(), pengguna.getNoTelp());

                            setTextToComponent(pengadaanBarang.getKuantitas(), pengadaanBarang.getSupplier());
                            insertAndSetBarangToComboBox(pengadaanBarang);
                            cardLayout.show(cardPanel, "update");
                        }
                    });
                    //user card button delete action listener
                    pengadaanBarangCard.addBtnDeleteActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //ketika btn delete di user card
                            //pc.deleteDataPengguna(pengguna.getIdPengguna());
                            //uc.deleteDataUser(pengguna.getUser().getIdUser());

                            pbc.deletePengadaanBarang(pengadaanBarang.getIdPengadaan());

                            System.out.println("Berhasil delete");
                            showUserCard(noUserCard);
                            setTableModel(pbc.showPengadaanBarang(""));
                        }
                    });

                }

            }
        });


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();
        readPengadaanBarang = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customTable = new view.swing.component.CustomTable();
        jPanel3 = new javax.swing.JPanel();
        fieldSearch = new view.swing.component.TextFieldWithBackground();
        jPanel4 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        judulDataPilihan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new view.swing.component.ButtonRectangle();
        judulForm = new javax.swing.JLabel();
        btnExport = new view.swing.component.ButtonRound();
        updatePembeli = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnBack = new view.swing.component.ButtonOutLine();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSave = new view.swing.component.ButtonRound();
        btnCancel = new view.swing.component.ButtonRound();
        judulUpdate = new javax.swing.JLabel();
        labelTanggal1 = new javax.swing.JLabel();
        tanggalTransaksiDateChooser1 = new com.toedter.calendar.JDateChooser();
        labelNamaBarang1 = new javax.swing.JLabel();
        namaBarangComboBox1 = new javax.swing.JComboBox<>();
        labelKuantitas1 = new javax.swing.JLabel();
        inputKuantitas1 = new view.swing.component.TextFieldInput();
        labelSupplier1 = new javax.swing.JLabel();
        inputSupplier1 = new view.swing.component.TextFieldInput();
        jPanel8 = new javax.swing.JPanel();
        createPengadaanBarang = new javax.swing.JPanel();
        scrollPaneCreate = new javax.swing.JScrollPane();
        containerCreate = new javax.swing.JPanel();
        labelTanggal = new javax.swing.JLabel();
        tanggalTransaksiDateChooser = new com.toedter.calendar.JDateChooser();
        labelNamaBarang = new javax.swing.JLabel();
        namaBarangComboBox = new javax.swing.JComboBox<>();
        labelKuantitas = new javax.swing.JLabel();
        inputKuantitas = new view.swing.component.TextFieldInput();
        labelSupplier = new javax.swing.JLabel();
        inputSupplier = new view.swing.component.TextFieldInput();
        judulCreate = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnSaveTbh = new view.swing.component.ButtonRound();
        btnCancelTbh = new view.swing.component.ButtonRound();
        btnBackTbh = new view.swing.component.ButtonOutLine();
        makeStaff = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnBackMS = new view.swing.component.ButtonOutLine();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnSaveMS = new view.swing.component.ButtonRound();
        btnCancelMS = new view.swing.component.ButtonRound();
        judulMS = new javax.swing.JLabel();
        tanggalMasukDateChooser = new com.toedter.calendar.JDateChooser();
        roleComboBox = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(684, 652));

        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new java.awt.Dimension(684, 652));
        cardPanel.setLayout(new java.awt.CardLayout());

        readPengadaanBarang.setOpaque(false);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        customTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        customTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(customTable);

        jPanel3.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setOpaque(false);

        panelCard.setOpaque(false);
        panelCard.setPreferredSize(new java.awt.Dimension(407, 80));
        panelCard.setLayout(new java.awt.BorderLayout());

        judulDataPilihan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulDataPilihan.setForeground(new java.awt.Color(0, 0, 0));
        judulDataPilihan.setText("Judul Pilihan");

        jPanel1.setOpaque(false);

        btnTambah.setText("+");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(judulDataPilihan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(judulDataPilihan)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCard, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        judulForm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulForm.setForeground(new java.awt.Color(0, 0, 0));
        judulForm.setText("Judul Form");

        btnExport.setText("Export (.xls)");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout readPengadaanBarangLayout = new javax.swing.GroupLayout(readPengadaanBarang);
        readPengadaanBarang.setLayout(readPengadaanBarangLayout);
        readPengadaanBarangLayout.setHorizontalGroup(
            readPengadaanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readPengadaanBarangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(readPengadaanBarangLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(readPengadaanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(readPengadaanBarangLayout.createSequentialGroup()
                        .addComponent(judulForm)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readPengadaanBarangLayout.createSequentialGroup()
                        .addGroup(readPengadaanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(readPengadaanBarangLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                        .addGap(30, 30, 30))))
        );
        readPengadaanBarangLayout.setVerticalGroup(
            readPengadaanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readPengadaanBarangLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judulForm)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        cardPanel.add(readPengadaanBarang, "read");

        updatePembeli.setOpaque(false);

        jPanel5.setOpaque(false);

        btnBack.setForeground(cp.getColor(0)
        );
        btnBack.setText("Kembali");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel6.setOpaque(false);

        jPanel7.setOpaque(false);

        btnSave.setText("Save");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        judulUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulUpdate.setForeground(cp.getColor(0)
        );
        judulUpdate.setText("Update Data Pembeli");

        labelTanggal1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggal1.setForeground(cp.getColor(0)
        );
        labelTanggal1.setText("Tanggal Pengadaan Barang (Default: Hari Ini)");

        tanggalTransaksiDateChooser1.setForeground(new java.awt.Color(122, 140, 141));
        tanggalTransaksiDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        labelNamaBarang1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNamaBarang1.setForeground(cp.getColor(0)
        );
        labelNamaBarang1.setText("Nama Baranf");

        namaBarangComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        namaBarangComboBox1.setForeground(new java.awt.Color(122, 140, 141));

        labelKuantitas1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas1.setForeground(cp.getColor(0)
        );
        labelKuantitas1.setText("Kuantitas");

        inputKuantitas1.setHint("25");

        labelSupplier1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelSupplier1.setForeground(cp.getColor(0)
        );
        labelSupplier1.setText("Supplier");

        inputSupplier1.setHint("Kalbe Farma");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelTanggal1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(namaBarangComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tanggalTransaksiDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelKuantitas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNamaBarang1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputKuantitas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSupplier1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputSupplier1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(judulUpdate))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulUpdate)
                .addGap(25, 25, 25)
                .addComponent(labelTanggal1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalTransaksiDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNamaBarang1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaBarangComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelKuantitas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSupplier1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputSupplier1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout updatePembeliLayout = new javax.swing.GroupLayout(updatePembeli);
        updatePembeli.setLayout(updatePembeliLayout);
        updatePembeliLayout.setHorizontalGroup(
            updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePembeliLayout.createSequentialGroup()
                .addGroup(updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, updatePembeliLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        updatePembeliLayout.setVerticalGroup(
            updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePembeliLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        cardPanel.add(updatePembeli, "update");

        createPengadaanBarang.setOpaque(false);

        scrollPaneCreate.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneCreate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrollPaneCreate.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCreate.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneCreate.setOpaque(false);

        labelTanggal.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggal.setForeground(cp.getColor(0)
        );
        labelTanggal.setText("Tanggal Pengadaan Barang (Default: Hari Ini)");

        tanggalTransaksiDateChooser.setForeground(new java.awt.Color(122, 140, 141));
        tanggalTransaksiDateChooser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        labelNamaBarang.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNamaBarang.setForeground(cp.getColor(0)
        );
        labelNamaBarang.setText("Nama Barang");

        namaBarangComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        namaBarangComboBox.setForeground(new java.awt.Color(122, 140, 141));

        labelKuantitas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas.setForeground(cp.getColor(0)
        );
        labelKuantitas.setText("Kuantitas");

        inputKuantitas.setHint("25");

        labelSupplier.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelSupplier.setForeground(cp.getColor(0)
        );
        labelSupplier.setText("Supplier");

        inputSupplier.setHint("Kalbe Farma");

        judulCreate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulCreate.setForeground(cp.getColor(0)
        );
        judulCreate.setText("Tambah Data");

        jPanel11.setOpaque(false);

        btnSaveTbh.setText("Save");

        btnCancelTbh.setText("Cancel");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSaveTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        btnBackTbh.setForeground(cp.getColor(0)
        );
        btnBackTbh.setText("Kembali");

        javax.swing.GroupLayout containerCreateLayout = new javax.swing.GroupLayout(containerCreate);
        containerCreate.setLayout(containerCreateLayout);
        containerCreateLayout.setHorizontalGroup(
            containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addComponent(judulCreate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelTanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(namaBarangComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
                            .addComponent(tanggalTransaksiDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelNamaBarang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(inputKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(300, Short.MAX_VALUE))))
        );
        containerCreateLayout.setVerticalGroup(
            containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(judulCreate)
                .addGap(25, 25, 25)
                .addComponent(labelTanggal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalTransaksiDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNamaBarang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaBarangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelKuantitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSupplier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        scrollPaneCreate.setViewportView(containerCreate);

        javax.swing.GroupLayout createPengadaanBarangLayout = new javax.swing.GroupLayout(createPengadaanBarang);
        createPengadaanBarang.setLayout(createPengadaanBarangLayout);
        createPengadaanBarangLayout.setHorizontalGroup(
            createPengadaanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        createPengadaanBarangLayout.setVerticalGroup(
            createPengadaanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );

        cardPanel.add(createPengadaanBarang, "create");

        makeStaff.setOpaque(false);

        jPanel9.setOpaque(false);

        btnBackMS.setForeground(cp.getColor(0)
        );
        btnBackMS.setText("Kembali");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMS, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setForeground(cp.getColor(0)
        );
        jLabel10.setText("Tanggal Masuk");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel11.setForeground(cp.getColor(0)
        );
        jLabel11.setText("Role");

        jPanel12.setOpaque(false);

        btnSaveMS.setText("Save");

        btnCancelMS.setText("Cancel");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSaveMS, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelMS, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveMS, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelMS, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        judulMS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulMS.setForeground(cp.getColor(0)
        );
        judulMS.setText("Jadikan <Nama> Sebagai Staff?");

        tanggalMasukDateChooser.setBackground(new java.awt.Color(255, 255, 255));
        tanggalMasukDateChooser.setForeground(new java.awt.Color(0, 0, 0));
        tanggalMasukDateChooser.setDateFormatString("dd MMMM yyyy");
        tanggalMasukDateChooser.setMaxSelectableDate(new java.util.Date(253370743293000L));
        tanggalMasukDateChooser.setPreferredSize(new java.awt.Dimension(88, 42));

        roleComboBox.setPreferredSize(new java.awt.Dimension(72, 42));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(494, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(judulMS)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(roleComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tanggalMasukDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                        .addContainerGap(201, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulMS)
                .addGap(25, 25, 25)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalMasukDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        jPanel13.setOpaque(false);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout makeStaffLayout = new javax.swing.GroupLayout(makeStaff);
        makeStaff.setLayout(makeStaffLayout);
        makeStaffLayout.setHorizontalGroup(
            makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeStaffLayout.createSequentialGroup()
                .addGroup(makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, makeStaffLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        makeStaffLayout.setVerticalGroup(
            makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeStaffLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        cardPanel.add(makeStaff, "makeStaff");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("PengadaanBarang");

                // Create header row
                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                for (int i = 0; i < customTable.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(customTable.getColumnName(i));
                }

                // Populate data rows
                for (int j = 0; j < customTable.getRowCount(); j++) {
                    org.apache.poi.ss.usermodel.Row dataRow = sheet.createRow(j + 1);
                    for (int k = 0; k < customTable.getColumnCount(); k++) {
                        Cell cell = dataRow.createCell(k);
                        Object value = customTable.getValueAt(j, k);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }

                // Write workbook to output file
                FileOutputStream out = new FileOutputStream(saveFile);
                wb.write(out);
                wb.close();
                out.close();

                // Open the saved file
                openFile(saveFile.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Export dibatalkan");
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File tidak ditemukan: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error IO: " + e.getMessage());
        }
    }//GEN-LAST:event_btnExportActionPerformed
private void openFile(String filePath) {
    try {
        File file = new File(filePath);
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(null, "Desktop tidak didukung pada sistem ini");
            }
        } else {
            JOptionPane.showMessageDialog(null, "File tidak ditemukan");
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error membuka file: " + e.getMessage());
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.swing.component.ButtonOutLine btnBack;
    private view.swing.component.ButtonOutLine btnBackMS;
    private view.swing.component.ButtonOutLine btnBackTbh;
    private view.swing.component.ButtonRound btnCancel;
    private view.swing.component.ButtonRound btnCancelMS;
    private view.swing.component.ButtonRound btnCancelTbh;
    private view.swing.component.ButtonRound btnExport;
    private view.swing.component.ButtonRound btnSave;
    private view.swing.component.ButtonRound btnSaveMS;
    private view.swing.component.ButtonRound btnSaveTbh;
    private view.swing.component.ButtonRectangle btnTambah;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel containerCreate;
    private javax.swing.JPanel createPengadaanBarang;
    private view.swing.component.CustomTable customTable;
    private view.swing.component.TextFieldWithBackground fieldSearch;
    private view.swing.component.TextFieldInput inputKuantitas;
    private view.swing.component.TextFieldInput inputKuantitas1;
    private view.swing.component.TextFieldInput inputSupplier;
    private view.swing.component.TextFieldInput inputSupplier1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judulCreate;
    private javax.swing.JLabel judulDataPilihan;
    private javax.swing.JLabel judulForm;
    private javax.swing.JLabel judulMS;
    private javax.swing.JLabel judulUpdate;
    private javax.swing.JLabel labelKuantitas;
    private javax.swing.JLabel labelKuantitas1;
    private javax.swing.JLabel labelNamaBarang;
    private javax.swing.JLabel labelNamaBarang1;
    private javax.swing.JLabel labelSupplier;
    private javax.swing.JLabel labelSupplier1;
    private javax.swing.JLabel labelTanggal;
    private javax.swing.JLabel labelTanggal1;
    private javax.swing.JPanel makeStaff;
    private javax.swing.JComboBox<Barang> namaBarangComboBox;
    private javax.swing.JComboBox<Barang> namaBarangComboBox1;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel readPengadaanBarang;
    private javax.swing.JComboBox<Role> roleComboBox;
    private javax.swing.JScrollPane scrollPaneCreate;
    private com.toedter.calendar.JDateChooser tanggalMasukDateChooser;
    private com.toedter.calendar.JDateChooser tanggalTransaksiDateChooser;
    private com.toedter.calendar.JDateChooser tanggalTransaksiDateChooser1;
    private javax.swing.JPanel updatePembeli;
    // End of variables declaration//GEN-END:variables
}
