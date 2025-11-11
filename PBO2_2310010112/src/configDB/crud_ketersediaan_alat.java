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
import java.util.ArrayList;
import java.sql.Date; // Pastikan menggunakan java.sql.Date

/**
 *
 * @author User
 */
public class crud_ketersediaan_alat {

    private String namaDB = "pertambangan_pbo2";
    private String url = "jdbc:mysql://localhost:3306/" + namaDB;
    private String username = "root";
    private String password = "";
    private Connection koneksi;

    // Variabel untuk menampung data dari Frame (digunakan oleh metode baru)
    public String VAR_STATUS = null;
    public int VAR_ID_ALAT = 0; // Diubah ke int agar sesuai dengan setInt()
    public Date VAR_TANGGAL_PENEMPATAN = null; // Tipe data java.sql.Date
    public Date VAR_TANGGAL_SELESAI = null; // Tipe data java.sql.Date
    public boolean validasi = false;

    public crud_ketersediaan_alat() {
        try {
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            koneksi = DriverManager.getConnection(url, username, password);
            System.out.print("Berhasil dikoneksikan (Ketersediaan Alat)");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + error.getMessage());

        }
    }

    // --- METODE BARU UNTUK KOMPATIBILITAS FRAME (JDateChooser & JComboBox) ---
    
    /**
     * Menyimpan data baru ke tabel ketersediaan_alat.
     * Menggunakan member variables (VAR_ID_ALAT, VAR_STATUS, VAR_TANGGAL_PENEMPATAN, VAR_TANGGAL_SELESAI)
     * ID_Status diasumsikan AUTO_INCREMENT (sesuai SQL).
     */
    public void tambahData() {
        try {
            // ID_Status tidak dimasukkan karena AUTO_INCREMENT
            String sql = "INSERT INTO ketersediaan_alat (ID_Alat, Status, Tanggal_Penempatan, Tanggal_Selesai) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement perintah = koneksi.prepareStatement(sql);

            perintah.setInt(1, this.VAR_ID_ALAT); // Gunakan setInt untuk ID_Alat
            perintah.setString(2, this.VAR_STATUS);
            perintah.setDate(3, this.VAR_TANGGAL_PENEMPATAN); // Gunakan setDate
            perintah.setDate(4, this.VAR_TANGGAL_SELESAI);   // Gunakan setDate

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Ketersediaan Alat berhasil disimpan");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    /**
     * Mengubah data di tabel ketersediaan_alat berdasarkan ID_Status.
     * Menggunakan member variables (VAR_ID_ALAT, VAR_STATUS, VAR_TANGGAL_PENEMPATAN, VAR_TANGGAL_SELESAI)
     * @param id_status ID data (String) yang akan diubah.
     */
    public void updateData(String id_status) {
        try {
            String sql = "UPDATE ketersediaan_alat SET ID_Alat = ?, Status = ?, Tanggal_Penempatan = ?, Tanggal_Selesai = ? "
                    + "WHERE ID_Status = ?";

            PreparedStatement perintah = koneksi.prepareStatement(sql);

            perintah.setInt(1, this.VAR_ID_ALAT); // Gunakan setInt
            perintah.setString(2, this.VAR_STATUS);
            perintah.setDate(3, this.VAR_TANGGAL_PENEMPATAN); // Gunakan setDate
            perintah.setDate(4, this.VAR_TANGGAL_SELESAI);   // Gunakan setDate

            // Konversi id_status (String) ke int untuk PreparedStatement
            try {
                perintah.setInt(5, Integer.parseInt(id_status));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID Status harus berupa angka.");
                return; // Hentikan eksekusi jika ID tidak valid
            }

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Ketersediaan Alat berhasil diubah");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
    }

    /**
     * Menghapus data dari tabel ketersediaan_alat berdasarkan ID_Status.
     * Versi ini menggunakan PreparedStatement.
     * @param id_status ID data (String) yang akan dihapus.
     */
    public void hapusData(String id_status) {
        try {
            String sql = "DELETE FROM ketersediaan_alat WHERE ID_Status = ?";

            PreparedStatement perintah = koneksi.prepareStatement(sql);

            // Konversi id_status (String) ke int
            try {
                perintah.setInt(1, Integer.parseInt(id_status));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID Status harus berupa angka.");
                return; // Hentikan eksekusi jika ID tidak valid
            }

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Ketersediaan Alat berhasil dihapus");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
        }
    }

    // --- METODE TAMPIL DAN CARI DATA (SUDAH DIUPDATE) ---
    
    /**
     * DIPERBARUI: Metode ini sekarang menggunakan JOIN SQL untuk efisiensi.
     */
    public void tampilDataKetersediaan(JTable komponenTable) {
        try {
            // SQL query menggunakan JOIN untuk mendapatkan Nama_Alat secara efisien
            String sql = "SELECT a.ID_Status, a.ID_Alat, b.Nama_Alat, a.Status, a.Tanggal_Penempatan, a.Tanggal_Selesai "
                    + "FROM ketersediaan_alat a "
                    + "LEFT JOIN jenis_alat b ON a.ID_Alat = b.ID_Alat";

            Statement perintah = koneksi.createStatement();
            ResultSet data = perintah.executeQuery(sql);

            // Definisikan nama kolom
            String[] kolom = {"ID Status", "ID Alat", "Nama Alat", "Status", "Tanggal Penempatan", "Tanggal Selesai"};
            DefaultTableModel modelTable = new DefaultTableModel(null, kolom); // Set kolom saat inisialisasi

            modelTable.getDataVector().clear();
            modelTable.fireTableDataChanged();

            while (data.next()) {
                Object[] row = new Object[6]; // 6 kolom sesuai query
                row[0] = data.getObject("ID_Status");
                row[1] = data.getObject("ID_Alat");
                row[2] = data.getObject("Nama_Alat"); // Diambil dari JOIN
                row[3] = data.getObject("Status");
                row[4] = data.getObject("Tanggal_Penempatan");
                row[5] = data.getObject("Tanggal_Selesai");
                modelTable.addRow(row);
            }
            komponenTable.setModel(modelTable);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    /**
     * Metode baru untuk pencarian menggunakan JOIN.
     */
    public void cariData(JTable komponenTable, String kriteria) {
        try {
            // Query JOIN yang sama dengan tampilData, tapi dengan klausa WHERE
            String sql = "SELECT a.ID_Status, a.ID_Alat, b.Nama_Alat, a.Status, a.Tanggal_Penempatan, a.Tanggal_Selesai "
                    + "FROM ketersediaan_alat a "
                    + "LEFT JOIN jenis_alat b ON a.ID_Alat = b.ID_Alat "
                    + "WHERE b.Nama_Alat LIKE ? OR a.Status LIKE ?";

            PreparedStatement perintah = koneksi.prepareStatement(sql);
            String kriteriaLike = "%" + kriteria + "%";
            perintah.setString(1, kriteriaLike); // Cari berdasarkan Nama_Alat
            perintah.setString(2, kriteriaLike); // Cari berdasarkan Status

            ResultSet data = perintah.executeQuery();

            // Definisikan nama kolom (sama seperti di tampilData)
            String[] kolom = {"ID Status", "ID Alat", "Nama Alat", "Status", "Tanggal Penempatan", "Tanggal Selesai"};
            DefaultTableModel modelTable = new DefaultTableModel(null, kolom);
            modelTable.getDataVector().clear();
            modelTable.fireTableDataChanged();

            while (data.next()) {
                Object[] row = new Object[6];
                row[0] = data.getObject("ID_Status");
                row[1] = data.getObject("ID_Alat");
                row[2] = data.getObject("Nama_Alat");
                row[3] = data.getObject("Status");
                row[4] = data.getObject("Tanggal_Penempatan");
                row[5] = data.getObject("Tanggal_Selesai");
                modelTable.addRow(row);
            }
            komponenTable.setModel(modelTable);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mencari data: " + e.getMessage());
        }
    }

    // --- METODE UTILITY (UNTUK JCOMBOBOX) ---
    
    public ArrayList<String> getIDAlat() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT ID_Alat FROM jenis_alat ORDER BY ID_Alat";
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("ID_Alat"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list;
    }

    public String getNamaAlatById(String id_alat) {
        String namaAlat = "";
        try {
            String sql = "SELECT Nama_Alat FROM jenis_alat WHERE ID_Alat = ?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, id_alat); 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                namaAlat = rs.getString("Nama_Alat");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return namaAlat;
    }

    // --- METODE LAMA (DIPERTAHANKAN UNTUK REFERENSI) ---
    
    public void simpanKetersediaan02(String id_status, String id_alat, String status, String tanggal_penempatan, String tanggal_selesai) {
        try {
            String sql = "insert into ketersediaan_alat(ID_Status, ID_Alat, Status, Tanggal_Penempatan, Tanggal_Selesai) value(?, ?, ?, ?, ?)";
            String cekPrimary = "select * from ketersediaan_alat where ID_Status= ?";

            PreparedStatement check = koneksi.prepareStatement(cekPrimary);
            check.setString(1, id_status);
            ResultSet data = check.executeQuery();
            if (data.next()) {
                JOptionPane.showMessageDialog(null, "ID Status sudah terdaftar");
                // ... (logika validasi lama)
            } else {
                // ... (logika validasi lama)
                PreparedStatement perintah = koneksi.prepareStatement(sql);
                perintah.setString(1, id_status);
                perintah.setString(2, id_alat);
                perintah.setString(3, status);
                perintah.setString(4, tanggal_penempatan); // Ini kurang ideal untuk tanggal
                perintah.setString(5, tanggal_selesai); // Ini kurang ideal untuk tanggal
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Ketersediaan Alat berhasil disimpan");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
    }

    public void ubahKetersediaan02(String id_status, String id_alat, String status, String tanggal_penempatan, String tanggal_selesai) {
        try {
            String sql = "update ketersediaan_alat set ID_Alat = ?, Status = ?, Tanggal_Penempatan = ?, Tanggal_Selesai = ? where ID_Status = ?";

            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, id_alat);
            perintah.setString(2, status);
            perintah.setString(3, tanggal_penempatan); // Kurang ideal
            perintah.setString(4, tanggal_selesai); // Kurang ideal
            perintah.setString(5, id_status);

            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Ketersediaan Alat berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
    }

    public void hapusKetersediaan02(String id_status) {
        try {
            String sql = "delete from ketersediaan_alat where ID_Status = ?";

            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, id_status);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Ketersediaan Alat berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
    }

}