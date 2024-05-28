/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package javaswingdev.formSuperAdmin;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author alwia
 */
public class formV_BarangMasuk extends javax.swing.JPanel {
    private DefaultTableModel table = new DefaultTableModel();

    /**
     * Creates new form formV_BarangMasuk
     */
    public formV_BarangMasuk() {
        initComponents();
        init();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_validateStocks.setModel(table);
        table.addColumn("ID");
        table.addColumn("Supplier");
        table.addColumn("Alamat");
        table.addColumn("Kontak");
        table.addColumn("Nama Produk");
        table.addColumn("Spesifikasi");
        table.addColumn("Harga Awal");
        table.addColumn("Harga Jual");
        table.addColumn("Ongkir Awal");
        table.addColumn("Ongkir Akhir");
        table.addColumn("QTY");
        table.addColumn("Total Awal");
        table.addColumn("Tanggal");
 
        tampilData();
    }
        
    private void init(){
        tb_validateStocks.fixTable(jScrollPane1);
    }
    
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = tb_validateStocks.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String query = "SELECT * FROM `tb_validasistok` ";
        
        try{
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                
                    String id = rslt.getString("idproduk");
                    String supplier = rslt.getString("supplier");
                    String alamat = rslt.getString("alamat");
                    String kontak = rslt.getString("kontak");
                    String namaproduk = rslt.getString("namaproduk");
                    String spesifikasi = rslt.getString("spesifikasi");
//                    String stokawal = rslt.getString("stokawal");
                    String hargaawal = rslt.getString("hargaawal");
                    String hargajual = rslt.getString("hargajual");
                    String ongkirawal = rslt.getString("ongkirawal");
                    String ongkirakhir = rslt.getString("ongkirakhir");
                    String stokakhir = rslt.getString("stokakhir");
                    String totalawal = rslt.getString("totalawal");
                    String tanggal = rslt.getString("tanggal");
                    
                    
                //masukan semua data kedalam array
                String[] data = {id,supplier,alamat,kontak,namaproduk,spesifikasi,hargaawal,hargajual,ongkirawal,ongkirakhir,stokakhir,totalawal,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_validateStocks.setModel(table);
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    private void hapusData() {
        int selectedRow = tb_validateStocks.getSelectedRow(); // Get the selected row index

        if (selectedRow != -1) { // If a row is selected
            String id = table.getValueAt(selectedRow, 0).toString(); // Get the id of the selected row

            Connection connect = koneksi.getKoneksi();
            String query = "DELETE FROM `tb_validasistok` WHERE `idproduk` = ?";

            int returnValue = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE?", "DELETE Data !!", JOptionPane.YES_NO_OPTION);

            if (returnValue == JOptionPane.YES_OPTION) {
                try {
                    PreparedStatement ps = connect.prepareStatement(query);
                    ps.setString(1, id);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                } catch (SQLException | HeadlessException e) {
                    System.out.println(e);
                } finally {
                    tampilData();
                }
            } else if (returnValue == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
                this.setVisible(true);
            }
        } else {
            int deleteOption = JOptionPane.showOptionDialog(null, "Are you sure you want to DELETE ALL records ?", "DELETE Data !!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Delete All", "Cancel"}, "Delete All");
            if (deleteOption == JOptionPane.YES_OPTION) { // Delete all records
                int deleteAllConfirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to DELETE ALL records?", "DELETE ALL Data !!", JOptionPane.YES_NO_OPTION);
                if (deleteAllConfirmation == JOptionPane.YES_OPTION) {
                    Connection connect = koneksi.getKoneksi();
                    String query = "TRUNCATE TABLE `tb_validasistok`";
                    try {
                        PreparedStatement ps = connect.prepareStatement(query);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "All records deleted successfully");
                    } catch (SQLException | HeadlessException e) {
                        System.out.println(e);
                    } finally {
                        tampilData();
                    }
                }
            }
        }
    }

    private void cari() {
        String keyword = txt_search.getText(); // Ambil kata kunci pencarian

        // Query pencarian data
        String query = "SELECT * FROM tb_validasistok WHERE idproduk LIKE ? OR namaproduk LIKE ? OR supplier LIKE ? OR alamat LIKE ? OR kontak LIKE ? OR spesifikasi LIKE ?";

        try {
            Connection connect = koneksi.getKoneksi(); // Panggil koneksi
            PreparedStatement ps = connect.prepareStatement(query); // Menyiapkan statement

            // Set parameter untuk pencarian
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery(); // Jalankan query

            // Hapus semua baris yang ada di tabel sebelum menampilkan hasil pencarian
            int row = tb_validateStocks.getRowCount();
            for(int a = 0 ; a < row ; a++){
                table.removeRow(0);
            }

            // Tampilkan hasil pencarian
            while (rs.next()){
                // Ambil data dari hasil pencarian
                String id = rs.getString("idproduk");
                String supplier = rs.getString("supplier");
                String alamat = rs.getString("alamat");
                String kontak = rs.getString("kontak");
                String namaproduk = rs.getString("namaproduk");
                String spesifikasi = rs.getString("spesifikasi");
                String hargaawal = rs.getString("hargaawal");
                String hargajual = rs.getString("hargajual");
                String ongkirawal = rs.getString("ongkirawal");
                String ongkirakhir = rs.getString("ongkirakhir");
                String stokakhir = rs.getString("stokakhir");
                String totalawal = rs.getString("totalawal");
                String tanggal = rs.getString("tanggal");

                // Masukkan data ke dalam array
                String[] data = {id, supplier, alamat, kontak, namaproduk, spesifikasi, hargaawal, hargajual, ongkirawal, ongkirakhir, stokakhir, totalawal, tanggal};
                // Tambahkan baris sesuai dengan data yang tersimpan di array
                table.addRow(data);
            }
            // Set nilai yang ditampilkan agar muncul di tabel
            tb_validateStocks.setModel(table);

        } catch (SQLException e) {
            System.out.println(e);
        }
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
        tb_validateStocks = new javaswingdev.swing.table.Table();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_search = new textfield.TextField();
        btn_search = new button.Button();
        btn_refresh = new button.Button();
        btn_delete = new button.Button();
        btn_validate = new button.Button();

        setOpaque(false);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setRound(10);

        tb_validateStocks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tb_validateStocks);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Validate Stock's");

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

