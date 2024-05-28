package javaswingdev.formSuperAdmin;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

public class formV_PenggunaBaru extends javax.swing.JPanel {
    private DefaultTableModel table = new DefaultTableModel();
    public formV_PenggunaBaru() {
        initComponents();
        init();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tableV_User.setModel(table);
        table.addColumn("#");
        table.addColumn("Username");
        table.addColumn("Hak Akses");
        table.addColumn("Tanggal Daftar");
 
        tampilData();
    }
        
    private void init(){
        tableV_User.fixTable(jScrollPane1);
    }
    
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = tableV_User.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String query = "SELECT * FROM `tb_register` ";
        
        try{
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                
                    String id = rslt.getString("id");
                    String username = rslt.getString("username");
                    String hak_akses = rslt.getString("hak_akses");
                    String tanggal_daftar = rslt.getString("tanggal");
                    
                //masukan semua data kedalam array
                String[] data = {id,username,hak_akses, tanggal_daftar};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tableV_User.setModel(table);
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    private void hapusData() {
        int selectedRow = tableV_User.getSelectedRow(); // Get the selected row index

        if (selectedRow != -1) { // If a row is selected
            String id = table.getValueAt(selectedRow, 0).toString(); // Get the id of the selected row

            Connection connect = koneksi.getKoneksi();
            String query = "DELETE FROM `tb_register` WHERE `id` = ?";

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
                    String query = "TRUNCATE TABLE `tb_register`";
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

    private void cari(){
        int row = tableV_User.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
            String cari = txt_search.getText();
                        
        
        String query = "SELECT * FROM `tb_register` WHERE "
                + "(`username` LIKE '%"+cari+"%' OR"
                + "`hak_akses`  LIKE '%"+cari+"%') ";
                
        try{
            tableV_User.setModel(new DefaultTableModel(null, new Object[]{"id","username","hak_akses"}));
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
           
           while (rslt.next()){
                //menampung data sementara
                   
                    String id = rslt.getString("id");
                    String username = rslt.getString("username");
                    String hak_akses = rslt.getString("hak_akses");
                    
                //masukan semua data kedalam array
                String[] data = {id,username,hak_akses};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tableV_User.setModel(table);
           
    }catch(Exception e){
           System.out.println("Error cari : "+e);
    }
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel3 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableV_User = new javaswingdev.swing.table.Table();
        btn_search = new button.Button();
        txt_search = new textfield.TextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_refresh = new button.Button();
        btn_validate = new button.Button();
        btn_delete = new button.Button();

        setOpaque(false);

        roundPanel3.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel3.setRound(10);

        tableV_User.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tableV_User.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableV_User);

        btn_search.setBackground(new java.awt.Color(69, 162, 71));
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setText(" S E A R C H");
        btn_search.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Validate User's");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaswingdev/picture/users.png"))); // NOI18N

        btn_refresh.setBackground(new java.awt.Color(69, 162, 71));
        btn_refresh.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh.setText("R E F R E S H");
        btn_refresh.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txt_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_validate.setBackground(new java.awt.Color(69, 162, 71));
        btn_validate.setForeground(new java.awt.Color(255, 255, 255));
        btn_validate.setText("V A L I D A T E");
        btn_validate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_validate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_validateActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(145, 70, 67));
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("D E L E T E");
        btn_delete.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btn_validate, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_validate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void btn_validateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_validateActionPerformed
        // TODO add your handling code here:
    try {
        Connection connect = koneksi.getKoneksi();

        // Get data from tb_register
        String cari = txt_search.getText(); // Get the search keyword
        String querySelect = "SELECT username, password, hak_akses, tanggal FROM tb_register";

        // If there's a search keyword, add it to the query
        if (!cari.isEmpty()) {
            querySelect += " WHERE username LIKE '%" + cari + "%'";
        }

        PreparedStatement psSelect = connect.prepareStatement(querySelect);
        ResultSet rs = psSelect.executeQuery();

        // Show confirmation dialog before inserting data into tb_login
        int insertOption = JOptionPane.showOptionDialog(null, "Do You Want Validate This User?", "Confirm Insertion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (insertOption == JOptionPane.YES_OPTION) {
            // Get the maximum ID from the tb_login table
            String getMaxIdQuery = "SELECT MAX(CAST(SUBSTRING(id, 4) AS UNSIGNED)) FROM tb_login";
            PreparedStatement getMaxIdStatement = connect.prepareStatement(getMaxIdQuery);
            ResultSet idResultSet = getMaxIdStatement.executeQuery();

            int maxId = 0;
            if (idResultSet.next()) {
                maxId = idResultSet.getInt(1);
            }

            // Insert data into tb_login
            String queryInsert = "INSERT INTO tb_login (id, username, password, hak_akses, tanggal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psInsert = connect.prepareStatement(queryInsert);

            while (rs.next()) {
                // Generate the new ID by incrementing the maximum ID
                int newId = maxId + 1;
                String formattedId = String.format("OPR%04d", newId);
                maxId = newId; // Update maxId for the next loop iteration

                String username = rs.getString("username");
                String password = rs.getString("password");
                String hak_akses = rs.getString("hak_akses");
                java.sql.Date registrationDate = rs.getDate("tanggal");

                psInsert.setString(1, formattedId); // Set the auto-generated formatted ID
                psInsert.setString(2, username);
                psInsert.setString(3, password);
                psInsert.setString(4, hak_akses);
                psInsert.setDate(5, registrationDate);

                psInsert.executeUpdate();
            }

            // Show confirmation dialog before deleting records from tb_register
            int deleteOption = JOptionPane.showOptionDialog(null, "New User has been Added. Do you want to delete the records?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (deleteOption == JOptionPane.YES_OPTION) {
                // Delete records from tb_register based on the search criteria
                if (!cari.isEmpty()) {
                    String queryDelete = "DELETE FROM tb_register WHERE username LIKE '%" + cari + "%'";
                    PreparedStatement psDelete = connect.prepareStatement(queryDelete);
                    psDelete.executeUpdate();
                } else {
                    // Truncate tb_register if no search criteria provided
                    String queryTruncate = "TRUNCATE TABLE tb_register";
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

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_btn_searchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.Button btn_delete;
    private button.Button btn_refresh;
    private button.Button btn_search;
    private button.Button btn_validate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel3;
    private javaswingdev.swing.table.Table tableV_User;
    private textfield.TextField txt_search;
    // End of variables declaration//GEN-END:variables

}
