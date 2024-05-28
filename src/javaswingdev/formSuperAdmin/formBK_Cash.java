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
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import javaswingdev.main.SuperAdmin;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author alwia
 */
public class formBK_Cash extends javax.swing.JPanel {
    private DefaultTableModel table1 = new DefaultTableModel();
    private DefaultTableModel table2 = new DefaultTableModel();

    /**
     * Creates new form formBarangMasuk
     */
    public formBK_Cash() {
        initComponents();
        init();
//        tanggal();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_dataBarang.setModel(table1);
        table1.addColumn("ID Produk");
        table1.addColumn("Nama Produk");
        table1.addColumn("Spesifikasi");
        table1.addColumn("Harga");
        table1.addColumn("Stok");
        
        tb_transaksi.setModel(table2);
        table2.addColumn("ID");
        table2.addColumn("Pembeli");
        table2.addColumn("Alamat");
        table2.addColumn("Kontak");
        table2.addColumn("Nama Produk");
        table2.addColumn("Spesifikasi");
        table2.addColumn("QTY");
        table2.addColumn("Total Harga");
        table2.addColumn("Tanggal");
        
        tampilData1();
        tampilData2();
    }
    private void tampilData1(){
        //untuk mengahapus baris setelah input
        int row = tb_dataBarang.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table1.removeRow(0);
        }
        
        String query = "SELECT * FROM `tb_stokbarang` ORDER BY idproduk";
        
