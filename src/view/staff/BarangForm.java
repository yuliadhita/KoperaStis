package view.staff;

import components.exception.InputKosongException;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import controller.BarangControl;
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
import controller.TransaksiControl;
import controller.UserControl;
import components.exception.DeleteBarangException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Barang;
import model.Pengguna;
import view.swing.ColorPallete;
import view.swing.component.dashboard.BarangCard;
import view.table.BarangTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author yulia
 */
public class BarangForm extends javax.swing.JPanel {

    private Barang barang;
    private BarangControl bc;

    //untuk menyimpan info Pengguna yang sedang dipilih
    private Pengguna pengguna;

    private PenggunaControl pc;
    private StaffControl sc;
    private UserControl uc;
    private RoleControl rc;
    private TransaksiControl tc;

    private static ColorPallete cp = new ColorPallete();
    private BarangCard barangCard;
    private NoUserCard noUserCard = new NoUserCard();
    private String namaUserGroup;
    private CardLayout cardLayout;

    private String searchInput = "";

    //Konstruktor
    public BarangForm() {
        this.namaUserGroup = "Barang";

        this.bc = new BarangControl();

        this.pc = new PenggunaControl();
        this.uc = new UserControl();
        this.rc = new RoleControl();
        this.sc = new StaffControl();
        this.tc = new TransaksiControl();

        initComponents();

        showUserCard(noUserCard);

        setTableModel(bc.showDataBarang(""));

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
                    inputKosongUpdateException();
                    
                    //update data ke database
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Barang barangNew = new Barang(barang.getIdBarang(), Integer.parseInt(inputKuantitas2.getText()), inputNama1.getText(), Double.parseDouble(inputHarga1.getText()));
                    bc.updateDataBarang(barang.getIdBarang(), barangNew);
                    setTableModel(bc.showDataBarang(""));

                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, e.message());
                } catch (NumberFormatException ec) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Dan Harga harus angka");
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
                //insert data transaksi ke database
                try {
                    inputKosongException();
                    String namaBarang = inputNama.getText();
                    double hargaBarang = Double.parseDouble(inputHarga.getText());
                    int kuantitasBarang = Integer.parseInt(inputKuantitas.getText());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Barang b = new Barang(0, kuantitasBarang, namaBarang, hargaBarang);
                    bc.insertDataBarang(b);

                    resetCreatePanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                    //setComponents();
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, e.message());
                } catch (NumberFormatException ec) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Dan Harga harus angka");
                } 

            }
        });
        //action listener untuk search
        //tanpa button
        addFieldSearchActionListener();
    }

    private void deleteBarangException(int idBarang) throws DeleteBarangException {
        if (tc.cekNullBarang(idBarang) == 1) {
            throw new DeleteBarangException();
        }
    }

    //Method-method pada Create/Tambah Panel
    private void resetCreatePanel() {
        inputNama.setText("");
        inputKuantitas.setText("");
        inputHarga.setText("");
    }


    //Method-method pada Update Panel
    private void setTextToComponent(Barang b) {
        try {
            inputHarga1.setText(String.valueOf(b.getHarga()));
            inputKuantitas2.setText(String.valueOf(b.getKuantitas()));
            inputNama1.setText(b.getNamaBarang());
        } catch (Exception e) {

        }
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
        inputNama.setText("");
        inputKuantitas.setText("");
        inputHarga.setText("");
    }

    //Method-method pada Read Panel
    private void addBtnTambahActionListener(ActionListener event) {
        btnTambah.addActionListener(event);
    }

    private void resetReadPanel() {
        searchInput = "";
        fieldSearch.setText(searchInput);
        showUserCard(noUserCard);
        setTableModel(bc.showDataBarang(""));
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
                        BarangTable bTable = bc.showDataBarang(searchInput);
                        SwingUtilities.invokeLater(() -> {
                            customTable.setModel(bTable);
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
        this.customTable.setModel((BarangTable) tableModel);
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
                if (namaUserGroup.equalsIgnoreCase("Barang")) {
                    int clickedRow = customTable.getSelectedRow();
                    TableModel tableModel = customTable.getModel();
                    //untuk reset pengguna
                    barang = null;
                    //assign pengguna sesuai row yang di klik pada tabel
                    barang = (Barang) tableModel.getValueAt(clickedRow, 4);
                    //show user card panel
                    barangCard = new BarangCard(barang);
                    showUserCard(barangCard);

                    //user card button edit action listener
                    barangCard.addBtnEditActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //melempar event untuk dieksekusi ke panel/frame diatasnya yaitu SuperAdminView
                            //fungsinya untuk mengganti panel ReadDataPembeli ke panel UpdateDataPembeli
                            setTextToComponent(barang);
                            cardLayout.show(cardPanel, "update");
                        }
                    });
                    //user card button delete action listener
                    barangCard.addBtnDeleteActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            try {
                                //ketika btn delete di user card
                                deleteBarangException(barang.getIdBarang());
                                bc.deleteDataBarang(barang.getIdBarang());
                                System.out.println("Berhasil delete");
                                showUserCard(noUserCard);
                                setTableModel(bc.showDataBarang(""));

                            } catch (DeleteBarangException e) {
                                JOptionPane.showMessageDialog(null, e.message());
                            }
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
        labelNama1 = new javax.swing.JLabel();
        inputNama1 = new view.swing.component.TextFieldInput();
        labelHarga1 = new javax.swing.JLabel();
        inputHarga1 = new view.swing.component.TextFieldInput();
        labelKuantitas2 = new javax.swing.JLabel();
        inputKuantitas2 = new view.swing.component.TextFieldInput();
        jPanel8 = new javax.swing.JPanel();
        createPengadaanBarang = new javax.swing.JPanel();
        scrollPaneCreate = new javax.swing.JScrollPane();
        containerCreate = new javax.swing.JPanel();
        labelNama = new javax.swing.JLabel();
        inputNama = new view.swing.component.TextFieldInput();
        labelHarga = new javax.swing.JLabel();
        inputHarga = new view.swing.component.TextFieldInput();
        labelKuantitas = new javax.swing.JLabel();
        inputKuantitas = new view.swing.component.TextFieldInput();
        judulCreate = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnSaveTbh = new view.swing.component.ButtonRound();
        btnCancelTbh = new view.swing.component.ButtonRound();
        btnBackTbh = new view.swing.component.ButtonOutLine();

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

        fieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSearchActionPerformed(evt);
            }
        });

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
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
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
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

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
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

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

        labelNama1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNama1.setForeground(cp.getColor(0)
        );
        labelNama1.setText("Nama Barang");

        inputNama1.setHint("25");

        labelHarga1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelHarga1.setForeground(cp.getColor(0)
        );
        labelHarga1.setText("Harga");

        inputHarga1.setHint("25000");

        labelKuantitas2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas2.setForeground(cp.getColor(0)
        );
        labelKuantitas2.setText("Kuantitas");

        inputKuantitas2.setHint("25");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelNama1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                        .addComponent(labelKuantitas2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelHarga1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputKuantitas2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputHarga1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputNama1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(judulUpdate))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulUpdate)
                .addGap(25, 25, 25)
                .addComponent(labelNama1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNama1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(labelHarga1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputHarga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelKuantitas2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(229, 229, 229)
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

        labelNama.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNama.setForeground(cp.getColor(0)
        );
        labelNama.setText("Nama Barang");

        inputNama.setHint("Pakaian dinas akademik");

        labelHarga.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelHarga.setForeground(cp.getColor(0)
        );
        labelHarga.setText("Harga");

        inputHarga.setHint("25000");

        labelKuantitas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas.setForeground(cp.getColor(0)
        );
        labelKuantitas.setText("Kuantitas");

        inputKuantitas.setHint("25");

        judulCreate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulCreate.setForeground(cp.getColor(0)
        );
        judulCreate.setText("Tambah Data");

        jPanel11.setOpaque(false);

        btnSaveTbh.setText("Save");
        btnSaveTbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTbhActionPerformed(evt);
            }
        });

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
        btnBackTbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackTbhActionPerformed(evt);
            }
        });

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
                            .addComponent(labelNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(inputKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(labelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelHarga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelKuantitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(229, 229, 229)
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
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );

        cardPanel.add(createPengadaanBarang, "create");

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

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    public void inputKosongException() throws InputKosongException {
        if (inputNama.getText().isBlank() || inputHarga.getText().isBlank() || inputKuantitas.getText().isBlank()) {
            throw new InputKosongException();
        }
    }

    public void inputKosongUpdateException() throws InputKosongException {
        if (inputNama1.getText().isBlank() || inputHarga1.getText().isBlank() || inputKuantitas2.getText().isBlank()) {
            throw new InputKosongException();
        }
    }

    private void btnSaveTbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTbhActionPerformed
//        
    }//GEN-LAST:event_btnSaveTbhActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        resetCreatePanel();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnBackTbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackTbhActionPerformed
        resetUpdatePanel();
    }//GEN-LAST:event_btnBackTbhActionPerformed

    private void fieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldSearchActionPerformed

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
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Barang");

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.swing.component.ButtonOutLine btnBack;
    private view.swing.component.ButtonOutLine btnBackTbh;
    private view.swing.component.ButtonRound btnCancel;
    private view.swing.component.ButtonRound btnCancelTbh;
    private view.swing.component.ButtonRound btnExport;
    private view.swing.component.ButtonRound btnSave;
    private view.swing.component.ButtonRound btnSaveTbh;
    private view.swing.component.ButtonRectangle btnTambah;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel containerCreate;
    private javax.swing.JPanel createPengadaanBarang;
    private view.swing.component.CustomTable customTable;
    private view.swing.component.TextFieldWithBackground fieldSearch;
    private view.swing.component.TextFieldInput inputHarga;
    private view.swing.component.TextFieldInput inputHarga1;
    private view.swing.component.TextFieldInput inputKuantitas;
    private view.swing.component.TextFieldInput inputKuantitas2;
    private view.swing.component.TextFieldInput inputNama;
    private view.swing.component.TextFieldInput inputNama1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judulCreate;
    private javax.swing.JLabel judulDataPilihan;
    private javax.swing.JLabel judulForm;
    private javax.swing.JLabel judulUpdate;
    private javax.swing.JLabel labelHarga;
    private javax.swing.JLabel labelHarga1;
    private javax.swing.JLabel labelKuantitas;
    private javax.swing.JLabel labelKuantitas2;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelNama1;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel readPengadaanBarang;
    private javax.swing.JScrollPane scrollPaneCreate;
    private javax.swing.JPanel updatePembeli;
    // End of variables declaration//GEN-END:variables
}
