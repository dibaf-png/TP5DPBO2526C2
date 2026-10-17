import javax.swing.*;                     // Mengimpor pustaka Java Swing untuk komponen GUI dasar
import javax.swing.table.DefaultTableModel; // Mengimpor pustaka untuk mengatur model data pada tabel
import java.awt.*;                        // Mengimpor pustaka AWT untuk pengaturan warna dan tata letak
import java.awt.event.ActionEvent;        // Mengimpor pustaka untuk menangani aksi event komponen
import java.awt.event.ActionListener;     // Mengimpor pustaka antarmuka pendengar aksi tombol
import java.awt.event.MouseAdapter;       // Mengimpor pustaka adaptor mouse untuk interaksi tabel
import java.awt.event.MouseEvent;         // Mengimpor pustaka event klik mouse
import java.sql.ResultSet;                // Mengimpor pustaka untuk menampung hasil query SELECT dari database
import java.sql.SQLException;             // Mengimpor pustaka untuk menangani error SQL

public class PeopleMenu extends JFrame {  // Mendeklarasikan kelas PeopleMenu yang mewarisi JFrame
    public JPanel mainPanel;              // Komponen panel utama aplikasi
    private JTextField idField;           // Kotak teks untuk input ID
    private JTextField namaField;         // Kotak teks untuk input Nama
    private JTextField tahunField;        // Kotak teks untuk input Tahun Lahir
    private JComboBox kategoriComboBox;   // ComboBox untuk pilihan Kategori
    private JComboBox jenisKelaminComboBox; // ComboBox untuk pilihan Jenis Kelamin
    private JButton addButton;            // Tombol untuk Tambah data
    private JButton deleteButton;         // Tombol untuk Hapus data
    private JButton cancelButton;         // Tombol untuk Batalkan/Bersihkan form
    private JTable peopleTable;           // Tabel untuk menampilkan data dari database
    private JButton updateButton;         // Tombol khusus untuk Ubah data
    private JLabel titleLabel;            // Label teks judul form

    private Database database;            // Objek penghubung ke kelas Database kita
    private int selectedIndex = -1;       // Menyimpan indeks baris tabel yang dipilih, nilai -1 berarti belum ada baris dipilih