        try{
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                   
                    String id = rslt.getString("idproduk");
                    String nama = rslt.getString("namaproduk");
                    String spek = rslt.getString("spesifikasi");
                    String harga = rslt.getString("hargajual");
                    String stok = rslt.getString("stokakhir");
                    
                //masukan semua data kedalam array
                String[] data = {id,nama,spek,harga,stok};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table1.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_dataBarang.setModel(table1);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void cari(){
        int row = tb_dataBarang.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table1.removeRow(0);
        }
        
        String cari = txt_cariDataBarang.getText();
        
        String query = "SELECT * FROM `tb_stokbarang` WHERE `idproduk`  LIKE '%"+cari+"%' OR `namaproduk` LIKE '%"+cari+"%' ";
                
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
                    String stokakhir = rslt.getString("stokakhir");
                    
                //masukan semua data kedalam array
                String[] data = {id,nama,spek,hargajual,stokakhir};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table1.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_dataBarang.setModel(table1);
                
            } catch(Exception e){
                   System.out.println(e);
            }
    }
    
    private void tampilData2() {
        // untuk menghapus baris setelah input
        int row = tb_transaksi.getRowCount();
        for (int a = 0; a < row; a++) {
            table2.removeRow(0);
        }

        String query = "SELECT * FROM `tb_cart`";

        try {
            Connection connect = koneksi.getKoneksi(); // memanggil koneksi
            Statement sttmnt = connect.createStatement(); // membuat statement
            ResultSet rslt = sttmnt.executeQuery(query); // menjalankan query

            while (rslt.next()) {
                // menampung data sementara
                String id = rslt.getString("idproduk");
                String pembeli = rslt.getString("pembeli");
                String alamat = rslt.getString("alamat");
                String kontak = rslt.getString("kontak");
                String namaproduk = rslt.getString("namaproduk");
                String spesifikasi = rslt.getString("spesifikasi");
                String jumlahbarang = rslt.getString("jumlahbarang");
                String hargajual = rslt.getString("totalharga");
                String tanggal = rslt.getString("tanggaltransaksi");

                // masukan semua data kedalam array
                String[] data = {id, pembeli, alamat, kontak, namaproduk, spesifikasi, jumlahbarang, hargajual, tanggal};
                // menambahakan baris sesuai dengan data yang tersimpan diarray
                table2.addRow(data);
            }
            // mengeset nilai yang ditampung agar muncul di table
            tb_transaksi.setModel(table2);

        } catch (SQLException e) {
            System.out.println(e);
        }

        // Menghitung total belanja tanpa desimal
        double totalBelanja = 0.0;
        for (int i = 0; i < table2.getRowCount(); i++) {
            // Mendapatkan nilai dari kolom "Total Harga" pada setiap baris
            double hargaBarang = Double.parseDouble(table2.getValueAt(i, 7).toString());
            // Menambahkan nilai ke dalam totalBelanja
            totalBelanja += hargaBarang;
        }
        // Menampilkan totalBelanja pada JTextField tanpa desimal
        txt_totalBelanja.setText(String.valueOf((int) totalBelanja));
    }


    private void total() {
        String harga = txt_harga.getText();
        String jumlah = txt_qty.getText();

        try {
            int hargaa = Integer.parseInt(harga);
            int jumlahh = Integer.parseInt(jumlah);

            // Melakukan perhitungan dan memastikan hasilnya adalah int
            int total = hargaa * jumlahh;

            // Menampilkan total tanpa desimal
            txt_totalHarga.setText(String.valueOf(total));
        } catch (NumberFormatException e) {
            txt_qty.setText(null);
            txt_totalHarga.setText(null);
        }
    }

    
    private void keranjang() {
        String idproduk = txt_cariDataBarang.getText();
        String pembeli = txt_pembeli.getText();
        String alamat = txt_alamat.getText();
        String kontak = txt_kontak.getText();
        String namaproduk = txt_produk.getText();
        String spesifikasi = txt_spek.getText();
        String jumlah = txt_qty.getText();
        String total = txt_totalHarga.getText();
        String tanggal = txt_tanggal.getText();
        
        //panggil koneksi
        Connection connect = koneksi.getKoneksi();
        //query untuk memasukan data
        String query = "INSERT INTO `tb_transaksi` (`idproduk`, `pembeli`, `alamat`, `kontak`, `namaproduk`, `spesifikasi`, `jumlahbarang`, `totalharga`, `tanggaltransaksi`)"
                + "VALUES ('"+idproduk+"', '"+pembeli+"', '"+alamat+"', '"+kontak+"', '"+namaproduk+"', '"+spesifikasi+"', '"+jumlah+"', '"+total+"', '"+tanggal+"')";

        try {
            //menyiapkan statement untuk di eksekusi
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Data Masuk Ke-Keranjang");

        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");

        } finally {
            tampilData2();
//            clear();

        }
    }
    
    private void totalBelanja() {
        try {
            double bayar = Double.parseDouble(txt_bayar.getText());
            double totalBelanja = Double.parseDouble(txt_totalBelanja.getText());
            double kembalian = bayar - totalBelanja;

            // Menampilkan kembalian tanpa desimal
            txt_kembalian.setText(String.valueOf((int) kembalian));
        } catch (NumberFormatException e) {
            txt_bayar.setText(null);
            txt_kembalian.setText(null);
        }
    }
    
    private void hapusdata() {
        int selectedRow = tb_transaksi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus terlebih dahulu.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null, "Ingin BATALKAN belanja untuk produk ini ?", "Konfirmasi BATALKAN belanja", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            // Dapatkan nilai ID dari kolom yang sesuai (misalnya kolom pertama)
            String idproduk = tb_transaksi.getValueAt(selectedRow, 0).toString();

            // Panggil koneksi
            Connection connect = koneksi.getKoneksi();

            // Query untuk menghapus data berdasarkan ID
            String query = "DELETE FROM `tb_cart` WHERE `tb_cart`.`idproduk` = ?";

            try {
                // Menyiapkan statement untuk dieksekusi
                PreparedStatement ps = connect.prepareStatement(query);
                ps.setString(1, idproduk);

                // Eksekusi query
                int result = ps.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    tampilData2(); // Refresh data tabel setelah penghapusan
                } else {
                    JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
                }
            } catch (SQLException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data");
            }
        }
    }

      
    private void init() {
        tb_transaksi.fixTable(jScrollPane1);
        tb_dataBarang.fixTable(jScrollPane3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateCustom = new com.raven.datechooser.DateChooser();
        btn_dateCustom = new button.Button();
        roundPanel4 = new javaswingdev.swing.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_dataBarang = new javaswingdev.swing.table.Table();
        jLabel9 = new javax.swing.JLabel();
        txt_cariDataBarang = new textfield.TextField();
        txt_refresh = new button.Button();
        jLabel10 = new javax.swing.JLabel();
        txt_searchDataBarang = new button.Button();
        txt_kontak = new textfield.TextField();
        txt_qty = new textfield.TextField();
        txt_alamat = new textfield.TextField();
        jLabel1 = new javax.swing.JLabel();
        txt_spek = new textfield.TextField();
        jLabel5 = new javax.swing.JLabel();
        txt_totalHarga = new textfield.TextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_produk = new textfield.TextField();
        txt_tanggal = new textfield.TextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_pembeli = new textfield.TextField();
        roundPanel2 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_transaksi = new javaswingdev.swing.table.Table();
        btn_print = new button.Button();
        btn_delete = new button.Button();
        btn_reset = new button.Button();
        btn_cart = new button.Button();
        jLabel11 = new javax.swing.JLabel();
        txt_totalBelanja = new textfield.TextField();
        txt_kembalian = new textfield.TextField();
        txt_bayar = new textfield.TextField();
        txt_harga = new textfield.TextField();
        jLabel8 = new javax.swing.JLabel();

        dateCustom.setDateFormat("yyyy-MM-dd");
        dateCustom.setTextRefernce(txt_tanggal);

        btn_dateCustom.setText("...");
        btn_dateCustom.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        btn_dateCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dateCustomActionPerformed(evt);
            }
        });

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1098, 683));

        roundPanel4.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel4.setRound(10);

        tb_dataBarang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tb_dataBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_dataBarang.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tb_dataBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_dataBarangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_dataBarang);

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Data Barang");

        txt_cariDataBarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cariDataBarang.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_refresh.setBackground(new java.awt.Color(69, 162, 71));
        txt_refresh.setForeground(new java.awt.Color(255, 255, 255));
        txt_refresh.setText("R E F R E S H");
        txt_refresh.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_refreshActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaswingdev/picture/stocks.png"))); // NOI18N

        txt_searchDataBarang.setBackground(new java.awt.Color(69, 162, 71));
        txt_searchDataBarang.setForeground(new java.awt.Color(255, 255, 255));
        txt_searchDataBarang.setText("S E A R C H");
        txt_searchDataBarang.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_searchDataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchDataBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(20, 20, 20)
                        .addComponent(txt_cariDataBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(txt_searchDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(txt_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
                        .addGap(4, 4, 4))))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel10))
                    .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_cariDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_searchDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );

        txt_kontak.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kontak.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_qty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_qty.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_qtyKeyReleased(evt);
            }
        });

        txt_alamat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_alamat.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Alamat");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_spek.setEditable(false);
        txt_spek.setBackground(java.awt.SystemColor.controlHighlight);
        txt_spek.setForeground(new java.awt.Color(69, 162, 71));
        txt_spek.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_spek.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_spek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_spekActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Spesifikasi");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_totalHarga.setEditable(false);
        txt_totalHarga.setBackground(java.awt.SystemColor.controlHighlight);
        txt_totalHarga.setForeground(new java.awt.Color(69, 162, 71));
        txt_totalHarga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_totalHarga.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("QTY");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Total Harga");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Kontak");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_produk.setEditable(false);
        txt_produk.setBackground(java.awt.SystemColor.controlHighlight);
        txt_produk.setForeground(new java.awt.Color(69, 162, 71));
        txt_produk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_produk.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_tanggal.setForeground(new java.awt.Color(69, 162, 71));
        txt_tanggal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_tanggal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Pembeli");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nama Produk");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_pembeli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_pembeli.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_pembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_pembeliActionPerformed(evt);
            }
        });

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel2.setRound(10);

        tb_transaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tb_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_transaksi.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(tb_transaksi);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1)
                .addGap(4, 4, 4))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_print.setBackground(new java.awt.Color(69, 162, 71));
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("P R I N T");
        btn_print.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(145, 70, 67));
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("D E L E T E");
        btn_delete.setToolTipText("Pilih data dalam tabel telebih dahulu untuk membatalkan B E L A N J A");
        btn_delete.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_reset.setBackground(new java.awt.Color(145, 70, 67));
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("R E S E T");
        btn_reset.setToolTipText("K O S O N G K A N    K E R A N J A N G   Untuk melakukan transaksi berikutnya");
        btn_reset.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        btn_cart.setBackground(new java.awt.Color(69, 162, 71));
        btn_cart.setForeground(new java.awt.Color(255, 255, 255));
        btn_cart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaswingdev/picture/cart.png"))); // NOI18N
        btn_cart.setText("C A R T");
        btn_cart.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_cart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cartActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Tanggal");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_totalBelanja.setEditable(false);
        txt_totalBelanja.setBackground(java.awt.SystemColor.controlHighlight);
        txt_totalBelanja.setForeground(new java.awt.Color(69, 162, 71));
        txt_totalBelanja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_totalBelanja.setText("T O T A L   H A R G A");
        txt_totalBelanja.setEnabled(false);
        txt_totalBelanja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_totalBelanja.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_totalBelanjaFocusGained(evt);
            }
        });

        txt_kembalian.setEditable(false);
        txt_kembalian.setBackground(java.awt.SystemColor.controlHighlight);
        txt_kembalian.setForeground(new java.awt.Color(69, 162, 71));
        txt_kembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kembalian.setText("K E M B A L I");
        txt_kembalian.setEnabled(false);
        txt_kembalian.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_kembalian.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_kembalianFocusGained(evt);
            }
        });

        txt_bayar.setForeground(new java.awt.Color(69, 162, 71));
        txt_bayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bayar.setText("B A Y A R");
        txt_bayar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_bayar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_bayarFocusGained(evt);
            }
        });
        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
        });

        txt_harga.setEditable(false);
        txt_harga.setBackground(java.awt.SystemColor.controlHighlight);
        txt_harga.setForeground(new java.awt.Color(69, 162, 71));
        txt_harga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_harga.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Harga");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txt_qty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_pembeli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txt_harga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_alamat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_kontak, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txt_totalHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txt_produk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txt_spek, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(roundPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txt_totalBelanja, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_pembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_kontak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_produk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(4, 4, 4)
                        .addComponent(txt_spek, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_totalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_totalBelanja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dateCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dateCustomActionPerformed
        // TODO add your handling code here:
        dateCustom.showPopup();
    }//GEN-LAST:event_btn_dateCustomActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        try {
            String file = "/javaswingdev/print/printBK_Cash.jasper";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap parameter = new HashMap();
            parameter.put("total", txt_totalBelanja.getText());
            parameter.put("bayar", txt_bayar.getText());
            parameter.put("kembalian", txt_kembalian.getText());

            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file), parameter, koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        hapusdata();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:    
    int confirmation = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus semua data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
    if (confirmation == JOptionPane.YES_OPTION) {
        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();

        // Query untuk menghapus semua data dari tabel
        String query = "TRUNCATE TABLE `tb_cart`";

        try {
            // Menyiapkan statement untuk dieksekusi
            Statement statement = connect.createStatement();

            // Eksekusi query
            statement.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Semua data berhasil dihapus");
            tampilData2(); // Refresh data tabel setelah penghapusan
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data");
        }
    }
    }//GEN-LAST:event_btn_resetActionPerformed

    private void tb_dataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_dataBarangMouseClicked
        // TODO add your handling code here:
    int baris = tb_dataBarang.getSelectedRow();

    // Dapatkan nilai-nilai dari baris yang dipilih dan isi field-field input
    String idproduk = tb_dataBarang.getValueAt(baris, 0).toString();
    txt_cariDataBarang.setText(idproduk);
    
    String produk = tb_dataBarang.getValueAt(baris, 1).toString();
    txt_produk.setText(produk);

    String spesifikasi = tb_dataBarang.getValueAt(baris, 2).toString();
    txt_spek.setText(spesifikasi);

    String harga = tb_dataBarang.getValueAt(baris, 3).toString();
    txt_harga.setText(harga);
    }//GEN-LAST:event_tb_dataBarangMouseClicked

    private void txt_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_refreshActionPerformed
        // TODO add your handling code here:
        tampilData1();
        txt_cariDataBarang.setText(null);
        txt_produk.setText(null);
        txt_spek.setText(null);
        txt_harga.setText(null);
    }//GEN-LAST:event_txt_refreshActionPerformed

    private void btn_cartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cartActionPerformed
        // TODO add your handling code here:
        keranjang();
    }//GEN-LAST:event_btn_cartActionPerformed

    private void txt_bayarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bayarFocusGained
        // TODO add your handling code here:
        txt_bayar.setText("");
    }//GEN-LAST:event_txt_bayarFocusGained

    private void txt_totalBelanjaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_totalBelanjaFocusGained
        // TODO add your handling code here:
        txt_totalBelanja.setText("");
    }//GEN-LAST:event_txt_totalBelanjaFocusGained

    private void txt_kembalianFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_kembalianFocusGained
        // TODO add your handling code here:
        txt_kembalian.setText("");
    }//GEN-LAST:event_txt_kembalianFocusGained

    private void txt_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyReleased
        // TODO add your handling code here:
        total();
    }//GEN-LAST:event_txt_qtyKeyReleased

    private void txt_pembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_pembeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pembeliActionPerformed

    private void txt_spekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_spekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_spekActionPerformed

    private void txt_searchDataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchDataBarangActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_txt_searchDataBarangActionPerformed

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        // TODO add your handling code here:
        totalBelanja();
    }//GEN-LAST:event_txt_bayarKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.Button btn_cart;
    private button.Button btn_dateCustom;
    private button.Button btn_delete;
    private button.Button btn_print;
    private button.Button btn_reset;
    private com.raven.datechooser.DateChooser dateCustom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javaswingdev.swing.RoundPanel roundPanel2;
    private javaswingdev.swing.RoundPanel roundPanel4;
    private javaswingdev.swing.table.Table tb_dataBarang;
    private javaswingdev.swing.table.Table tb_transaksi;
    private textfield.TextField txt_alamat;
    private textfield.TextField txt_bayar;
    private textfield.TextField txt_cariDataBarang;
    private textfield.TextField txt_harga;
    private textfield.TextField txt_kembalian;
    private textfield.TextField txt_kontak;
    private textfield.TextField txt_pembeli;
    private textfield.TextField txt_produk;
    private textfield.TextField txt_qty;
    private button.Button txt_refresh;
    private button.Button txt_searchDataBarang;
    private textfield.TextField txt_spek;
    private textfield.TextField txt_tanggal;
    private textfield.TextField txt_totalBelanja;
    private textfield.TextField txt_totalHarga;
    // End of variables declaration//GEN-END:variables
}
