package configdb;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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

public class tbl_laporan_kk {
    private final String namadb   = "pbo2_2310010569";
    private final String url      = "jdbc:mysql://localhost:3306/" + namadb + "?serverTimezone=UTC";
    private final String username = "root";
    private final String password = "";
    private Connection koneksi;

    public String VAR_nama, VAR_rt, VAR_rw, VAR_no_telp, VAR_desa, VAR_tgl_input, VAR_id_user;
    public boolean validasi = false;

    public tbl_laporan_kk() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, username, password);
            System.out.println("Berhasil konek ke database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void SimpanLaporanKK(String noKk, String nama, int rt, int rw,
                                String noTelp, String desa, String tglInput, String idUser) {
        try {
            String cek = "SELECT * FROM tbl_laporan_kk WHERE no_kk=? AND tgl_input=?";
            PreparedStatement psCek = koneksi.prepareStatement(cek);
            psCek.setString(1, noKk);
            psCek.setString(2, tglInput);
            ResultSet rs = psCek.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Laporan KK dengan NoKK & TglInput ini sudah ada!");
                validasi = true;
                VAR_nama = rs.getString("nama");
                VAR_rt = String.valueOf(rs.getInt("rt"));
                VAR_rw = String.valueOf(rs.getInt("rw"));
                VAR_no_telp = rs.getString("no_telp");
                VAR_desa = rs.getString("desa");
                VAR_tgl_input = rs.getString("tgl_input");
                VAR_id_user = rs.getString("id_user");
            } else {
                validasi = false;
                String sql = "INSERT INTO tbl_laporan_kk(no_kk,nama,rt,rw,no_telp,desa,tgl_input,id_user) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps = koneksi.prepareStatement(sql);
                ps.setString(1, noKk);
                ps.setString(2, nama);
                ps.setInt(3, rt);
                ps.setInt(4, rw);
                ps.setString(5, noTelp);
                ps.setString(6, desa);
                ps.setString(7, tglInput);
                ps.setString(8, idUser);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Laporan KK berhasil disimpan!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void UbahLaporanKK(String noKk, String tglInput,
                              String nama, int rt, int rw, String noTelp, String desa, String idUser) {
        try {
            String sql = "UPDATE tbl_laporan_kk SET nama=?, rt=?, rw=?, no_telp=?, desa=?, id_user=? WHERE no_kk=? AND tgl_input=?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, rt);
            ps.setInt(3, rw);
            ps.setString(4, noTelp);
            ps.setString(5, desa);
            ps.setString(6, idUser);
            ps.setString(7, noKk);
            ps.setString(8, tglInput);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Laporan KK berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void HapusLaporanKK(String noKk, String tglInput) {
        try {
            String sql = "DELETE FROM tbl_laporan_kk WHERE no_kk=? AND tgl_input=?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, noKk);
            ps.setString(2, tglInput);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Laporan KK berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResultSet CariLaporanKK(String keyword) {
        try {
            String sql = "SELECT * FROM tbl_laporan_kk WHERE no_kk LIKE ? OR nama LIKE ? OR desa LIKE ? ORDER BY tgl_input DESC";
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

    public void tampilDataAnggota(JTable komponenTable, String SQL) {
        try (Statement perintah = koneksi.createStatement();
             ResultSet data = perintah.executeQuery(SQL)) {

            ResultSetMetaData meta = data.getMetaData();
            int jumKolom = meta.getColumnCount();

            DefaultTableModel modelTable = new DefaultTableModel();

            // Header kolom mengikuti label/alias di SELECT
            for (int i = 1; i <= jumKolom; i++) {
                modelTable.addColumn(meta.getColumnLabel(i));
            }

            // Isi baris
            modelTable.setRowCount(0);
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
        String SQL =
            "SELECT " +
            " no_kk     AS 'No KK'," +
            " nama      AS 'Nama'," +
            " rt        AS 'RT'," +
            " rw        AS 'RW'," +
            " no_telp   AS 'No Telp'," +
            " desa      AS 'Desa'," +
            " tgl_input AS 'Tanggal Input'," +
            " id_user   AS 'ID User' " +
            "FROM tbl_laporan_kk " +
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