    public PeopleMenu() {                 // Konstruktor kelas PeopleMenu
        database = new Database();        // Menginisialisasi objek Database untuk koneksi SQL

        peopleTable.setModel(setTable()); // Mengatur model awal tabel dengan mengambil data langsung dari database

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f)); // Mengubah gaya huruf judul form jadi tebal ukuran 20f

        String[] kategoriData = { "???", "Siswa", "Mahasiswa", "Dosen", "Staff", "Umum" }; // Array pilihan kategori
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData)); // Memasukkan pilihan ke ComboBox kategori

        String[] jenisKelaminData = { "???", "Laki-laki", "Perempuan" }; // Array pilihan jenis kelamin
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData)); // Memasukkan pilihan ke ComboBox jenis kelamin

        // Tombol Add (menambah data baru)
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData(); // Menjalankan method insertData
            }
        });

        // Tombol Update (mengubah data)
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) { // Pengecekan apakah ada baris tabel yang sedang dipilih
                    updateData(); // Menjalankan method updateData jika baris valid dipilih
                }
                else { // Jika belum ada baris yang dipilih di tabel
                    JOptionPane.showMessageDialog(null, "Pilih data yang akan di-update terlebih dahulu pada tabel!", "Peringatan", JOptionPane.WARNING_MESSAGE); // Munculkan peringatan
                }
            }
        });

        // Tombol Delete (menghapus data)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) { // Pengecekan apakah ada baris tabel yang dipilih
                    int konfirmasi = JOptionPane.showConfirmDialog( // Menampilkan kotak dialog konfirmasi penghapusan
                            null,
                            "Apakah Anda yakin ingin menghapus data ini?",
                            "Konfirmasi Hapus",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (konfirmasi == JOptionPane.YES_OPTION) { // Jika pengguna memilih 'Yes'
                        deleteData(); // Memanggil method deleteData
                    }
                }
                else { // Jika belum ada baris yang dipilih
                    JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus terlebih dahulu pada tabel!", "Peringatan", JOptionPane.WARNING_MESSAGE); // Munculkan peringatan
                }
            }
        });

        // Tombol Cancel (membatalkan perubahan)
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm(); // Memanggil method untuk membersihkan formulir
            }
        });

        // Event klik baris tabel (Membaca data langsung dari tabel tanpa ArrayList sesuai aturan TP5)
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = peopleTable.getSelectedRow(); // Mengambil nomor indeks baris tabel yang diklik

                // Mengambil nilai string langsung dari model tabel pada baris yang diklik
                String curId = peopleTable.getModel().getValueAt(selectedIndex, 1).toString(); // Ambil kolom ID
                String curNama = peopleTable.getModel().getValueAt(selectedIndex, 2).toString(); // Ambil kolom Nama
                String curTahun = peopleTable.getModel().getValueAt(selectedIndex, 3).toString(); // Ambil kolom Tahun Lahir
                String curKategori = peopleTable.getModel().getValueAt(selectedIndex, 4).toString(); // Ambil kolom Kategori
                String curKelamin = peopleTable.getModel().getValueAt(selectedIndex, 5).toString(); // Ambil kolom Jenis Kelamin

                // Menampilkan data yang dipilih ke form input
                idField.setText(curId);           // Masukkan ID ke text field ID
                namaField.setText(curNama);       // Masukkan Nama ke text field Nama
                tahunField.setText(curTahun);     // Masukkan Tahun ke text field Tahun
                kategoriComboBox.setSelectedItem(curKategori); // Pilih item ComboBox kategori
                jenisKelaminComboBox.setSelectedItem(curKelamin); // Pilih item ComboBox jenis kelamin

                idField.setEnabled(false);       // Menonaktifkan field ID agar tidak bisa diubah saat mode edit
            }
        });
    }

    // Method untuk mengambil data dari MySQL dan menampilkannya ke JTable (Read)
    public final DefaultTableModel setTable() {
        Object[] cols = { "No", "ID", "Nama", "Tahun Lahir", "Kategori", "Jenis Kelamin" }; // Mendefinisikan judul kolom tabel
        DefaultTableModel tmp = new DefaultTableModel(null, cols); // Membuat model tabel kosong dengan kolom tersebut

        try {
            // Mengeksekusi query SELECT untuk mengambil semua data dari tabel database
            ResultSet resultSet = database.selectQuery("SELECT * FROM people");
            int i = 1; // Variabel penomoran baris tabel
            while (resultSet.next()) { // Melakukan perulangan untuk setiap baris hasil data database
                Object[] row = {
                        i++,                                    // Nomor urut bertambah otomatis
                        resultSet.getString("id"),              // Mengambil data kolom id
                        resultSet.getString("nama"),            // Mengambil data kolom nama
                        resultSet.getString("tahun_lahir"),     // Mengambil data kolom tahun_lahir
                        resultSet.getString("kategori"),        // Mengambil data kolom kategori
                        resultSet.getString("jenis_kelamin")    // Mengambil data kolom jenis_kelamin
                };
                tmp.addRow(row); // Menambahkan baris data ke model tabel
            }
        }
        catch (SQLException e) { // Menangkap error jika query SQL gagal
            throw new RuntimeException(e); // Lempar sebagai runtime exception
        }
        return tmp; // Mengembalikan model tabel yang sudah terisi data
    }

    // Method untuk menambah data baru ke database dengan validasi lengkap (Create)
    public void insertData() {
        try {
            String id = idField.getText().trim();                 // Mengambil teks input ID dan menghapus spasi berlebih
            String nama = namaField.getText().trim();             // Mengambil teks input Nama
            String tahunLahir = tahunField.getText().trim();      // Mengambil teks input Tahun Lahir
            String kategori = kategoriComboBox.getSelectedItem().toString();       // Mengambil pilihan Kategori
            String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString(); // Mengambil pilihan Jenis Kelamin

            // Prompt error jika ada kolom input yang kosong atau belum dipilih pada ComboBox
            if (id.isEmpty() || nama.isEmpty() || tahunLahir.isEmpty() || kategori.equals("???") || jenisKelamin.equals("???")) {
                JOptionPane.showMessageDialog(null, "Semua kolom input harus diisi dengan benar!", "Error Validasi", JOptionPane.ERROR_MESSAGE);
                return; // Menghentikan eksekusi method jika validasi gagal
            }

            // Prompt error jika sudah ada ID yang sama persis saat insert (Cek Primary Key ganda)
            ResultSet checkId = database.selectQuery("SELECT * FROM people WHERE id = '" + id + "'");
            if (checkId.next()) { // Jika data dengan ID tersebut sudah ditemukan di database
                JOptionPane.showMessageDialog(null, "ID '" + id + "' sudah terdaftar! Gunakan ID lain.", "Error ID Ganda", JOptionPane.ERROR_MESSAGE);
                return; // Menghentikan eksekusi method
            }

            // Menyusun string query INSERT ke database MySQL
            String sqlQuery = "INSERT INTO people VALUES ('" + id + "', '" + nama + "', '" + tahunLahir + "', '" + kategori + "', '" + jenisKelamin + "')";
            database.insertUpdateDeleteQuery(sqlQuery); // Menjalankan query eksekusi ke database

            peopleTable.setModel(setTable()); // Memperbarui (refresh) tampilan tabel agar data baru langsung muncul
            clearForm();                      // Mengosongkan form input kembali
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!"); // Pesan sukses
        }
        catch (Exception ex) { // Menangkap error tak terduga
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk mengubah data yang sudah ada di database (Update)
    public void updateData() {
        try {
            String id = idField.getText().trim();                 // Mengambil nilai ID (yang sedang dikunci/tidak berubah)
            String nama = namaField.getText().trim();             // Mengambil inputan Nama terbaru
            String tahunLahir = tahunField.getText().trim();      // Mengambil inputan Tahun Lahir terbaru
            String kategori = kategoriComboBox.getSelectedItem().toString();       // Mengambil pilihan Kategori terbaru
            String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString(); // Mengambil pilihan Jenis Kelamin terbaru

            // Validasi prompt error jika kolom penting kosong saat update
            if (nama.isEmpty() || tahunLahir.isEmpty() || kategori.equals("???") || jenisKelamin.equals("???")) {
                JOptionPane.showMessageDialog(null, "Kolom tidak boleh kosong!", "Error Validasi", JOptionPane.ERROR_MESSAGE);
                return; // Menghentikan eksekusi method
            }

            // Menyusun string query UPDATE ke database MySQL berdasarkan ID yang bersangkutan
            String sqlQuery = "UPDATE people SET nama = '" + nama + "', tahun_lahir = '" + tahunLahir + "', kategori = '" + kategori + "', jenis_kelamin = '" + jenisKelamin + "' WHERE id = '" + id + "'";
            database.insertUpdateDeleteQuery(sqlQuery); // Menjalankan query eksekusi update

            peopleTable.setModel(setTable()); // Memperbarui (refresh) tabel
            clearForm();                      // Mengosongkan formulir
            JOptionPane.showMessageDialog(null, "Data berhasil diubah!"); // Pesan sukses update
        }
        catch (Exception ex) { // Menangkap error SQL/Exception
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengubah data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk menghapus data dari database (Delete)
    public void deleteData() {
        try {
            String id = idField.getText().trim(); // Mengambil ID dari field data yang dipilih

            // Menyusun string query DELETE dari database berdasarkan ID
            String sqlQuery = "DELETE FROM people WHERE id = '" + id + "'";
            database.insertUpdateDeleteQuery(sqlQuery); // Menjalankan query eksekusi hapus ke database

            peopleTable.setModel(setTable()); // Memperbarui (refresh) tabel setelah data terhapus
            clearForm();                      // Mengosongkan formulir input
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!"); // Pesan sukses hapus
        }
        catch (Exception ex) { // Menangkap error jika penghapusan gagal
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk mereset dan mengosongkan formulir input
    public void clearForm() {
        idField.setText("");               // Mengosongkan text field ID
        namaField.setText("");           // Mengosongkan text field Nama
        tahunField.setText("");         // Mengosongkan text field Tahun Lahir
        kategoriComboBox.setSelectedIndex(0);    // Mengembalikan ComboBox kategori ke indeks pertama ("???")
        jenisKelaminComboBox.setSelectedIndex(0); // Mengembalikan ComboBox jenis kelamin ke indeks pertama ("???")
        idField.setEnabled(true);       // Mengaktifkan kembali field ID agar bisa diisi data baru
        addButton.setText("Add");       // Mengembalikan teks tombol utama menjadi "Add"
        selectedIndex = -1;             // Mereset indeks pilihan baris tabel menjadi -1 (tidak ada yang dipilih)
    }

    public static void main(String[] args) {            // Method utama (titik awal) untuk menjalankan aplikasi Java
        PeopleMenu menu = new PeopleMenu();             // Membuat objek jendela utama aplikasi
        menu.setSize(750, 650);                         // Mengatur ukuran lebar dan tinggi jendela GUI
        menu.setLocationRelativeTo(null);               // Mengatur posisi jendela agar berada di tengah layar monitor
        menu.setContentPane(menu.mainPanel);            // Menetapkan panel utama ke konten jendela frame
        menu.getContentPane().setBackground(Color.WHITE); // Mengatur warna latar belakang konten menjadi putih
        menu.setVisible(true);                          // Mengatur agar jendela dapat terlihat oleh pengguna
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur agar program berhenti total saat tombol silang (X) ditekan
    }
}