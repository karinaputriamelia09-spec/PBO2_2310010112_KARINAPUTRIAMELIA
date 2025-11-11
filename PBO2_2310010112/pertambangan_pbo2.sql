-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 11 Nov 2025 pada 07.35
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pertambangan_pbo2`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_alat`
--

CREATE TABLE `jenis_alat` (
  `ID_Alat` int(11) NOT NULL,
  `Nama_Alat` varchar(100) NOT NULL,
  `Deskripsi` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jenis_alat`
--

INSERT INTO `jenis_alat` (`ID_Alat`, `Nama_Alat`, `Deskripsi`) VALUES
(1, 'Forklift', 'Alat untuk mengangkat dan memindahkan barang berat di gudang'),
(2, 'Conveyor Belt', 'Sabuk berjalan otomatis untuk memindahkan barang'),
(3, 'Crane', 'Alat pengangkat barang berukuran besar di area pelabuhan'),
(4, 'Hand Pallet', 'Alat bantu manual untuk mengangkat palet'),
(5, 'Packaging Machine', 'Mesin untuk pengemasan produk'),
(6, 'Mixer', 'Alat pencampur bahan produksi'),
(7, 'Label Printer', 'Mesin pencetak label produk'),
(8, 'Sealer', 'Alat penyegel kemasan'),
(9, 'Compressor', 'Alat penyedia udara tekan untuk mesin produksi'),
(10, 'Cooling Fan', 'Kipas pendingin area produksi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_produk`
--

CREATE TABLE `jenis_produk` (
  `ID_Produk` int(11) NOT NULL,
  `Jenis_Produk` varchar(100) NOT NULL,
  `Ukuran` varchar(50) DEFAULT NULL,
  `Harga` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jenis_produk`
--

INSERT INTO `jenis_produk` (`ID_Produk`, `Jenis_Produk`, `Ukuran`, `Harga`) VALUES
(1, 'Botol Plastik', '500 ml', 1500.00),
(2, 'Botol Kaca', '1 Liter', 3500.00),
(3, 'Kaleng Besar', '2 Liter', 5000.00),
(4, 'Kemasan Kardus', 'Medium', 2500.00),
(5, 'Kemasan Plastik', 'Small', 1000.00),
(6, 'Galon Air', '19 Liter', 20000.00),
(7, 'Drum Plastik', '50 Liter', 80000.00),
(8, 'Kantong Kertas', 'Large', 1200.00),
(9, 'Box Styrofoam', 'Medium', 4000.00),
(10, 'Botol Spray', '250 ml', 2500.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `ketersediaan_alat`
--

CREATE TABLE `ketersediaan_alat` (
  `ID_Status` int(11) NOT NULL,
  `ID_Alat` int(11) NOT NULL,
  `Status` varchar(50) DEFAULT NULL,
  `Tanggal_Penempatan` date DEFAULT NULL,
  `Tanggal_Selesai` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `ketersediaan_alat`
--

INSERT INTO `ketersediaan_alat` (`ID_Status`, `ID_Alat`, `Status`, `Tanggal_Penempatan`, `Tanggal_Selesai`) VALUES
(1, 1, 'Tersedia', '2025-01-05', NULL),
(2, 2, 'Digunakan', '2025-01-10', '2025-01-20'),
(3, 3, 'Maintenance', '2025-02-01', '2025-02-10'),
(4, 4, 'Tersedia', '2025-02-15', NULL),
(5, 5, 'Digunakan', '2025-03-01', '2025-03-05'),
(6, 6, 'Tersedia', '2025-03-10', NULL),
(7, 7, 'Rusak', '2025-03-20', '2025-03-25'),
(8, 8, 'Tersedia', '2025-04-01', NULL),
(9, 9, 'Maintenance', '2025-04-10', '2025-04-15'),
(10, 10, 'Tersedia', '2025-04-20', NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `wilayah`
--

CREATE TABLE `wilayah` (
  `ID_Wilayah` int(11) NOT NULL,
  `Wilayah_Penyimpanan` varchar(100) NOT NULL,
  `Tanggal_Penyimpanan` date DEFAULT NULL,
  `Tanggal_Pemindahan` date DEFAULT NULL,
  `Tanggal_Restok` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `wilayah`
--

INSERT INTO `wilayah` (`ID_Wilayah`, `Wilayah_Penyimpanan`, `Tanggal_Penyimpanan`, `Tanggal_Pemindahan`, `Tanggal_Restok`) VALUES
(1, 'Gudang Utara', '2025-01-01', '2025-01-10', '2025-02-01'),
(2, 'Gudang Selatan', '2025-01-05', NULL, '2025-02-10'),
(3, 'Gudang Timur', '2025-01-08', '2025-01-18', '2025-02-15'),
(4, 'Gudang Barat', '2025-01-12', NULL, '2025-02-20'),
(5, 'Area Produksi A', '2025-02-01', '2025-02-10', '2025-03-01'),
(6, 'Area Produksi B', '2025-02-05', NULL, '2025-03-05'),
(7, 'Gudang Pendingin', '2025-02-10', '2025-02-20', '2025-03-10'),
(8, 'Tempat Transit 1', '2025-02-15', NULL, '2025-03-15'),
(9, 'Tempat Transit 2', '2025-02-18', '2025-02-25', '2025-03-20'),
(10, 'Gudang Pusat', '2025-03-01', NULL, '2025-03-25');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `jenis_alat`
--
ALTER TABLE `jenis_alat`
  ADD PRIMARY KEY (`ID_Alat`);

--
-- Indeks untuk tabel `jenis_produk`
--
ALTER TABLE `jenis_produk`
  ADD PRIMARY KEY (`ID_Produk`);

--
-- Indeks untuk tabel `ketersediaan_alat`
--
ALTER TABLE `ketersediaan_alat`
  ADD PRIMARY KEY (`ID_Status`),
  ADD KEY `ID_Alat` (`ID_Alat`);

--
-- Indeks untuk tabel `wilayah`
--
ALTER TABLE `wilayah`
  ADD PRIMARY KEY (`ID_Wilayah`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `jenis_alat`
--
ALTER TABLE `jenis_alat`
  MODIFY `ID_Alat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `jenis_produk`
--
ALTER TABLE `jenis_produk`
  MODIFY `ID_Produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `ketersediaan_alat`
--
ALTER TABLE `ketersediaan_alat`
  MODIFY `ID_Status` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT untuk tabel `wilayah`
--
ALTER TABLE `wilayah`
  MODIFY `ID_Wilayah` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `ketersediaan_alat`
--
ALTER TABLE `ketersediaan_alat`
  ADD CONSTRAINT `ketersediaan_alat_ibfk_1` FOREIGN KEY (`ID_Alat`) REFERENCES `jenis_alat` (`ID_Alat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
