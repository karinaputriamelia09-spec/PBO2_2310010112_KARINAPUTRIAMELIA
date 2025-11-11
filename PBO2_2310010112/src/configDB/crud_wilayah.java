/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;

/**
 *
 * @author User
 */
public class crud_wilayah {
    
    private String namaDB = "pertambangan_pbo2";
    private String url = "jdbc:mysql://localhost:3306/" + namaDB;
    private String username = "root";
    private String password = "";
    private Connection koneksi;
    
    // Variabel untuk menampung data dari Frame (digunakan oleh metode baru)
    public String VAR_WILAYAH_PENYIMPANAN = null;
    public Date VAR_TANGGAL_PENYIMPANAN = null;
    public Date VAR_TANGGAL_PEMINDAHAN = null;
    public Date VAR_TANGGAL_RESTOK = null;
    
    public boolean validasi = false;
    
    public crud_wilayah(){
        try {
           // Gunakan com.mysql.cj.jdbc.Driver jika MySQL Connector Anda versi 8.0 ke atas
           // Jika Connector versi lama, biarkan com.mysql.jdbc.Driver
           Driver mysqldriver = new com.mysql.jdbc.Driver(); 
           DriverManager.registerDriver(mysqldriver);
           koneksi = DriverManager.getConnection(url,username,password);
           System.out.print("Berhasil dikoneksikan");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: Pastikan XAMPP dan MySQL Connector JAR sudah terpasang! \n" + error.getMessage());
                
        }
    }
    
    // --- METODE LAMA YANG SUDAH ANDA BUAT (DIPERTAHANKAN) ---
    
