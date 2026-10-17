-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 17 Okt 2026 pada 16.09
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_people`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `people`
--

CREATE TABLE `people` (
  `id` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `tahun_lahir` varchar(10) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `jenis_kelamin` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `people`
--

INSERT INTO `people` (`id`, `nama`, `tahun_lahir`, `kategori`, `jenis_kelamin`) VALUES
('A342', 'Cakra Mahameru', '1989', 'Dosen', 'Laki-laki'),
('B112', 'Queen Aluna', '2007', 'Mahasiswa', 'Perempuan'),
('B676', 'Gusti Prakasa M.', '1978', 'Dosen', 'Laki-laki'),
('B719', 'Djiwa Pangestu', '1988', 'Umum', 'Laki-laki'),
('G458', 'Maharani Putri', '2006', 'Mahasiswa', 'Perempuan'),
('H567', 'Kelana Putri', '2018', 'Siswa', 'Perempuan');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `people`
--
ALTER TABLE `people`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
