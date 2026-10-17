import java.sql.*; // Mengimpor seluruh pustaka JDBC (Java Database Connectivity) untuk koneksi SQL

public class Database { // Mendeklarasikan kelas Database
    private Connection connection; // Variabel privat untuk menyimpan objek koneksi database
    private Statement statement;   // Variabel privat untuk mengeksekusi perintah/query SQL

    public Database() { // Konstruktor kelas Database yang dijalankan saat objek dibuat
        try { // Blok try untuk menangani potensi error/exception saat koneksi
            // Menghubungkan program Java ke database MySQL (nama: db_people, user: root, password: kosong)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_people", "root", "");

            // Membuat objek statement dari koneksi untuk menjalankan query SQL
            statement = connection.createStatement();
        } catch (SQLException e) { // Menangkap error tipe SQLException jika koneksi gagal
            throw new RuntimeException(e); // Melempar runtime exception jika terjadi kesalahan
        }
    }

    // Method khusus untuk mengeksekusi query pengambilan data (SELECT)
    public ResultSet selectQuery(String sql) {
        try {
            statement.executeQuery(sql); // Menjalankan query SELECT pada database
            return statement.getResultSet(); // Mengembalikan hasil data tabel dari database ke bentuk ResultSet
        } catch (SQLException e) {
            throw new RuntimeException(e); // Menangkap error SQL jika query gagal dijalankan
        }
    }

    // Method khusus untuk mengeksekusi query perubahan data (INSERT, UPDATE, DELETE)
    public int insertUpdateDeleteQuery(String sql) {
        try {
            // Menjalankan query update/insert/delete dan mengembalikan jumlah baris yang terpengaruh (integer)
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e); // Menangkap error SQL jika eksekusi gagal
        }
    }

    // Method getter untuk mengambil nilai objek statement jika diperlukan di luar kelas
    public Statement getStatement() {
        return statement; // Mengembalikan objek statement
    }
}