    public void simpanWilayah01(String id_wilayah, String wilayah_penyimpanan, String tanggal_penyimpanan, String tanggal_pemindahan, String tanggal_restok){
        try {
            String sql = "insert into wilayah(ID_Wilayah, Wilayah_Penyimpanan, Tanggal_Penyimpanan, Tanggal_Pemindahan, Tanggal_Restok) "
                    + "values('"+id_wilayah+"', '"+wilayah_penyimpanan+"', '"+tanggal_penyimpanan+"', '"+tanggal_pemindahan+"', '"+tanggal_restok+"')";
            
            String cekPrimary = "select * from wilayah where ID_Wilayah= '"+id_wilayah+"'";
            
            Statement check = koneksi.createStatement();
            ResultSet data = check.executeQuery(cekPrimary);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Wilayah sudah terdaftar");
                this.VAR_WILAYAH_PENYIMPANAN = data.getString("Wilayah_Penyimpanan");
                this.VAR_TANGGAL_PENYIMPANAN = data.getDate("Tanggal_Penyimpanan");
                this.VAR_TANGGAL_PEMINDAHAN = data.getDate("Tanggal_Pemindahan");
                this.VAR_TANGGAL_RESTOK = data.getDate("Tanggal_Restok");
                this.validasi = true;
            } else {
                this.validasi = false;
                this.VAR_WILAYAH_PENYIMPANAN = null;
                this.VAR_TANGGAL_PENYIMPANAN = null;
                this.VAR_TANGGAL_PEMINDAHAN = null;
                this.VAR_TANGGAL_RESTOK = null;
                Statement perintah = koneksi.createStatement();
                perintah.execute(sql);
                JOptionPane.showMessageDialog(null, "Data Wilayah berhasil disimpan");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
    
     public void simpanWilayah02(String id_wilayah, String wilayah_penyimpanan, String tanggal_penyimpanan, String tanggal_pemindahan, String tanggal_restok){
        try {
            String sql = "insert into wilayah(ID_Wilayah, Wilayah_Penyimpanan, Tanggal_Penyimpanan, Tanggal_Pemindahan, Tanggal_Restok) value(?, ?, ?, ?, ?)";
            String cekPrimary = "select * from wilayah where ID_Wilayah= ?";
            
            PreparedStatement check = koneksi.prepareStatement(cekPrimary);
            check.setString(1, id_wilayah);
            ResultSet data = check.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Wilayah sudah terdaftar");
                this.VAR_WILAYAH_PENYIMPANAN = data.getString("Wilayah_Penyimpanan");
                this.VAR_TANGGAL_PENYIMPANAN = data.getDate("Tanggal_Penyimpanan");
                this.VAR_TANGGAL_PEMINDAHAN = data.getDate("Tanggal_Pemindahan");
                this.VAR_TANGGAL_RESTOK = data.getDate("Tanggal_Restok");
                this.validasi = true;
            } else {
                this.validasi = false;
                this.VAR_WILAYAH_PENYIMPANAN = null;
                this.VAR_TANGGAL_PENYIMPANAN = null;
                this.VAR_TANGGAL_PEMINDAHAN = null;
                this.VAR_TANGGAL_RESTOK = null;
                PreparedStatement perintah = koneksi.prepareStatement(sql);
                perintah.setString(1, id_wilayah);
                perintah.setString(2, wilayah_penyimpanan);
                perintah.setString(3, tanggal_penyimpanan);
                perintah.setString(4, tanggal_pemindahan);
                perintah.setString(5, tanggal_restok);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Wilayah berhasil disimpan");
            }
            
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
     
     public void ubahWilayah01(String id_wilayah, String wilayah_penyimpanan, String tanggal_penyimpanan, String tanggal_pemindahan, String tanggal_restok){
        try {
            String sql = "update wilayah set Wilayah_Penyimpanan = '"+wilayah_penyimpanan+"', Tanggal_Penyimpanan = '"+tanggal_penyimpanan+"'"
                    + ", Tanggal_Pemindahan = '"+tanggal_pemindahan+"', Tanggal_Restok = '"+tanggal_restok+"' where ID_Wilayah = '"+id_wilayah+"'";
                   
            Statement perintah = koneksi.createStatement();
            perintah.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Wilayah berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
     
      public void ubahWilayah02(String id_wilayah, String wilayah_penyimpanan, String tanggal_penyimpanan, String tanggal_pemindahan, String tanggal_restok){
        try {
            String sql = "update wilayah set Wilayah_Penyimpanan = ?, Tanggal_Penyimpanan = ?, Tanggal_Pemindahan = ?, Tanggal_Restok = ? where ID_Wilayah = ?";
                   
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, wilayah_penyimpanan);
            perintah.setString(2, tanggal_penyimpanan);
            perintah.setString(3, tanggal_pemindahan);
            perintah.setString(4, tanggal_restok);
            perintah.setString(5, id_wilayah);
            
            perintah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Wilayah berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
      
       public void hapusWilayah01(String id_wilayah){
        try {
            String sql = "delete from wilayah where ID_Wilayah = '"+id_wilayah+"'";
                   
            Statement perintah = koneksi.createStatement();
            perintah.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Wilayah berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
       
       public void hapusWilayah02(String id_wilayah){
        try {
            String sql = "delete from wilayah where ID_Wilayah = ?";
                   
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, id_wilayah);
            perintah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Wilayah berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
       
      public void tampilDataWilayah (JTable komponenTable, String SQL){
          try {
              Statement perintah = koneksi.createStatement();
              ResultSet data = perintah.executeQuery(SQL);
              ResultSetMetaData meta = data.getMetaData();
              int jumKolom = meta.getColumnCount();
              DefaultTableModel modelTable = new DefaultTableModel();
              modelTable.addColumn("ID Wilayah");
              modelTable.addColumn("Wilayah Penyimpanan");
              modelTable.addColumn("Tanggal Penyimpanan");
              modelTable.addColumn("Tanggal Pemindahan");
              modelTable.addColumn("Tanggal Restok");
              modelTable.getDataVector().clear();
              modelTable.fireTableDataChanged();
              while (data.next() ) {
                  Object[] row = new Object[jumKolom];
                  for(int i = 1; i <= jumKolom; i++ ){
                      row [i - 1] = data.getObject(i);
                  }
                  modelTable.addRow(row);
              }
              komponenTable.setModel(modelTable);
          } catch (Exception e) {
              // Jika terjadi error, biarkan table kosong (tidak perlu JOptionPane di sini)
          }
      }
      
    // --- METODE BARU UNTUK KOMPATIBILITAS FRAME ---
    
    public void tambahData() {
        try {
            // Gunakan PreparedStatement untuk menangani Date dan nilai null
            String sql = "INSERT INTO wilayah (Wilayah_Penyimpanan, Tanggal_Penyimpanan, Tanggal_Pemindahan, Tanggal_Restok) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, this.VAR_WILAYAH_PENYIMPANAN);
            perintah.setDate(2, this.VAR_TANGGAL_PENYIMPANAN);
            perintah.setDate(3, this.VAR_TANGGAL_PEMINDAHAN);
            perintah.setDate(4, this.VAR_TANGGAL_RESTOK);

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Wilayah berhasil disimpan");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data Wilayah: " + e.getMessage());
        }
    }

    public void updateData(String id_wilayah) {
        try {
            // Gunakan PreparedStatement untuk menangani Date dan nilai null
            String sql = "UPDATE wilayah SET Wilayah_Penyimpanan = ?, Tanggal_Penyimpanan = ?, Tanggal_Pemindahan = ?, Tanggal_Restok = ? WHERE ID_Wilayah = ?";

            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, this.VAR_WILAYAH_PENYIMPANAN);
            perintah.setDate(2, this.VAR_TANGGAL_PENYIMPANAN);
            perintah.setDate(3, this.VAR_TANGGAL_PEMINDAHAN);
            perintah.setDate(4, this.VAR_TANGGAL_RESTOK);
            perintah.setString(5, id_wilayah);

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Wilayah berhasil diubah");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data Wilayah: " + e.getMessage());
        }
    }

    public void hapusData(String id_wilayah) {
        try {
            String sql = "DELETE FROM wilayah WHERE ID_Wilayah = ?";
            
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, id_wilayah);
            
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Wilayah berhasil dihapus");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data Wilayah: " + e.getMessage());
        }
    }
}