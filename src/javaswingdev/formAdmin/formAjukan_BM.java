package javaswingdev.formAdmin;

import javaswingdev.formSuperAdmin.*;
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

/**
 *
 * @author alwia
 */
public class formAjukan_BM extends javax.swing.JPanel {
    private DefaultTableModel table = new DefaultTableModel();

    /**
     * Creates new form formBarangMasuk
     */
    public formAjukan_BM() {
        initComponents();
        init();
//        tanggal();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        tb_ajukanBM.setModel(table);
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
    
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = tb_ajukanBM.getRowCount();
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
                tb_ajukanBM.setModel(table);
            
        }catch(SQLException e){
            System.out.println(e);
        }
       
    }
    

    private void total() {
        // Ambil nilai jumlah dari txt_qty
        String jumlah = txt_qty.getText(); 
        try {
            // Konversi jumlah ke integer
            int jumlahh = Integer.parseInt(jumlah); 

            // Biaya ongkir per benih untuk ongkir awal (1500) dan ongkir akhir (1800)
            int biayaPerBenihAwal = 1500; 
            int biayaPerBenihAkhir = 1800;

            // Hitung total biaya ongkir awal dan akhir
            int totalOngkirAwal = jumlahh * biayaPerBenihAwal;
            int totalOngkirAkhir = jumlahh * biayaPerBenihAkhir;

            // Ambil nilai harga awal dari txt_hargaAwal
            String hargaAwalStr = txt_hargaAwal.getText();
            int hargaAwal = Integer.parseInt(hargaAwalStr);

            // Hitung total biaya keseluruhan
            int total = (hargaAwal * jumlahh) + totalOngkirAwal;

            // Tampilkan hasil perhitungan pada txt_ongkirAwal, txt_ongkirAkhir, dan txt_totalAwal
            txt_ongkirAwal.setText(Integer.toString(totalOngkirAwal));
            txt_ongkirAkhir.setText(Integer.toString(totalOngkirAkhir));
            txt_totalAwal.setText(Integer.toString(total));
        } catch (NumberFormatException e) {
            // Tangani pengecualian jika jumlah tidak valid
            txt_qty.setText(null);
            txt_ongkirAwal.setText(null);
            txt_ongkirAkhir.setText(null);
            txt_totalAwal.setText(null);
        }
    }
  
    private void tambahData() {
        String supplier = txt_supplier.getText();
        String alamat = txt_alamat.getText();
        String kontak = txt_kontak.getText();
        String namaproduk = txt_produk.getText();
        String stokawal = txt_qty.getText();
        String stokakhir = txt_qty.getText();
        String spesifikasi = txt_spek.getText();
        String hargaawal = txt_hargaAwal.getText();
        String hargajual = txt_hargaJual.getText();
        String ongkirawal = txt_ongkirAwal.getText();
        String ongkirakhir = txt_ongkirAkhir.getText();
        String totalawal = txt_totalAwal.getText();
        String tanggalStr = txt_tanggal.getText();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggal;
        try {
            tanggal = dateFormat.parse(tanggalStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Format tanggal tidak valid");
            return;
        }
        String tanggalFormatted = dateFormat.format(tanggal);

        Connection connect = koneksi.getKoneksi();
        String lastId = null;

        try {
            // Dapatkan ID terakhir dari database
            String queryLastId = "SELECT idproduk FROM tb_validasistok ORDER BY idproduk DESC LIMIT 1";
            PreparedStatement psLastId = connect.prepareStatement(queryLastId);
            ResultSet rs = psLastId.executeQuery();
            if (rs.next()) {
                lastId = rs.getString("id");
            }

            // Generate ID baru
            String newId;
            if (lastId != null) {
                int idNumber = Integer.parseInt(lastId.substring(4)) + 1;
                newId = String.format("PRDK%04d", idNumber);
            } else {
                newId = "PRDK0001";
            }

            // Query untuk memasukkan data
            String query = "INSERT INTO tb_validasistok (idproduk, supplier, alamat, kontak, namaproduk, stokawal, stokakhir, spesifikasi, hargaawal, hargajual, ongkirawal, ongkirakhir, totalawal, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Menyiapkan statement untuk dieksekusi
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, newId);
            ps.setString(2, supplier);
            ps.setString(3, alamat);
            ps.setString(4, kontak);
            ps.setString(5, namaproduk);
            ps.setString(6, stokawal);
            ps.setString(7, stokakhir);
            ps.setString(8, spesifikasi);
            ps.setString(9, hargaawal);
            ps.setString(10, hargajual);
            ps.setString(11, ongkirawal);
            ps.setString(12, ongkirakhir);
            ps.setString(13, totalawal);
            ps.setString(14, tanggalFormatted);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
        } finally {
            tampilData();
            // clear(); // Jika Anda ingin menghapus isian form setelah data disimpan
        }
    }

    private void hapusdata() {
        int selectedRow = tb_ajukanBM.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus terlebih dahulu.");
            return;
        }

        // Dapatkan nilai ID dari kolom yang sesuai (misalnya kolom pertama)
        String id = tb_ajukanBM.getValueAt(selectedRow, 0).toString();

        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();

        // Query untuk menghapus data berdasarkan ID
        String query = "DELETE FROM tb_validasistok WHERE idproduk = ?";

        try {
            // Menyiapkan statement untuk dieksekusi
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, id);

            // Eksekusi query
            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                tampilData(); // Refresh data tabel setelah penghapusan
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
            }
        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data");
        }
    }
    
    
    private void editdata() {
        int selectedRow = tb_ajukanBM.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin diedit terlebih dahulu.");
            return;
        }

        // Dapatkan nilai ID dari kolom yang sesuai (misalnya kolom pertama)
        String id = tb_ajukanBM.getValueAt(selectedRow, 0).toString();

        // Dapatkan data baru dari input pengguna
        String supplier = txt_supplier.getText();
        String alamat = txt_alamat.getText();
        String kontak = txt_kontak.getText();
        String namaproduk = txt_produk.getText();
        String stokakhir = txt_qty.getText(); // Fix: Add stokakhir here
        String spesifikasi = txt_spek.getText();
        String hargaawal = txt_hargaAwal.getText();
        String hargajual = txt_hargaJual.getText();
        String ongkirawal = txt_ongkirAwal.getText();
        String ongkirakhir = txt_ongkirAkhir.getText();
        String totalawal = txt_totalAwal.getText();
        String tanggalStr = txt_tanggal.getText();

        // Parse tanggal dari String ke Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggal;
        try {
            tanggal = dateFormat.parse(tanggalStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Format tanggal tidak valid");
            return;
        }
        String tanggalFormatted = dateFormat.format(tanggal);

        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();

        // Query untuk memperbarui data berdasarkan ID
        String query = "UPDATE tb_validasistok SET supplier = ?, alamat = ?, kontak = ?, namaproduk = ?, stokakhir = ?, spesifikasi = ?, hargaawal = ?, hargajual = ?, ongkirawal = ?, ongkirakhir = ?, totalawal = ?, tanggal = ? WHERE idproduk = ?";

        try {
            // Menyiapkan statement untuk dieksekusi
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, supplier);
            ps.setString(2, alamat);
            ps.setString(3, kontak);
            ps.setString(4, namaproduk);
            ps.setString(5, stokakhir); // Set stokakhir at parameter 5
            ps.setString(6, spesifikasi);
            ps.setString(7, hargaawal);
            ps.setString(8, hargajual);
            ps.setString(9, ongkirawal);
            ps.setString(10, ongkirakhir);
            ps.setString(11, totalawal);
            ps.setString(12, tanggalFormatted);
            ps.setString(13, id); // Set id at parameter 13

            // Eksekusi query
            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
                tampilData(); // Refresh data tabel setelah pengeditan
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Diperbarui");
            }
        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memperbarui data");
        }
    }
    

    private void cari() {
        String keyword = txt_search.getText(); // Ambil kata kunci pencarian

        // Query pencarian data
        String query = "SELECT * FROM tb_validasistok WHERE namaproduk LIKE ? OR supplier LIKE ? OR alamat LIKE ? OR kontak LIKE ? OR spesifikasi LIKE ?";

        try {
            Connection connect = koneksi.getKoneksi(); // Panggil koneksi
            PreparedStatement ps = connect.prepareStatement(query); // Menyiapkan statement

            // Set parameter untuk pencarian
            for (int i = 1; i <= 5; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery(); // Jalankan query

            // Hapus semua baris yang ada di tabel sebelum menampilkan hasil pencarian
            int row = tb_ajukanBM.getRowCount();
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
            tb_ajukanBM.setModel(table);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    private void init() {
        tb_ajukanBM.fixTable(jScrollPane1);
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
        roundPanel1 = new javaswingdev.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_supplier = new textfield.TextField();
        txt_alamat = new textfield.TextField();
        txt_kontak = new textfield.TextField();
        txt_produk = new textfield.TextField();
        txt_spek = new textfield.TextField();
        txt_hargaAwal = new textfield.TextField();
        txt_hargaJual = new textfield.TextField();
        txt_ongkirAwal = new textfield.TextField();
        txt_ongkirAkhir = new textfield.TextField();
        txt_qty = new textfield.TextField();
        txt_totalAwal = new textfield.TextField();
        txt_tanggal = new textfield.TextField();
        jPanel1 = new javax.swing.JPanel();
        btn_search = new button.Button();
        roundPanel2 = new javaswingdev.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_ajukanBM = new javaswingdev.swing.table.Table();
        txt_search = new textfield.TextField();
        txt_add = new button.Button();
        btn_refresh = new button.Button();
        btn_print = new button.Button();
        btn_edit = new button.Button();
        btn_delete = new button.Button();

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
        setPreferredSize(new java.awt.Dimension(1018, 693));

        roundPanel1.setBackground(new java.awt.Color(248, 248, 248));
        roundPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel1.setToolTipText("");
        roundPanel1.setRound(10);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Supplier");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Alamat");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Kontak");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nama Produk");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Spesifikasi");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Harga Awal");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Harga Jual");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ongkir Awal *1500");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total Awal");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Ongkir Akhir *1800");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("QTY");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tanggal Kirim");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txt_supplier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_supplier.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_alamat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_alamat.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_kontak.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kontak.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_produk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_produk.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_spek.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_spek.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_spek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_spekActionPerformed(evt);
            }
        });

        txt_hargaAwal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_hargaAwal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_hargaJual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_hargaJual.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_ongkirAwal.setEditable(false);
        txt_ongkirAwal.setBackground(java.awt.SystemColor.controlHighlight);
        txt_ongkirAwal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ongkirAwal.setDisabledTextColor(new java.awt.Color(69, 162, 71));
        txt_ongkirAwal.setEnabled(false);
        txt_ongkirAwal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_ongkirAkhir.setEditable(false);
        txt_ongkirAkhir.setBackground(java.awt.SystemColor.controlHighlight);
        txt_ongkirAkhir.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ongkirAkhir.setDisabledTextColor(new java.awt.Color(69, 162, 71));
        txt_ongkirAkhir.setEnabled(false);
        txt_ongkirAkhir.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_qty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_qty.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_qtyKeyReleased(evt);
            }
        });

        txt_totalAwal.setEditable(false);
        txt_totalAwal.setBackground(java.awt.SystemColor.controlHighlight);
        txt_totalAwal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_totalAwal.setDisabledTextColor(new java.awt.Color(69, 162, 71));
        txt_totalAwal.setEnabled(false);
        txt_totalAwal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_tanggal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_tanggal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(248, 248, 248));

        btn_search.setBackground(new java.awt.Color(69, 162, 71));
        btn_search.setForeground(new java.awt.Color(255, 255, 255));
        btn_search.setText("S E A R C H");
        btn_search.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        roundPanel2.setRound(10);

        tb_ajukanBM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tb_ajukanBM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_ajukanBM.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tb_ajukanBM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_ajukanBMMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_ajukanBM);

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
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
        );

        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_search.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        txt_add.setBackground(new java.awt.Color(69, 162, 71));
        txt_add.setForeground(new java.awt.Color(255, 255, 255));
        txt_add.setText("A J U K A N   S T O K");
        txt_add.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txt_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_addActionPerformed(evt);
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

        btn_print.setBackground(new java.awt.Color(69, 162, 71));
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("P R I N T");
        btn_print.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(69, 162, 71));
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("E D I T");
        btn_edit.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(txt_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(txt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_supplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_ongkirAkhir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_spek, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_qty, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_hargaAwal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_totalAwal, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(txt_hargaJual, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(txt_kontak, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_produk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(txt_ongkirAwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addGap(30, 30, 30))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addGap(3, 3, 3)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_kontak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_produk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(3, 3, 3)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_spek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_hargaAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_ongkirAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addGap(3, 3, 3)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ongkirAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_totalAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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

    private void btn_dateCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dateCustomActionPerformed
        // TODO add your handling code here:
        dateCustom.showPopup();
    }//GEN-LAST:event_btn_dateCustomActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void txt_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_addActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_txt_addActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_supplier.setText(null);
        txt_alamat.setText(null);
        txt_kontak.setText(null);
        txt_produk.setText(null);
        txt_spek.setText(null);
        txt_hargaAwal.setText(null);
        txt_hargaJual.setText(null);
        txt_ongkirAwal.setText(null);
        txt_ongkirAkhir.setText(null);
        txt_qty.setText(null);
        txt_totalAwal.setText(null);
        tampilData();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        editdata();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        hapusdata();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txt_spekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_spekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_spekActionPerformed

    private void txt_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyReleased
        // TODO add your handling code here:
        total();
    }//GEN-LAST:event_txt_qtyKeyReleased

    private void tb_ajukanBMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_ajukanBMMouseClicked
        // TODO add your handling code here:
   int baris = tb_ajukanBM.getSelectedRow();

    // Dapatkan nilai-nilai dari baris yang dipilih dan isi field-field input
    String supplier = tb_ajukanBM.getValueAt(baris, 1).toString();
    txt_supplier.setText(supplier);

    String alamat = tb_ajukanBM.getValueAt(baris, 2).toString();
    txt_alamat.setText(alamat);

    String kontak = tb_ajukanBM.getValueAt(baris, 3).toString();
    txt_kontak.setText(kontak);

    String namaproduk = tb_ajukanBM.getValueAt(baris, 4).toString();
    txt_produk.setText(namaproduk);

    String spesifikasi = tb_ajukanBM.getValueAt(baris, 5).toString();
    txt_spek.setText(spesifikasi);

    String hargaawal = tb_ajukanBM.getValueAt(baris, 6).toString();
    txt_hargaAwal.setText(hargaawal);

    String hargajual = tb_ajukanBM.getValueAt(baris, 7).toString();
    txt_hargaJual.setText(hargajual);

    String ongkirawal = tb_ajukanBM.getValueAt(baris, 8).toString();
    txt_ongkirAwal.setText(ongkirawal);

    String ongkirakhir = tb_ajukanBM.getValueAt(baris, 9).toString();
    txt_ongkirAkhir.setText(ongkirakhir);

    String stokakhir = tb_ajukanBM.getValueAt(baris, 10).toString();
    txt_qty.setText(stokakhir);

    String totalawal = tb_ajukanBM.getValueAt(baris, 11).toString();
    txt_totalAwal.setText(totalawal);

    String tanggal = tb_ajukanBM.getValueAt(baris, 12).toString();
    txt_tanggal.setText(tanggal);
    }//GEN-LAST:event_tb_ajukanBMMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.Button btn_dateCustom;
    private button.Button btn_delete;
    private button.Button btn_edit;
    private button.Button btn_print;
    private button.Button btn_refresh;
    private button.Button btn_search;
    private com.raven.datechooser.DateChooser dateCustom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javaswingdev.swing.RoundPanel roundPanel1;
    private javaswingdev.swing.RoundPanel roundPanel2;
    private javaswingdev.swing.table.Table tb_ajukanBM;
    private button.Button txt_add;
    private textfield.TextField txt_alamat;
    private textfield.TextField txt_hargaAwal;
    private textfield.TextField txt_hargaJual;
    private textfield.TextField txt_kontak;
    private textfield.TextField txt_ongkirAkhir;
    private textfield.TextField txt_ongkirAwal;
    private textfield.TextField txt_produk;
    private textfield.TextField txt_qty;
    private textfield.TextField txt_search;
    private textfield.TextField txt_spek;
    private textfield.TextField txt_supplier;
    private textfield.TextField txt_tanggal;
    private textfield.TextField txt_totalAwal;
    // End of variables declaration//GEN-END:variables
}
