/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.formAdmin;

import javaswingdev.formSuperAdmin.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author alwia
 */
public class formCetakRiwayatBK extends javax.swing.JPanel {
    private DefaultTableModel table1 = new DefaultTableModel();
    private DefaultTableModel table2 = new DefaultTableModel();

    /**
     * Creates new form formCetakRiwayat
     */
    public formCetakRiwayatBK() {
        initComponents();
        init();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_transaksi1.setModel(table1);
        table1.addColumn("ID");
        table1.addColumn("Pembeli");
        table1.addColumn("Alamat");
        table1.addColumn("Kontak");
        table1.addColumn("Nama Produk");
        table1.addColumn("Spesifikasi");
        table1.addColumn("QTY");
        table1.addColumn("Total Harga");
        table1.addColumn("Tanggal");
        
        tb_transaksi1.setModel(table2);
        table2.addColumn("NO Surat");
        table2.addColumn("ID");
        table2.addColumn("Pembeli");
        table2.addColumn("Satuan Kerja");
        table2.addColumn("Paket");
        table2.addColumn("Nama Produk");
        table2.addColumn("Spesifikasi");
        table2.addColumn("QTY");
        table2.addColumn("Ongkir");
        table2.addColumn("Harga");
        table2.addColumn("Total Harga");
        table2.addColumn("Tanggal");
 
        tampilData1();
        tampilData2();
    }
    
    private void tampilData1(){
        // Untuk menghapus baris setelah input
        int row = tb_transaksi1.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table1.removeRow(0);
        }

        String query = "SELECT * FROM `tb_transaksi` ";

