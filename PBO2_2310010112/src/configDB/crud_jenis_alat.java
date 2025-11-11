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

/**
 *
 * @author User
 */
public class crud_jenis_alat {
    
    private String namaDB = "pertambangan_pbo2";
    private String url = "jdbc:mysql://localhost:3306/" + namaDB;
    private String username = "root";
    private String password = "";
    private Connection koneksi;
    public String VAR_NAMA_ALAT = null;
    public String VAR_DESKRIPSI = null;
    public boolean validasi = false;
    
    public crud_jenis_alat(){
        try {
           Driver mysqldriver = new com.mysql.jdbc.Driver();
           DriverManager.registerDriver(mysqldriver);
           koneksi = DriverManager.getConnection(url,username,password);
           System.out.print("Berhasil dikoneksikan");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
                
        }
    }
    
    public void simpanAlat01(String id_alat, String nama_alat, String deskripsi){
        try {
            String sql = "insert into jenis_alat(ID_Alat, Nama_Alat, Deskripsi) "
                    + "values('"+id_alat+"', '"+nama_alat+"', '"+deskripsi+"')";
            
            String cekPrimary = "select * from jenis_alat where ID_Alat= '"+id_alat+"'";
            
            Statement check = koneksi.createStatement();
            ResultSet data = check.executeQuery(cekPrimary);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Alat sudah terdaftar");
                this.VAR_NAMA_ALAT = data.getString("Nama_Alat");
                this.VAR_DESKRIPSI = data.getString("Deskripsi");
                this.validasi = true;
            } else {
                this.validasi = false;
                this.VAR_NAMA_ALAT = null;
                this.VAR_DESKRIPSI = null;
                Statement perintah = koneksi.createStatement();
                perintah.execute(sql);
                JOptionPane.showMessageDialog(null, "Data Alat berhasil disimpan");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
    
     public void simpanAlat02(String id_alat, String nama_alat, String deskripsi){
        try {
            String sql = "insert into jenis_alat(ID_Alat, Nama_Alat, Deskripsi) value(?, ?, ?)";
            String cekPrimary = "select * from jenis_alat where ID_Alat= ?";
            
            PreparedStatement check = koneksi.prepareStatement(cekPrimary);
            check.setString(1, id_alat);
            ResultSet data = check.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Alat sudah terdaftar");
                this.VAR_NAMA_ALAT = data.getString("Nama_Alat");
                this.VAR_DESKRIPSI = data.getString("Deskripsi");
                this.validasi = true;
            } else {
                this.validasi = false;
                this.VAR_NAMA_ALAT = null;
                this.VAR_DESKRIPSI = null;
                PreparedStatement perintah = koneksi.prepareStatement(sql);
                perintah.setString(1, id_alat);
                perintah.setString(2, nama_alat);
                perintah.setString(3, deskripsi);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Alat berhasil disimpan");
            }
            
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
     
     public void ubahAlat01(String id_alat, String nama_alat, String deskripsi){
        try {
            String sql = "update jenis_alat set Nama_Alat = '"+nama_alat+"', Deskripsi = '"+deskripsi+"'"
                    + " where ID_Alat = '"+id_alat+"'";
                   
            Statement perintah = koneksi.createStatement();
            perintah.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Alat berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
     
      public void ubahAlat02(String id_alat, String nama_alat, String deskripsi){
        try {
            String sql = "update jenis_alat set Nama_Alat = ?, Deskripsi = ? where ID_Alat = ?";
                   
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, nama_alat);
            perintah.setString(2, deskripsi);
            perintah.setString(3, id_alat);
            
            perintah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Alat berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
      
       public void hapusAlat01(String id_alat){
        try {
            String sql = "delete from jenis_alat where ID_Alat = '"+id_alat+"'";
                   
            Statement perintah = koneksi.createStatement();
            perintah.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Alat berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
       
       public void hapusAlat02(String id_alat){
        try {
            String sql = "delete from jenis_alat where ID_Alat = ?";
                   
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, id_alat);
            perintah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Alat berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
       
      public void tampilDataAlat (JTable komponenTable, String SQL){
          try {
              Statement perintah = koneksi.createStatement();
              ResultSet data = perintah.executeQuery(SQL);
              ResultSetMetaData meta = data.getMetaData();
              int jumKolom = meta.getColumnCount();
              DefaultTableModel modelTable = new DefaultTableModel();
              modelTable.addColumn("ID Alat");
              modelTable.addColumn("Nama Alat");
              modelTable.addColumn("Deskripsi");
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
              
          }
      }
}