        btn_refresh.setBackground(new java.awt.Color(69, 162, 71));
        btn_refresh.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh.setText("R E F R E S H");
        btn_refresh.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txt_search, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_delete.setBackground(new java.awt.Color(145, 70, 67));
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("D E L E T E");
        btn_delete.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_validate.setBackground(new java.awt.Color(69, 162, 71));
        btn_validate.setForeground(new java.awt.Color(255, 255, 255));
        btn_validate.setText("V A L I D A T E");
        btn_validate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_validate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btn_validate, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_validate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_search.setText(null);
        tampilData();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        txt_search.setText(null);
        hapusData();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_validateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validateActionPerformed
        // TODO add your handling code here: 
    try {
        Connection connect = koneksi.getKoneksi();

        // Get data from tb_validasistok
        String cari = txt_search.getText(); // Get the search keyword
        String querySelect = "SELECT idproduk, supplier, alamat, kontak, namaproduk, spesifikasi, hargaawal, hargajual, ongkirawal, ongkirakhir, stokawal, stokakhir, totalawal, tanggal FROM tb_validasistok";

        // If there's a search keyword, add it to the query
        if (!cari.isEmpty()) {
            querySelect += " WHERE idproduk LIKE '%" + cari + "%' OR namaproduk LIKE '%" + cari + "%' OR spesifikasi LIKE '%" + cari + "%'";
        }

        PreparedStatement psSelect = connect.prepareStatement(querySelect);
        ResultSet rs = psSelect.executeQuery();

        // Show confirmation dialog before inserting data into tb_stokbarang
        int insertOption = JOptionPane.showOptionDialog(null, "Do You Want Validate This Stock?", "Confirm Insertion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (insertOption == JOptionPane.YES_OPTION) {
            // Get the maximum ID from the tb_stokbarang table
            String getMaxIdQuery = "SELECT MAX(CAST(SUBSTRING(idproduk, 5) AS UNSIGNED)) FROM tb_stokbarang";
            PreparedStatement getMaxIdStatement = connect.prepareStatement(getMaxIdQuery);
            ResultSet idResultSet = getMaxIdStatement.executeQuery();

            int maxId = 0;
            if (idResultSet.next()) {
                maxId = idResultSet.getInt(1);
            }

            // Insert data into tb_stokbarang
            String queryInsert = "INSERT INTO tb_stokbarang (idproduk, supplier, alamat, kontak, namaproduk, spesifikasi, hargaawal, hargajual, ongkirawal, ongkirakhir, stokawal, stokakhir, totalawal, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = connect.prepareStatement(queryInsert);

            while (rs.next()) {
                // Generate the new ID by incrementing the maximum ID
                int newId = maxId + 1;
                String formattedId = String.format("PRDK%04d", newId);
                maxId = newId; // Update maxId for the next loop iteration

                psInsert.setString(1, formattedId); // Set the auto-generated formatted ID
                psInsert.setString(2, rs.getString("supplier"));
                psInsert.setString(3, rs.getString("alamat"));
                psInsert.setString(4, rs.getString("kontak"));
                psInsert.setString(5, rs.getString("namaproduk"));
                psInsert.setString(6, rs.getString("spesifikasi"));
                psInsert.setString(7, rs.getString("hargaawal"));
                psInsert.setString(8, rs.getString("hargajual"));
                psInsert.setString(9, rs.getString("ongkirawal"));
                psInsert.setString(10, rs.getString("ongkirakhir"));
                psInsert.setString(11, rs.getString("stokawal"));
                psInsert.setString(12, rs.getString("stokakhir"));
                psInsert.setString(13, rs.getString("totalawal"));
                psInsert.setDate(14, rs.getDate("tanggal"));

                psInsert.executeUpdate();
            }

            // Show confirmation dialog before deleting records from tb_validasistok
            int deleteOption = JOptionPane.showOptionDialog(null, "New Stock has been Added. Do you want to delete the records?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (deleteOption == JOptionPane.YES_OPTION) {
                // Delete records from tb_validasistok based on the search criteria
                if (!cari.isEmpty()) {
                    String queryDelete = "DELETE FROM tb_validasistok WHERE idproduk LIKE '%" + cari + "%' OR namaproduk LIKE '%" + cari + "%' OR spesifikasi LIKE '%" + cari + "%'";
                    PreparedStatement psDelete = connect.prepareStatement(queryDelete);
                    psDelete.executeUpdate();
                } else {
                    // Truncate tb_validasistok if no search criteria provided
                    String queryTruncate = "TRUNCATE TABLE tb_validasistok";
                    PreparedStatement psTruncate = connect.prepareStatement(queryTruncate);
                    psTruncate.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Data deleted successfully");
            }
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
    }//GEN-LAST:event_btn_validateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.Button btn_delete;
    private button.Button btn_refresh;
    private button.Button btn_search;
    private button.Button btn_validate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.table.Table tb_validateStocks;
    private textfield.TextField txt_search;
    // End of variables declaration//GEN-END:variables
}