        try{
            Connection connect = koneksi.getKoneksi(); // Memanggil koneksi
            Statement sttmnt = connect.createStatement(); // Membuat statement
            ResultSet rslt = sttmnt.executeQuery(query); // Menjalankan query

            while (rslt.next()){
                // Menampung data sementara
                String id = rslt.getString("idproduk");
                String pembeli = rslt.getString("pembeli");
                String alamat = rslt.getString("alamat");
                String kontak = rslt.getString("kontak");
                String namaproduk = rslt.getString("namaproduk");
                String spesifikasi = rslt.getString("spesifikasi");
                String jumlahbarang = rslt.getString("jumlahbarang");
                String totalharga = rslt.getString("totalharga");
                String tanggaltransaksi = rslt.getString("tanggaltransaksi");

                // Masukan semua data kedalam array
                String[] data = {id, pembeli, alamat, kontak, namaproduk, spesifikasi, jumlahbarang, totalharga, tanggaltransaksi};
                // Menambahakan baris sesuai dengan data yang tersimpan di array
                table1.addRow(data);
            }
            
            tb_transaksi1.setModel(table1);

        } catch(SQLException e){
            System.out.println(e);
        }
        // Menghitung total belanja tanpa desimal
        double totalBelanja = 0.0;
        for (int i = 0; i < table1.getRowCount(); i++) {
            // Mendapatkan nilai dari kolom "totalharga" pada setiap baris
            double hargaBarang = Double.parseDouble(table1.getValueAt(i, 7).toString());
            // Menambahkan nilai ke dalam totalBelanja
            totalBelanja += hargaBarang;
        }
         // Membuat formatter untuk format ribuan dengan titik
         DecimalFormat formatter = new DecimalFormat("#,###");
         // Menampilkan totalBelanja pada JTextField dengan format ribuan tanpa desimal
         jLabel1.setText(formatter.format(totalBelanja));
    }
    
    private void cari1(){
        int row = tb_transaksi1.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table1.removeRow(0);
        }
        
        String cari = txt_cari1.getText();
        
        String query = "SELECT * FROM `tb_transaksi` WHERE `idproduk`  LIKE '%"+cari+"%' OR `namaproduk` LIKE '%"+cari+"%' OR `pembeli` LIKE '%"+cari+"%' OR `alamat` LIKE '%"+cari+"%' ";
                
       try{
           Connection connect = koneksi.getKoneksi();//memanggil koneksi
           Statement sttmnt = connect.createStatement();//membuat statement
           ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
           
           while (rslt.next()){
                // Menampung data sementara
                String id = rslt.getString("idproduk");
                String pembeli = rslt.getString("pembeli");
                String alamat = rslt.getString("alamat");
                String kontak = rslt.getString("kontak");
                String namaproduk = rslt.getString("namaproduk");
                String spesifikasi = rslt.getString("spesifikasi");
                String jumlahbarang = rslt.getString("jumlahbarang");
                String totalharga = rslt.getString("totalharga");
                String tanggaltransaksi = rslt.getString("tanggaltransaksi");

                // Masukan semua data kedalam array
                String[] data = {id, pembeli, alamat, kontak, namaproduk, spesifikasi, jumlahbarang, totalharga, tanggaltransaksi};
                // Menambahakan baris sesuai dengan data yang tersimpan di array
                table1.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_transaksi1.setModel(table1);
                
            } catch(Exception e){
                   System.out.println(e);
            }
        // Menghitung total belanja tanpa desimal
        double totalBelanja = 0.0;
        for (int i = 0; i < table1.getRowCount(); i++) {
            // Mendapatkan nilai dari kolom "totalharga" pada setiap baris
            double hargaBarang = Double.parseDouble(table1.getValueAt(i, 7).toString());
            // Menambahkan nilai ke dalam totalBelanja
            totalBelanja += hargaBarang;
        }
         // Membuat formatter untuk format ribuan dengan titik
         DecimalFormat formatter = new DecimalFormat("#,###");
         // Menampilkan totalBelanja pada JTextField dengan format ribuan tanpa desimal
         jLabel1.setText(formatter.format(totalBelanja));
    }
    
    private void tampilData2(){
        // Untuk menghapus baris setelah input
        int row = table2.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table2.removeRow(0);
        }

        String query = "SELECT * FROM `tb_mou` ";

        try{
            Connection connect = koneksi.getKoneksi(); // Memanggil koneksi
            Statement sttmnt = connect.createStatement(); // Membuat statement
            ResultSet rslt = sttmnt.executeQuery(query); // Menjalankan query

            while (rslt.next()){
                // Menampung data sementara
                String nosurat = rslt.getString("nosurat");
                String id = rslt.getString("idproduk");
                String pembeli = rslt.getString("pembeli");
                String satuankerja = rslt.getString("satuankerja");
                String paket = rslt.getString("paket");
                String namaproduk = rslt.getString("namaproduk");
                String spesifikasi = rslt.getString("spesifikasi");
                String jumlahbarang = rslt.getString("jumlahbarang");
                String ongkir = rslt.getString("ongkir");
                String harga = rslt.getString("harga");
                String totalharga = rslt.getString("totalharga");
                String tanggaltransaksi = rslt.getString("tanggaltransaksi");

                // Masukan semua data kedalam array
                String[] data = {nosurat, id, pembeli, satuankerja, paket, namaproduk, spesifikasi, jumlahbarang, ongkir, harga, totalharga, tanggaltransaksi};
                // Menambahakan baris sesuai dengan data yang tersimpan di array
                table2.addRow(data);
            }

            tb_transaksi2.setModel(table2);

        } catch(SQLException e){
            System.out.println(e);
        }
        // Menghitung total belanja tanpa desimal
        double totalBelanja = 0.0;
        for (int i = 0; i < table2.getRowCount(); i++) {
            // Mendapatkan nilai dari kolom "totalharga" pada setiap baris
            double hargaBarang = Double.parseDouble(table2.getValueAt(i, 10).toString().replace(",", ""));
            // Menambahkan nilai ke dalam totalBelanja
            totalBelanja += hargaBarang;
        }
        // Membuat formatter untuk format ribuan dengan titik
        DecimalFormat formatter = new DecimalFormat("#,###");
        // Menampilkan totalBelanja pada JTextField dengan format ribuan tanpa desimal
        jLabel2.setText(formatter.format(totalBelanja));
    }

    private void cari2(){
        int row = table2.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table2.removeRow(0);
        }

        String cari = txt_cari2.getText();
        String query = "SELECT * FROM `tb_mou` WHERE `idproduk`  LIKE '%"+cari+"%' OR `namaproduk` LIKE '%"+cari+"%' OR `paket` LIKE '%"+cari+"%' OR `satuankerja` LIKE '%"+cari+"%' ";

        try{
            Connection connect = koneksi.getKoneksi(); // Memanggil koneksi
            Statement sttmnt = connect.createStatement(); // Membuat statement
            ResultSet rslt = sttmnt.executeQuery(query); // Menjalankan query

            while (rslt.next()){
                // Menampung data sementara
                String nosurat = rslt.getString("nosurat");
                String id = rslt.getString("idproduk");
                String pembeli = rslt.getString("pembeli");
                String satuankerja = rslt.getString("satuankerja");
                String paket = rslt.getString("paket");
                String namaproduk = rslt.getString("namaproduk");
                String spesifikasi = rslt.getString("spesifikasi");
                String jumlahbarang = rslt.getString("jumlahbarang");
                String ongkir = rslt.getString("ongkir");
                String harga = rslt.getString("harga");
                String totalharga = rslt.getString("totalharga");
                String tanggaltransaksi = rslt.getString("tanggaltransaksi");

                // Masukan semua data kedalam array
                String[] data = {nosurat, id, pembeli, satuankerja, paket, namaproduk, spesifikasi, jumlahbarang, ongkir, harga, totalharga, tanggaltransaksi};
                // Menambahakan baris sesuai dengan data yang tersimpan di array
                table2.addRow(data);
            }

            tb_transaksi2.setModel(table2);

        } catch(SQLException e){
            System.out.println(e);
        }
        // Menghitung total belanja tanpa desimal
        double totalBelanja = 0.0;
        for (int i = 0; i < table2.getRowCount(); i++) {
            // Mendapatkan nilai dari kolom "totalharga" pada setiap baris
            double hargaBarang = Double.parseDouble(table2.getValueAt(i, 10).toString().replace(",", ""));
            // Menambahkan nilai ke dalam totalBelanja
            totalBelanja += hargaBarang;
        }
        // Membuat formatter untuk format ribuan dengan titik
        DecimalFormat formatter = new DecimalFormat("#,###");
        // Menampilkan totalBelanja pada JTextField dengan format ribuan tanpa desimal
        jLabel2.setText(formatter.format(totalBelanja));
    }

    
    private void init() {
        tb_transaksi1.fixTable(jScrollPane1);
        tb_transaksi2.fixTable(jScrollPane2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_cari1 = new textfield.TextField();
        btn_search1 = new button.Button();
        btn_refresh1 = new button.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_transaksi1 = new javaswingdev.swing.table.Table();
        btn_print1 = new button.Button();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        labelIcon2 = new javaswingdev.card.LabelIcon();
        roundPanel2 = new javaswingdev.swing.RoundPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_cari2 = new textfield.TextField();
        btn_search2 = new button.Button();
        btn_refresh2 = new button.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_transaksi2 = new javaswingdev.swing.table.Table();
        btn_print2 = new button.Button();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelIcon1 = new javaswingdev.card.LabelIcon();

        setOpaque(false);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Riwayat Transaksi");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaswingdev/picture/stocks.png"))); // NOI18N

        txt_cari1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cari1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        btn_search1.setBackground(new java.awt.Color(69, 162, 71));
        btn_search1.setForeground(new java.awt.Color(255, 255, 255));
        btn_search1.setText("S E A R C H");
        btn_search1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search1ActionPerformed(evt);
            }
        });

        btn_refresh1.setBackground(new java.awt.Color(69, 162, 71));
        btn_refresh1.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh1.setText("R E F R E S H");
        btn_refresh1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh1ActionPerformed(evt);
            }
        });

        tb_transaksi1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tb_transaksi1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tb_transaksi1);

        btn_print1.setBackground(new java.awt.Color(69, 162, 71));
        btn_print1.setForeground(new java.awt.Color(255, 255, 255));
        btn_print1.setText("P R I N T");
        btn_print1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_print1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel13.setText("Total Transaksi Rp.");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(69, 162, 71));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        labelIcon2.setColor1(new java.awt.Color(204, 204, 204));
        labelIcon2.setColor2(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(10, 10, 10)
                        .addComponent(txt_cari1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_refresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_print1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelIcon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_refresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_print1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(labelIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel2.setRound(10);

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Riwayat Transaksi MOU");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaswingdev/picture/stocks.png"))); // NOI18N

        txt_cari2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cari2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        btn_search2.setBackground(new java.awt.Color(69, 162, 71));
        btn_search2.setForeground(new java.awt.Color(255, 255, 255));
        btn_search2.setText("S E A R C H");
        btn_search2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search2ActionPerformed(evt);
            }
        });

        btn_refresh2.setBackground(new java.awt.Color(69, 162, 71));
        btn_refresh2.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh2.setText("R E F R E S H");
        btn_refresh2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_refresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh2ActionPerformed(evt);
            }
        });

        tb_transaksi2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tb_transaksi2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tb_transaksi2);

        btn_print2.setBackground(new java.awt.Color(69, 162, 71));
        btn_print2.setForeground(new java.awt.Color(255, 255, 255));
        btn_print2.setText("P R I N T");
        btn_print2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_print2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print2ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel14.setText("Total Transaksi MOU Rp.");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(69, 162, 71));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        labelIcon1.setColor1(new java.awt.Color(204, 204, 204));
        labelIcon1.setColor2(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelIcon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(10, 10, 10)
                        .addComponent(txt_cari2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_search2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_refresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_print2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_cari2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_refresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_search2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_print2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(labelIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
        // TODO add your handling code here:
        cari1();
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void btn_refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refresh1ActionPerformed
        // TODO add your handling code here:
        tampilData1();
        txt_cari1.setText(null);
    }//GEN-LAST:event_btn_refresh1ActionPerformed

    private void btn_print1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print1ActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/javaswingdev/print/printRiwayatBK1.jasper";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap parameter = new HashMap();
            parameter.put("search",txt_cari1.getText());
            parameter.put("total", jLabel1.getText());

            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), parameter, koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_print1ActionPerformed

    private void btn_search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search2ActionPerformed
        // TODO add your handling code here:
        cari2();
    }//GEN-LAST:event_btn_search2ActionPerformed

    private void btn_refresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refresh2ActionPerformed
        // TODO add your handling code here:
        tampilData2();
        txt_cari2.setText(null);
    }//GEN-LAST:event_btn_refresh2ActionPerformed

    private void btn_print2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print2ActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/javaswingdev/print/printRiwayatBK2.jasper";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap parameter = new HashMap();
            parameter.put("search",txt_cari2.getText());
            parameter.put("total", jLabel2.getText());

            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), parameter, koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_print2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.Button btn_print1;
    private button.Button btn_print2;
    private button.Button btn_refresh1;
    private button.Button btn_refresh2;
    private button.Button btn_search1;
    private button.Button btn_search2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javaswingdev.card.LabelIcon labelIcon1;
    private javaswingdev.card.LabelIcon labelIcon2;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.RoundPanel roundPanel2;
    private javaswingdev.swing.table.Table tb_transaksi1;
    private javaswingdev.swing.table.Table tb_transaksi2;
    private textfield.TextField txt_cari1;
    private textfield.TextField txt_cari2;
    // End of variables declaration//GEN-END:variables
}
