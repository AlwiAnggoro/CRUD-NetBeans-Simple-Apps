/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.formSuperAdmin;

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
public class formDataBarang extends javax.swing.JPanel {
    private DefaultTableModel table = new DefaultTableModel();

    /**
     * Creates new form formDataBarang
     */
    public formDataBarang() {
        initComponents();
        init();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_stokbarang.setModel(table);
        table.addColumn("ID Produk");
        table.addColumn("Nama Produk");
        table.addColumn("Spesifikasi");
        table.addColumn("Harga");
        table.addColumn("Ongkir");
        table.addColumn("QTY");
        table.addColumn("Total Harga");
        table.addColumn("Tanggal");
 
        tampilData();
    }
        
    private void init(){
        tb_stokbarang.fixTable(jScrollPane1);
    }
    
    private void tampilData(){
        // Untuk menghapus baris setelah input
        int row = tb_stokbarang.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }

        String query = "SELECT * FROM `tb_stokbarang` ";

        try{
            Connection connect = koneksi.getKoneksi(); // Memanggil koneksi
            Statement sttmnt = connect.createStatement(); // Membuat statement
            ResultSet rslt = sttmnt.executeQuery(query); // Menjalankan query

            while (rslt.next()){
                // Menampung data sementara
                String id = rslt.getString("idproduk");
                String namaproduk = rslt.getString("namaproduk");
                String spesifikasi = rslt.getString("spesifikasi");
                String hargajual = rslt.getString("hargajual");
                String ongkirakhir = rslt.getString("ongkirakhir");
                String stokakhir = rslt.getString("stokakhir");
                String tanggal = rslt.getString("tanggal");

                // Menghitung Total Harga
                double hargaJual = Double.parseDouble(hargajual);
                double stokAkhir = Double.parseDouble(stokakhir);
                double ongkirAkhir = Double.parseDouble(ongkirakhir);
                double totalHarga = hargaJual * stokAkhir + ongkirAkhir;

                // Konversi totalHarga ke dalam string
                String totalHargaStr = String.valueOf((long) totalHarga);

                // Masukan semua data kedalam array
                String[] data = {id, namaproduk, spesifikasi, hargajual, ongkirakhir, stokakhir, totalHargaStr, tanggal};
                // Menambahakan baris sesuai dengan data yang tersimpan di array
                table.addRow(data);
            }
            tb_stokbarang.setModel(table);

        } catch(SQLException e){
            System.out.println(e);
        }
            // Menghitung total belanja tanpa desimal
            double totalBelanja = 0.0;
            for (int i = 0; i < table.getRowCount(); i++) {
                // Mendapatkan nilai dari kolom "totalharga" pada setiap baris
                double hargaBarang = Double.parseDouble(table.getValueAt(i, 6).toString());
                // Menambahkan nilai ke dalam totalBelanja
                totalBelanja += hargaBarang;
            }
             // Membuat formatter untuk format ribuan dengan titik
             DecimalFormat formatter = new DecimalFormat("#,###");
             // Menampilkan totalBelanja pada JTextField dengan format ribuan tanpa desimal
             jLabel1.setText(formatter.format(totalBelanja));
    }
    
    private void cari(){
        int row = tb_stokbarang.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String cari = txt_search.getText();
        
        String query = "SELECT * FROM `tb_stokbarang` WHERE `idproduk`  LIKE '%"+cari+"%' OR `namaproduk` LIKE '%"+cari+"%' OR `spesifikasi` LIKE '%"+cari+"%' ";
                
       try{
           Connection connect = koneksi.getKoneksi();//memanggil koneksi
           Statement sttmnt = connect.createStatement();//membuat statement
           ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
           
           while (rslt.next()){
                //menampung data sementara
                   
                    String id = rslt.getString("idproduk");
                    String nama = rslt.getString("namaproduk");
                    String spek = rslt.getString("spesifikasi");
                    String hargajual = rslt.getString("hargajual");
                    String ongkirakhir = rslt.getString("ongkirakhir");
                    String stokakhir = rslt.getString("stokakhir");
                    String tanggal = rslt.getString("tanggal");

                    // Menghitung Total Harga
                    double hargaJual = Double.parseDouble(hargajual);
                    double stokAkhir = Double.parseDouble(stokakhir);
                    double ongkirAkhir = Double.parseDouble(ongkirakhir);
                    double totalHarga = hargaJual * stokAkhir + ongkirAkhir;

                    // Konversi totalHarga ke dalam string
                    String totalHargaStr = String.valueOf((long) totalHarga);

                    // Masukan semua data kedalam array
                    String[] data = {id, nama, spek, hargajual, ongkirakhir, stokakhir, totalHargaStr, tanggal};
                    // Menambahakan baris sesuai dengan data yang tersimpan di array
                    table.addRow(data);
           }
                //mengeset nilai yang ditampung agar muncul di table
                tb_stokbarang.setModel(table);
                
            } catch(Exception e){
                   System.out.println(e);
            }
            // Menghitung total belanja tanpa desimal
            double totalBelanja = 0.0;
            for (int i = 0; i < table.getRowCount(); i++) {
                // Mendapatkan nilai dari kolom "totalharga" pada setiap baris
                double hargaBarang = Double.parseDouble(table.getValueAt(i, 6).toString());
                // Menambahkan nilai ke dalam totalBelanja
                totalBelanja += hargaBarang;
            }
             // Membuat formatter untuk format ribuan dengan titik
             DecimalFormat formatter = new DecimalFormat("#,###");
             // Menampilkan totalBelanja pada JTextField dengan format ribuan tanpa desimal
             jLabel1.setText(formatter.format(totalBelanja));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_stokbarang = new javaswingdev.swing.table.Table();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_search = new textfield.TextField();
        btn_search = new button.Button();
        btn_print = new button.Button();
        btn_refresh = new button.Button();
        labelIcon2 = new javaswingdev.card.LabelIcon();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        tb_stokbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tb_stokbarang);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Daftar Barang Tersedia");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaswingdev/picture/stocks.png"))); // NOI18N

        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        btn_search.setBackground(new java.awt.Color(69, 162, 71));
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setText(" S E A R C H");
        btn_search.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        btn_print.setBackground(new java.awt.Color(69, 162, 71));
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("P R I N T");
        btn_print.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(69, 162, 71));
        btn_refresh.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh.setText("R E F R E S H");
        btn_refresh.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        labelIcon2.setColor1(new java.awt.Color(204, 204, 204));
        labelIcon2.setColor2(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel13.setText("Total Transaksi Rp.");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(69, 162, 71));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(txt_search, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelIcon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/javaswingdev/print/printDaftarBarang.jasper";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap parameter = new HashMap();
            parameter.put("search",txt_search.getText());
            parameter.put("total", jLabel1.getText());

            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), parameter, koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_search.setText(null);
        tampilData();
    }//GEN-LAST:event_btn_refreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.Button btn_print;
    private button.Button btn_refresh;
    private button.Button btn_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.card.LabelIcon labelIcon2;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.table.Table tb_stokbarang;
    private textfield.TextField txt_search;
    // End of variables declaration//GEN-END:variables
}
