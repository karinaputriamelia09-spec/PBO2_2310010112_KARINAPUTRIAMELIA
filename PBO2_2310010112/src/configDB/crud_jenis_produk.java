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
public class crud_jenis_produk {
    
    private String namaDB = "pertambangan_pbo2";
    private String url = "jdbc:mysql://localhost:3306/" + namaDB;
    private String username = "root";
    private String password = "";
    private Connection koneksi;
    public String VAR_JENIS_PRODUK = null;
    public String VAR_UKURAN = null;
    public double VAR_HARGA = 0;
    public boolean validasi = false;
    
    public crud_jenis_produk(){
        try {
           Driver mysqldriver = new com.mysql.jdbc.Driver();
           DriverManager.registerDriver(mysqldriver);
           koneksi = DriverManager.getConnection(url,username,password);
           System.out.print("Berhasil dikoneksikan");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
                
        }
    }
    
    public void simpanProduk01(String id_produk, String jenis_produk, String ukuran, double harga){
        try {
            String sql = "insert into jenis_produk(ID_Produk, Jenis_Produk, Ukuran, Harga) "
                    + "values('"+id_produk+"', '"+jenis_produk+"', '"+ukuran+"', "+harga+")";
            
            String cekPrimary = "select * from jenis_produk where ID_Produk= '"+id_produk+"'";
            
            Statement check = koneksi.createStatement();
            ResultSet data = check.executeQuery(cekPrimary);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Produk sudah terdaftar");
                this.VAR_JENIS_PRODUK = data.getString("Jenis_Produk");
                this.VAR_UKURAN = data.getString("Ukuran");
                this.VAR_HARGA = data.getDouble("Harga");
                this.validasi = true;
            } else {
                this.validasi = false;
                this.VAR_JENIS_PRODUK = null;
                this.VAR_UKURAN = null;
                this.VAR_HARGA = 0;
                Statement perintah = koneksi.createStatement();
                perintah.execute(sql);
                JOptionPane.showMessageDialog(null, "Data Produk berhasil disimpan");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
    
     public void simpanProduk02(String id_produk, String jenis_produk, String ukuran, double harga){
        try {
            String sql = "insert into jenis_produk(ID_Produk, Jenis_Produk, Ukuran, Harga) value(?, ?, ?, ?)";
            String cekPrimary = "select * from jenis_produk where ID_Produk= ?";
            
            PreparedStatement check = koneksi.prepareStatement(cekPrimary);
            check.setString(1, id_produk);
            ResultSet data = check.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Produk sudah terdaftar");
                this.VAR_JENIS_PRODUK = data.getString("Jenis_Produk");
                this.VAR_UKURAN = data.getString("Ukuran");
                this.VAR_HARGA = data.getDouble("Harga");
                this.validasi = true;
            } else {
                this.validasi = false;
                this.VAR_JENIS_PRODUK = null;
                this.VAR_UKURAN = null;
                this.VAR_HARGA = 0;
                PreparedStatement perintah = koneksi.prepareStatement(sql);
                perintah.setString(1, id_produk);
                perintah.setString(2, jenis_produk);
                perintah.setString(3, ukuran);
                perintah.setDouble(4, harga);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Produk berhasil disimpan");
            }
            
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
     
     public void ubahProduk01(String id_produk, String jenis_produk, String ukuran, double harga){
        try {
            String sql = "update jenis_produk set Jenis_Produk = '"+jenis_produk+"', Ukuran = '"+ukuran+"'"
                    + ", Harga = "+harga+" where ID_Produk = '"+id_produk+"'";
                   
            Statement perintah = koneksi.createStatement();
            perintah.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Produk berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
     
      public void ubahProduk02(String id_produk, String jenis_produk, String ukuran, double harga){
        try {
            String sql = "update jenis_produk set Jenis_Produk = ?, Ukuran = ?, Harga = ? where ID_Produk = ?";
                   
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, jenis_produk);
            perintah.setString(2, ukuran);
            perintah.setDouble(3, harga);
            perintah.setString(4, id_produk);
            
            perintah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Produk berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
      
       public void hapusProduk01(String id_produk){
        try {
            String sql = "delete from jenis_produk where ID_Produk = '"+id_produk+"'";
                   
            Statement perintah = koneksi.createStatement();
            perintah.execute(sql);
             JOptionPane.showMessageDialog(null, "Data Produk berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
       
       public void hapusProduk02(String id_produk){
        try {
            String sql = "delete from jenis_produk where ID_Produk = ?";
                   
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, id_produk);
            perintah.executeUpdate();
             JOptionPane.showMessageDialog(null, "Data Produk berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
       
      public void tampilDataProduk (JTable komponenTable, String SQL){
          try {
              Statement perintah = koneksi.createStatement();
              ResultSet data = perintah.executeQuery(SQL);
              ResultSetMetaData meta = data.getMetaData();
              int jumKolom = meta.getColumnCount();
              DefaultTableModel modelTable = new DefaultTableModel();
              modelTable.addColumn("ID Produk");
              modelTable.addColumn("Jenis Produk");
              modelTable.addColumn("Ukuran");
              modelTable.addColumn("Harga");
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