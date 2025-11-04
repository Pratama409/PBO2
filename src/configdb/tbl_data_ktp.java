package configdb;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;                   
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;

public class tbl_data_ktp {
    
    private final String namadb   = "pbo2_2310010569";
    private final String url      = "jdbc:mysql://localhost:3306/" + namadb + "?serverTimezone=UTC";
    private final String username = "root";
    private final String password = "";
    private Connection koneksi;

    public String VAR_nama, VAR_rt, VAR_rw, VAR_no_telp, VAR_desa, VAR_tgl_input, VAR_id_user;
    public boolean validasi = false;

    public tbl_data_ktp() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, username, password);
            System.out.println("Berhasil konek ke database");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void SimpanKTP(String nik, String nama, int rt, int rw,
                          String noTelp, String desa, String tglInput, String idUser) {
        try {
            String cek = "SELECT * FROM tbl_data_ktp WHERE nik = ?";
            PreparedStatement psCek = koneksi.prepareStatement(cek);
            psCek.setString(1, nik);
            ResultSet rs = psCek.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "NIK sudah terdaftar!");
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
                String sql = "INSERT INTO tbl_data_ktp(nik,nama,rt,rw,no_telp,desa,tgl_input,id_user) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps = koneksi.prepareStatement(sql);
                ps.setString(1, nik);
                ps.setString(2, nama);
                ps.setInt(3, rt);
                ps.setInt(4, rw);
                ps.setString(5, noTelp);
                ps.setString(6, desa);
                ps.setString(7, tglInput);
                ps.setString(8, idUser);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data KTP berhasil disimpan!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void UbahKTP(String nik, String nama, int rt, int rw,
                        String noTelp, String desa, String tglInput, String idUser) {
        try {
            String sql = "UPDATE tbl_data_ktp SET nama=?, rt=?, rw=?, no_telp=?, desa=?, tgl_input=?, id_user=? WHERE nik=?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, rt);
            ps.setInt(3, rw);
            ps.setString(4, noTelp);
            ps.setString(5, desa);
            ps.setString(6, tglInput);
            ps.setString(7, idUser);
            ps.setString(8, nik);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data KTP berhasil diubah!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void HapusKTP(String nik) {
        try {
            String sql = "DELETE FROM tbl_data_ktp WHERE nik = ?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, nik);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data KTP berhasil dihapus!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResultSet CariKTP(String keyword) {
        try {
            String sql = "SELECT * FROM tbl_data_ktp WHERE nik LIKE ? OR nama LIKE ? OR desa LIKE ? ORDER BY tgl_input DESC";
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

                for (int i = 1; i <= jumKolom; i++) {
                    modelTable.addColumn(meta.getColumnLabel(i));
                }

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
            " nik       AS 'NIK', " +
            " nama      AS 'Nama', " +
            " rt        AS 'RT', " +
            " rw        AS 'RW', " +
            " no_telp   AS 'No Telp', " +
            " desa      AS 'Desa', " +
            " tgl_input AS 'Tanggal Input', " +
            " id_user   AS 'ID User' " +
            "FROM tbl_data_ktp " +
            "ORDER BY tgl_input DESC";
        tampilDataAnggota(komponenTable, SQL);
    }

}
