public class Person {
    // Deklarasi atribut privat untuk menyimpan data Person
    private String id;
    private String nama;
    private String tahunLahir;
    private String kategori;
    private String jenisKelamin;

    // Konstruktor untuk menginisialisasi objek Person dengan nilai awal
    public Person(String id, String nama, String tahunLahir, String kategori, String jenisKelamin) {
        this.id = id;                     // Mengisi atribut id
        this.nama = nama;                 // Mengisi atribut nama
        this.tahunLahir = tahunLahir;     // Mengisi atribut tahunLahir
        this.kategori = kategori;         // Mengisi atribut kategori
        this.jenisKelamin = jenisKelamin; // Mengisi atribut jenisKelamin
    }

    // Getter untuk mengambil nilai id
    public String getId() {
        return id;
    }

    // Setter untuk mengubah nilai id
    public void setId(String id) {
        this.id = id;
    }

    // Getter untuk mengambil nilai nama
    public String getNama() {
        return nama;
    }

    // Setter untuk mengubah nilai nama
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter untuk mengambil nilai tahun lahir
    public String getTahunLahir() {
        return tahunLahir;
    }

    // Setter untuk mengubah nilai tahun lahir
    public void setTahunLahir(String tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    // Getter untuk mengambil nilai kategori
    public String getKategori() {
        return kategori;
    }
    // Setter untuk mengubah nilai kategori
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    // Getter untuk mengambil nilai jenis kelamin
    public String getJenisKelamin() {
        return jenisKelamin;
    }

    // Setter untuk mengubah nilai jenis kelamin
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}




