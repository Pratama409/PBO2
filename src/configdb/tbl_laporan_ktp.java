package configdb;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class tbl_laporan_ktp {

    private final String namadb   = "pbo2_2310010569";
    private final String url      = "jdbc:mysql://localhost:3306/" + namadb + "?serverTimezone=UTC";
    private final String username = "root";
    private final String password = "";
    private Connection koneksi;

    public String VAR_nama, VAR_rt, VAR_rw, VAR_no_telp, VAR_desa, VAR_tgl_input, VAR_id_user;
    public boolean validasi = false;

    public tbl_laporan_ktp() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, username, password);
            System.out.println("Berhasil konek ke database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void SimpanLaporanKTP(String nik, String nama, int rt, int rw,
                                 String noTelp, String desa, String tglInput, String idUser) {
        try {
            String cek = "SELECT 1 FROM tbl_laporan_ktp WHERE nik=? AND tgl_input=?";
            try (PreparedStatement psCek = koneksi.prepareStatement(cek)) {
                psCek.setString(1, nik);
                psCek.setString(2, tglInput);
                try (ResultSet rs = psCek.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Laporan KTP untuk NIK & TglInput ini sudah ada!");
                        validasi = true;
                        return;
                    }
                }
            }

            validasi = false;
            String sql = "INSERT INTO tbl_laporan_ktp(nik,nama,rt,rw,no_telp,desa,tgl_input,id_user) "
                       + "VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, nik);
                ps.setString(2, nama);
                ps.setInt(3, rt);
                ps.setInt(4, rw);
                ps.setString(5, noTelp);
                ps.setString(6, desa);
                ps.setString(7, tglInput);
                ps.setString(8, idUser);
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Laporan KTP berhasil disimpan!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void UbahLaporanKTP(String nik, String tglInput,
                               String nama, int rt, int rw, String noTelp, String desa, String idUser) {
        try {
            String sql = "UPDATE tbl_laporan_ktp SET nama=?, rt=?, rw=?, no_telp=?, desa=?, id_user=? "
                       + "WHERE nik=? AND tgl_input=?";
            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, nama);
                ps.setInt(2, rt);
                ps.setInt(3, rw);
                ps.setString(4, noTelp);
                ps.setString(5, desa);
                ps.setString(6, idUser);
                ps.setString(7, nik);
                ps.setString(8, tglInput);
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Laporan KTP berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void UbahKeyLaporanKTP(String nikLama, String tglLama, String nikBaru, String tglBaru) {
        try {
            String cekBaru = "SELECT 1 FROM tbl_laporan_ktp WHERE nik=? AND tgl_input=?";
            try (PreparedStatement psCek = koneksi.prepareStatement(cekBaru)) {
                psCek.setString(1, nikBaru);
                psCek.setString(2, tglBaru);
                try (ResultSet rs = psCek.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Gagal: kombinasi NIK & TglInput baru sudah ada.");
                        return;
                    }
                }
            }

            String sql = "UPDATE tbl_laporan_ktp SET nik=?, tgl_input=? WHERE nik=? AND tgl_input=?";
            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, nikBaru);
                ps.setString(2, tglBaru);
                ps.setString(3, nikLama);
                ps.setString(4, tglLama);
                int affected = ps.executeUpdate();
                JOptionPane.showMessageDialog(null,
                        affected > 0 ? "Key laporan KTP berhasil diubah!"
                                     : "Data tidak ditemukan untuk diubah key-nya.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void HapusLaporanKTP(String nik, String tglInput) {
        try {
            String sql = "DELETE FROM tbl_laporan_ktp WHERE nik=? AND tgl_input=?";
            try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                ps.setString(1, nik);
                ps.setString(2, tglInput);
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Laporan KTP berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResultSet CariLaporanKTP(String keyword) {
        try {
            String sql = "SELECT * FROM tbl_laporan_ktp "
                       + "WHERE nik LIKE ? OR nama LIKE ? OR desa LIKE ? "
                       + "ORDER BY tgl_input DESC";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            return ps.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            return null;
        }
    }

    public ResultSet GetByKey(String nik, String tglInput) {
        try {
            String sql = "SELECT * FROM tbl_laporan_ktp WHERE nik=? AND tgl_input=?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, nik);
            ps.setString(2, tglInput);
            return ps.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            return null;
        }
    }

    public void tampilDataAnggota(JTable komponenTable, String SQL) {
        try (Statement perintah = koneksi.createStatement();
             ResultSet data = perintah.executeQuery(SQL)) {

            ResultSetMetaData meta = data.getMetaData();
            int jumKolom = meta.getColumnCount();

            DefaultTableModel modelTable = new DefaultTableModel();

            for (int i = 1; i <= jumKolom; i++) {
                modelTable.addColumn(meta.getColumnLabel(i));
            }

            while (data.next()) {
                Object[] row = new Object[jumKolom];
                for (int i = 1; i <= jumKolom; i++) {
                    row[i - 1] = data.getObject(i);
                }
                modelTable.addRow(row);
            }

            komponenTable.setModel(modelTable);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void muatSemuaKeTabel(JTable komponenTable) {
        String SQL = "SELECT " +
                     " nik       AS 'NIK'," +
                     " nama      AS 'Nama'," +
                     " rt        AS 'RT'," +
                     " rw        AS 'RW'," +
                     " no_telp   AS 'No Telp'," +
                     " desa      AS 'Desa'," +
                     " tgl_input AS 'Tanggal Input'," +
                     " id_user   AS 'ID User' " +
                     "FROM tbl_laporan_ktp " +
                     "ORDER BY tgl_input DESC";
        tampilDataAnggota(komponenTable, SQL);
    }
    
    public void cetakLaporan(String fileLaporan, String SQL){
        try {
            File file = new File(fileLaporan);
            JasperDesign jasDes = JRXmlLoader.load(file);
            JRDesignQuery query = new JRDesignQuery();
            query.setText(SQL);
            jasDes.setQuery(query);
            JasperReport jr = JasperCompileManager.compileReport(jasDes);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, this.koneksi);
            JasperViewer.viewReport(jp);
            
        } catch (Exception e) {
        }
    }
}
