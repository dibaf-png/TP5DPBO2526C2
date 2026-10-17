# Janji
Saya Farah Diba Nur Malinda dengan NIM 2502083 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

---

# Fitur Utama
Pada program ini menyediakan 4 fungsi utama untuk pengoperasian data (CRUD):
1. **Tambah Data (Create / Add):** Menyimpan data baru (ID, Nama, Tahun Lahir, Kategori, dan Jenis Kelamin) langsung ke dalam database MySQL dengan pengecekan validasi input kosong dan pencegahan ID duplikat.
2. **Ubah Data (Update):** Memperbarui atau mengubah informasi data yang sudah ada di database berdasarkan ID yang dipilih dari tabel.
3. **Hapus Data (Delete):** Menghapus data terpilih dari database setelah melalui dialog konfirmasi keamanan (*confirmation dialog*).
4. **Batal/Reset (Cancel):** Mengosongkan seluruh kolom input, mengembalikan pilihan dropdown ke status awal, serta mengaktifkan kembali kolom ID melalui method `clearForm()`.

---

# Struktur Class
Pada program ini terdiri dari **3 kelas utama** yang saling mendukung, yaitu:

# 1. Class `Person`
Berfungsi sebagai model data atau wadah objek yang merepresentasikan atribut individual dari setiap orang yang dikelola.
- **Atribut:**
  - `String id` -> untuk menyimpan ID unik (*Primary Key*).
  - `String nama` -> untuk menyimpan data nama.
  - `String tahunLahir` -> untuk menyimpan data tahun kelahiran.
  - `String kategori` -> untuk menyimpan kategori status (Siswa, Mahasiswa, Dosen, Staff, Umum).
  - `String jenisKelamin` -> untuk menyimpan data jenis kelamin (Laki-laki / Perempuan).
- **Method:** Constructor untuk inisialisasi objek serta Getter dan Setter untuk masing-masing atribut.

# 2. Class `Database`
Berfungsi sebagai pengelola koneksi (*connection manager*) dan eksekusi query ke database MySQL (`db_people`).
- **Fungsi Utama:**
  - Menghubungkan aplikasi ke server MySQL XAMPP via JDBC Driver (`mysql-connector-j`).
  - `selectQuery(String query)` -> digunakan untuk mengeksekusi perintah query SQL `SELECT` dan mengembalikan hasilnya dalam bentuk `ResultSet`.
  - `insertUpdateDeleteQuery(String query)` -> digunakan untuk mengeksekusi perintah SQL pembawa perubahan data (`INSERT`, `UPDATE`, `DELETE`).

# 3. Class `PeopleMenu` extends `JFrame`
Berfungsi sebagai antarmuka grafis (GUI) utama aplikasi yang menampung seluruh komponen visual sekaligus mengatur logika interaksi pengguna dan pengolahan data.
- **Fungsi Utama:**
  - `insertData()` -> digunakan untuk memvalidasi input dan mengeksekusi query `INSERT` untuk menyimpan data baru ke database.
  - `updateData()` -> digunakan untuk memvalidasi input dan mengeksekusi query `UPDATE` untuk memperbarui data yang dipilih di database.
  - `deleteData()` -> digunakan untuk mengeksekusi query `DELETE` untuk menghapus data dari database setelah konfirmasi pengguna.
  - `clearForm()` -> untuk mengosongkan input form, mereset pilihan ComboBox, mengaktifkan kembali field ID, dan mereset status baris terpatri (`selectedIndex = -1`).
  - `setTable()` -> untuk membaca seluruh baris dari database MySQL melalui `selectQuery("SELECT * FROM people")` dan memperbarui isi `JTable` secara dinamis.

---

# Alur Program

1. **Tampilan Awal:** 

Saat aplikasi dijalankan, jendela utama (`PeopleMenu`) akan langsung memuat dan menampilkan seluruh data yang tersimpan di dalam database MySQL ke dalam `JTable` secara otomatis melalui pemanggilan method `setTable()`.

2. **Penambahan Data Baru (Add):** 

Pengguna mengisi formulir data diri dan memilih kategori serta jenis kelamin, lalu menekan tombol `Add` untuk memvalidasi kelengkapan isian serta memastikan ID belum terdaftar, sebelum akhirnya data disimpan secara permanen ke database dan tabel disegarkan.

3. **Perubahan Data (Update):** 

Pengguna wajib mengeklik salah satu baris data pada tabel terlebih dahulu untuk memuat datanya ke form (di mana kolom ID akan dikunci). Pengguna kemudian mengubah informasi yang diinginkan dan menekan tombol `Update` untuk memperbarui *record* terkait di database.

4. **Penghapusan Data (Delete):** 

Pengguna wajib memilih baris data yang ingin dihapus pada tabel terlebih dahulu lalu menekan tombol `Delete`, di mana sistem akan menampilkan dialog konfirmasi (*Yes/No Option*) sebelum mengeksekusi perintah penghapusan data dari database.

5. **Pembatalan dan Reset Form (Cancel):**

Pengguna dapat menekan tombol `Cancel` kapan saja untuk memanggil method `clearForm()` yang berfungsi mengosongkan seluruh kolom input, mengembalikan ComboBox ke pilihan awal, mengaktifkan kembali kolom ID, dan mereset indeks pilihan baris tabel (`selectedIndex = -1`).

---

# Dokumentasi

### 1. Tambah Data (Add)

<img width="800" height="449" alt="Add_data - Copy" src="https://github.com/user-attachments/assets/7da6ddaf-8d13-4509-941c-756a9b0136d6" />


### 2. Ubah Data (Update)

<img width="800" height="449" alt="Update_data" src="https://github.com/user-attachments/assets/5f758650-ce2a-40c7-8c4a-5c19eb34d1cf" />


### 3. Hapus Data (Delete)

<img width="800" height="449" alt="Delete_data" src="https://github.com/user-attachments/assets/5214dd7e-b733-456d-87a4-78125274b639" />

---

# Error Handling

Pada program ini juga terdapat beberapa penanganan error handling, yaitu:

### 1. Validasi Kolom Kosong

<img width="800" height="449" alt="Error_salah satu data tidak diisi" src="https://github.com/user-attachments/assets/969dc59a-d5d6-42be-94ac-4ea438be4f27" />

*Sistem akan menampilkan pop-up peringatan jika pengguna mencoba menambah atau mengubah data tanpa mengisi seluruh kolom atau jika ComboBox belum dipilih (masih `???`).*

### 2. Validasi ID Duplikat (Primary Key Check)

<img width="800" height="449" alt="Error_menggunakan ID yang sudah ada" src="https://github.com/user-attachments/assets/f32c3430-be58-4699-afd9-6ea7cebeb3e1" />

*Sistem melakukan pengecekan ke database saat proses penambahan data. Jika ID yang dimasukkan sudah terdaftar, muncul dialog error pencegahan duplikasi data.*

### 3. Validasi Pemilihan Data Tabel (Update & Delete)

<img width="800" height="449" alt="Update_data" src="https://github.com/user-attachments/assets/a5f0c3d9-fed8-4128-8d98-844fec119fbc" />

*Jika pengguna menekan tombol **Update** atau **Delete** tanpa memilih/mengeklik salah satu baris data pada tabel terlebih dahulu, sistem akan menampilkan peringatan untuk memilih data di tabel.*

---
