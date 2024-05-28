-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 28 Bulan Mei 2024 pada 09.59
-- Versi server: 10.4.17-MariaDB
-- Versi PHP: 7.4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_cvatmm`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_cart`
--

CREATE TABLE `tb_cart` (
  `no` int(30) NOT NULL,
  `idproduk` varchar(30) NOT NULL,
  `pembeli` varchar(11) CHARACTER SET latin1 NOT NULL,
  `alamat` varchar(100) CHARACTER SET latin1 NOT NULL,
  `kontak` varchar(100) CHARACTER SET latin1 NOT NULL,
  `namaproduk` varchar(50) CHARACTER SET latin1 NOT NULL,
  `spesifikasi` varchar(100) CHARACTER SET latin1 NOT NULL,
  `jumlahbarang` int(50) NOT NULL,
  `totalharga` int(50) NOT NULL,
  `tanggaltransaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_cart`
--

INSERT INTO `tb_cart` (`no`, `idproduk`, `pembeli`, `alamat`, `kontak`, `namaproduk`, `spesifikasi`, `jumlahbarang`, `totalharga`, `tanggaltransaksi`) VALUES
(14, 'PRDK0001', 'A', 'AB', '1', 'Benih Kopi Robusta Stek Berakar Klon BP 939', 'Bersertifikat dan Berlabel', 12, 120000, '2024-05-22'),
(15, 'PRDK0002', '', '', '', 'Bibit 1', 'Bersertifikat dan Berlabel', 9, 5130000, '2024-05-22');

--
-- Trigger `tb_cart`
--
DELIMITER $$
CREATE TRIGGER `cancel` AFTER DELETE ON `tb_cart` FOR EACH ROW BEGIN
UPDATE tb_stokbarang SET
stokakhir = stokakhir + OLD.jumlahbarang
WHERE idproduk = OLD.idproduk;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `cancel2` AFTER DELETE ON `tb_cart` FOR EACH ROW BEGIN
DELETE FROM tb_transaksi
WHERE idproduk = OLD.idproduk;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `stokhabis` AFTER INSERT ON `tb_cart` FOR EACH ROW BEGIN
DELETE FROM tb_stokbarang
WHERE stokakhir = 0
AND
idproduk = NEW.idproduk;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_cartmou`
--

CREATE TABLE `tb_cartmou` (
  `no` int(30) NOT NULL,
  `nosurat` varchar(200) DEFAULT NULL,
  `idproduk` varchar(30) DEFAULT NULL,
  `pembeli` varchar(50) DEFAULT NULL,
  `satuankerja` varchar(200) DEFAULT NULL,
  `nip` varchar(200) DEFAULT NULL,
  `jabatan` varchar(200) DEFAULT NULL,
  `alamat` varchar(200) DEFAULT NULL,
  `paket` varchar(100) DEFAULT NULL,
  `namaproduk` varchar(50) DEFAULT NULL,
  `spesifikasi` varchar(100) DEFAULT NULL,
  `jumlahbarang` int(50) DEFAULT NULL,
  `ongkir` int(30) DEFAULT NULL,
  `harga` int(50) DEFAULT NULL,
  `totalharga` int(50) DEFAULT NULL,
  `tanggaltransaksi` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_cartmou`
--

INSERT INTO `tb_cartmou` (`no`, `nosurat`, `idproduk`, `pembeli`, `satuankerja`, `nip`, `jabatan`, `alamat`, `paket`, `namaproduk`, `spesifikasi`, `jumlahbarang`, `ongkir`, `harga`, `totalharga`, `tanggaltransaksi`) VALUES
(7, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0001', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Benih Kopi Robusta Stek Berakar Klon BP 939', 'Bersertifikat dan Berlabel', 8, 14400, 10000, 94400, '2024-05-22'),
(8, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0003', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Bibit 2', 'Bersertifikat dan Berlabel', 9, 16200, 610000, 5506200, '2024-05-22'),
(9, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0004', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Bibit 3', 'Bersertifikat dan Berlabel', 9, 16200, 3000000, 27016200, '2024-05-22'),
(10, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0005', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Bibit 4', 'Bersertifikat dan Berlabel', 9, 16200, 3300000, 29716200, '2024-05-22');

--
-- Trigger `tb_cartmou`
--
DELIMITER $$
CREATE TRIGGER `cancel_mou` AFTER DELETE ON `tb_cartmou` FOR EACH ROW BEGIN
UPDATE tb_stokbarang SET
stokakhir = stokakhir + OLD.jumlahbarang
WHERE idproduk = OLD.idproduk;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `cancel_mou2` AFTER DELETE ON `tb_cartmou` FOR EACH ROW BEGIN
DELETE FROM tb_mou
WHERE idproduk = OLD.idproduk;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `stokhabismou` AFTER INSERT ON `tb_cartmou` FOR EACH ROW BEGIN
DELETE FROM tb_stokbarang
WHERE stokakhir = 0
AND
idproduk = NEW.idproduk;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_login`
--

CREATE TABLE `tb_login` (
  `no` int(30) NOT NULL,
  `id` varchar(30) CHARACTER SET latin1 NOT NULL,
  `username` varchar(30) CHARACTER SET latin1 NOT NULL,
  `password` varchar(30) CHARACTER SET latin1 NOT NULL,
  `hak_akses` varchar(30) CHARACTER SET latin1 NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_login`
--

INSERT INTO `tb_login` (`no`, `id`, `username`, `password`, `hak_akses`, `tanggal`) VALUES
(1, 'ADM0001', 'Pak Bos', '1234', 'penuh', '2024-05-13'),
(2, 'OPR0001', 'Mas Operator', '1234', 'terbatas', '2024-05-13'),
(3, 'OPR0002', 'OP2', '1234', 'terbatas', '2024-05-13'),
(4, 'OPR0003', 'OP3', '1234', 'terbatas', '2024-05-13'),
(5, 'OPR0004', 'OP4', '1234', 'terbatas', '2024-05-13'),
(6, 'OPR0005', 'OP5', '1234', 'terbatas', '2024-05-14'),
(7, 'OPR0006', 'OP6', '1234', 'terbatas', '2024-05-14'),
(8, 'OPR0007', 'OP7', '1234', 'terbatas', '2024-05-14'),
(9, 'OPR0008', 'OP8', '1234', 'terbatas', '2024-05-14');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_mou`
--

CREATE TABLE `tb_mou` (
  `no` int(11) NOT NULL,
  `nosurat` varchar(200) DEFAULT NULL,
  `idproduk` varchar(30) DEFAULT NULL,
  `pembeli` varchar(50) DEFAULT NULL,
  `satuankerja` varchar(200) DEFAULT NULL,
  `nip` varchar(200) DEFAULT NULL,
  `jabatan` varchar(200) DEFAULT NULL,
  `alamat` varchar(200) DEFAULT NULL,
  `paket` varchar(100) DEFAULT NULL,
  `namaproduk` varchar(50) DEFAULT NULL,
  `spesifikasi` varchar(100) DEFAULT NULL,
  `jumlahbarang` int(50) DEFAULT NULL,
  `ongkir` int(30) DEFAULT NULL,
  `harga` int(50) NOT NULL,
  `totalharga` int(50) DEFAULT NULL,
  `tanggaltransaksi` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_mou`
--

INSERT INTO `tb_mou` (`no`, `nosurat`, `idproduk`, `pembeli`, `satuankerja`, `nip`, `jabatan`, `alamat`, `paket`, `namaproduk`, `spesifikasi`, `jumlahbarang`, `ongkir`, `harga`, `totalharga`, `tanggaltransaksi`) VALUES
(7, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0001', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Benih Kopi Robusta Stek Berakar Klon BP 939', 'Bersertifikat dan Berlabel', 8, 14400, 10000, 94400, '2024-05-22'),
(8, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0003', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Bibit 2', 'Bersertifikat dan Berlabel', 9, 16200, 610000, 5506200, '2024-05-22'),
(9, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0004', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Bibit 3', 'Bersertifikat dan Berlabel', 9, 16200, 3000000, 27016200, '2024-05-22'),
(10, 'SP : 0001 / PL.30 / 09 / 2024', 'PRDK0005', 'Tri Kusnari, S.P', 'KEMENTERIAN PERTANIAN - DIREKTORAT JENDERAL PERKEBUNAN', '198107302009121003', 'Pejabat Pembuat Komitmen Direktorat Jenderal Perkebunan', 'Kementerian Pertanian Gedung C Jl. RM. Harsono, RT.5/RW.7, Ragunan, Kec. Ps. Minggu, Kota Jakarta Selatan, DKI Jakarta', 'PENGADAAN BENIH KOPI UNTUK PERLUASAN KOPI ROBUSTA KAB. PAGAR ALAM 100 HA', 'Bibit 4', 'Bersertifikat dan Berlabel', 9, 16200, 3300000, 29716200, '2024-05-22');

--
-- Trigger `tb_mou`
--
DELIMITER $$
CREATE TRIGGER `insert_into_tb_cartmou` AFTER INSERT ON `tb_mou` FOR EACH ROW BEGIN
INSERT INTO tb_cartmou SET
no = NEW.no,
nosurat = NEW.nosurat,
idproduk = NEW.idproduk, 
pembeli = NEW.pembeli, 
nip = NEW.nip, 
jabatan = NEW.jabatan,
alamat = NEW.alamat,
paket = NEW.paket,
namaproduk = NEW.namaproduk,
spesifikasi = NEW.spesifikasi,
jumlahbarang = NEW.jumlahbarang,
ongkir = NEW.ongkir,
harga = NEW.harga,
totalharga = NEW.totalharga,
tanggaltransaksi = NEW.tanggaltransaksi;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_tb_stokbarang2` AFTER INSERT ON `tb_mou` FOR EACH ROW BEGIN
UPDATE tb_stokbarang SET
stokakhir = stokakhir - NEW.jumlahbarang
WHERE idproduk = NEW.idproduk;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_register`
--

CREATE TABLE `tb_register` (
  `no` int(30) NOT NULL,
  `id` varchar(30) CHARACTER SET latin1 NOT NULL,
  `username` varchar(30) CHARACTER SET latin1 NOT NULL,
  `password` varchar(30) CHARACTER SET latin1 NOT NULL,
  `hak_akses` varchar(30) CHARACTER SET latin1 NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_register`
--

INSERT INTO `tb_register` (`no`, `id`, `username`, `password`, `hak_akses`, `tanggal`) VALUES
(2, 'OPR0002', 'OP9', '1234', 'terbatas', '2024-05-14'),
(3, 'OPR0003', 'OP10', '1234', 'terbatas', '2024-05-14');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_stokbarang`
--

CREATE TABLE `tb_stokbarang` (
  `no` int(30) NOT NULL,
  `idproduk` varchar(30) NOT NULL,
  `supplier` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `namaproduk` varchar(50) NOT NULL,
  `spesifikasi` varchar(100) NOT NULL,
  `hargaawal` int(100) NOT NULL,
  `hargajual` int(100) NOT NULL,
  `ongkirawal` int(100) NOT NULL,
  `ongkirakhir` int(100) NOT NULL,
  `stokawal` int(30) NOT NULL,
  `stokakhir` int(30) NOT NULL,
  `totalawal` int(100) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_stokbarang`
--

INSERT INTO `tb_stokbarang` (`no`, `idproduk`, `supplier`, `alamat`, `kontak`, `namaproduk`, `spesifikasi`, `hargaawal`, `hargajual`, `ongkirawal`, `ongkirakhir`, `stokawal`, `stokakhir`, `totalawal`, `tanggal`) VALUES
(3, 'PRDK0003', 'CV. Bibit Brebes', 'Brebes, Jawa Tengah', '0896 7891 0123', 'Bibit 2', 'Bersertifikat dan Berlabel', 480000, 610000, 103500, 124200, 69, 60, 33223500, '2024-05-16'),
(4, 'PRDK0004', 'CV. Bibit Busan', 'Kabupaten, Busan', '0896 7891 0123', 'Bibit 3', 'Bersertifikat dan Berlabel', 1500000, 3000000, 103500, 124200, 69, 60, 103603500, '2024-05-16'),
(5, 'PRDK0005', 'CV. Bibit Laut Selatan', 'Laut Selatan, JEJU', '0896 7891 0123', 'Bibit 4', 'Bersertifikat dan Berlabel', 1650000, 3300000, 103500, 124200, 69, 60, 113953500, '2024-05-16'),
(6, 'PRDK0006', 'CV. Bibit Unggul', 'Kota Khusus, SEOUL', '0821 2345 6789', 'Bibit 5', 'Bersertifikat dan Berlabel', 17000, 20000, 7500000, 9000000, 5000, 5000, 92500000, '2024-05-16'),
(7, 'PRDK0007', 'CV. Bibit Unggul', 'Kota Khusus, SEOUL', '0821 2345 6789', 'Bibit 6', 'Bersertifikat dan Berlabel', 15000, 17500, 9000000, 10800000, 6000, 6000, 99000000, '2024-05-22'),
(8, 'PRDK0008', 'CV. Bibit Unggul', 'Kota Khusus, SEOUL', '0821 2345 6789', 'Bibit 7', 'Bersertifikat dan Berlabel', 8000, 10000, 18000000, 21600000, 12000, 12000, 114000000, '2024-05-22'),
(9, 'PRDK0009', 'CV. Bibit Unggul', 'Kota Khusus, SEOUL', '0821 2345 6789', 'Bibit 8', 'Bersertifikat dan Berlabel', 6500, 8200, 10500000, 12600000, 7000, 7000, 56000000, '2024-05-22'),
(10, 'PRDK0010', 'CV. Bibit Unggul', 'Kota Khusus, SEOUL', '0821 2345 6789', 'Bibit 9', 'Bersertifikat dan Berlabel', 7200, 8500, 9000000, 10800000, 6000, 6000, 52200000, '2024-05-22'),
(11, 'PRDK0011', 'CV. Bibit Unggul', 'Kota Khusus, SEOUL', '0821 2345 6789', 'Bibit 10', 'Bersertifikat dan Berlabel', 200000, 210000, 3000000, 3600000, 2000, 2000, 403000000, '2024-05-22'),
(12, 'PRDK0012', 'CV. Bibit Laut Selatan', 'Laut Selatan, JEJU', '0896 7891 0123', 'Bibit 11', 'Bersertifikat dan Berlabel', 1800000, 2015000, 150000, 180000, 100, 100, 180150000, '2024-05-22'),
(13, 'PRDK0013', 'CV. Bibit Laut Selatan', 'Laut Selatan, JEJU', '0896 7891 0123', 'Bibit 12', 'Bersertifikat dan Berlabel', 700000, 750000, 318000, 381600, 212, 212, 148718000, '2024-05-22'),
(14, 'PRDK0014', 'CV. Bibit Laut Selatan', 'Laut Selatan, JEJU', '0896 7891 0123', 'Bibit 13', 'Bersertifikat dan Berlabel', 658000, 728000, 450000, 540000, 300, 300, 197850000, '2024-05-22'),
(15, 'PRDK0015', 'CV. Bibit Busan', 'Kabupaten, Busan', '0896 7891 0123', 'Bibit 14', 'Bersertifikat dan Berlabel', 127500, 145500, 468000, 561600, 312, 312, 40248000, '2024-05-22'),
(16, 'PRDK0016', 'CV. Bibit Busan', 'Kabupaten, Busan', '0896 7891 0123', 'Bibit 15', 'Bersertifikat dan Berlabel', 1347000, 1472500, 634500, 761400, 423, 423, 570415500, '2024-05-22'),
(20, 'PRDK0017', 'CV. Tani Jaya Sejahtera', 'Mblitang', '0856 6677 8899', 'Torabika CAPUCINO', 'Bersertifikat dan Berlabel', 5000, 6500, 4500000, 5400000, 2000, 3000, 19500000, '2024-05-26');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_transaksi`
--

CREATE TABLE `tb_transaksi` (
  `no` int(30) NOT NULL,
  `idproduk` varchar(30) DEFAULT NULL,
  `pembeli` varchar(11) DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `kontak` varchar(100) DEFAULT NULL,
  `namaproduk` varchar(50) DEFAULT NULL,
  `spesifikasi` varchar(100) DEFAULT NULL,
  `jumlahbarang` int(50) DEFAULT NULL,
  `totalharga` int(50) DEFAULT NULL,
  `tanggaltransaksi` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_transaksi`
--

INSERT INTO `tb_transaksi` (`no`, `idproduk`, `pembeli`, `alamat`, `kontak`, `namaproduk`, `spesifikasi`, `jumlahbarang`, `totalharga`, `tanggaltransaksi`) VALUES
(14, 'PRDK0001', 'A', 'AB', '1', 'Benih Kopi Robusta Stek Berakar Klon BP 939', 'Bersertifikat dan Berlabel', 12, 120000, '2024-05-22'),
(15, 'PRDK0002', '', '', '', 'Bibit 1', 'Bersertifikat dan Berlabel', 9, 5130000, '2024-05-22');

--
-- Trigger `tb_transaksi`
--
DELIMITER $$
CREATE TRIGGER `insert_into_tb_cart` AFTER INSERT ON `tb_transaksi` FOR EACH ROW BEGIN
INSERT INTO tb_cart SET
no = NEW.no,
idproduk = NEW.idproduk, 
pembeli = NEW.pembeli,
alamat = NEW.alamat,
kontak = NEW.kontak,
namaproduk = NEW.namaproduk,
spesifikasi = NEW.spesifikasi,
jumlahbarang = NEW.jumlahbarang,
totalharga = NEW.totalharga,
tanggaltransaksi = NEW.tanggaltransaksi;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_tb_stokbarang` AFTER INSERT ON `tb_transaksi` FOR EACH ROW BEGIN
UPDATE tb_stokbarang SET
stokakhir = stokakhir - NEW.jumlahbarang
WHERE idproduk = NEW.idproduk;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_validasistok`
--

CREATE TABLE `tb_validasistok` (
  `no` int(100) NOT NULL,
  `idproduk` varchar(30) CHARACTER SET latin1 NOT NULL,
  `supplier` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `namaproduk` varchar(50) NOT NULL,
  `spesifikasi` varchar(100) CHARACTER SET latin1 NOT NULL,
  `hargaawal` int(100) NOT NULL,
  `hargajual` int(100) NOT NULL,
  `ongkirawal` int(100) NOT NULL,
  `ongkirakhir` int(100) NOT NULL,
  `stokawal` int(30) NOT NULL,
  `stokakhir` int(30) NOT NULL,
  `totalawal` int(100) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_validasistok`
--

INSERT INTO `tb_validasistok` (`no`, `idproduk`, `supplier`, `alamat`, `kontak`, `namaproduk`, `spesifikasi`, `hargaawal`, `hargajual`, `ongkirawal`, `ongkirakhir`, `stokawal`, `stokakhir`, `totalawal`, `tanggal`) VALUES
(1, 'PRDK0001', 'CV. Tani Jaya Sejahtera', 'Mblitang', '0856 6677 8899', 'Torabika CAPUCINO', 'Bersertifikat dan Berlabel', 5000, 6500, 4500000, 5400000, 2000, 3000, 19500000, '2024-05-26');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_cart`
--
ALTER TABLE `tb_cart`
  ADD PRIMARY KEY (`no`),
  ADD KEY `idproduk` (`idproduk`);

--
-- Indeks untuk tabel `tb_cartmou`
--
ALTER TABLE `tb_cartmou`
  ADD PRIMARY KEY (`no`),
  ADD KEY `idproduk` (`idproduk`);

--
-- Indeks untuk tabel `tb_login`
--
ALTER TABLE `tb_login`
  ADD PRIMARY KEY (`no`);

--
-- Indeks untuk tabel `tb_mou`
--
ALTER TABLE `tb_mou`
  ADD PRIMARY KEY (`no`),
  ADD KEY `idproduk` (`idproduk`);

--
-- Indeks untuk tabel `tb_register`
--
ALTER TABLE `tb_register`
  ADD PRIMARY KEY (`no`);

--
-- Indeks untuk tabel `tb_stokbarang`
--
ALTER TABLE `tb_stokbarang`
  ADD PRIMARY KEY (`no`),
  ADD KEY `idproduk` (`idproduk`);

--
-- Indeks untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  ADD PRIMARY KEY (`no`),
  ADD KEY `idproduk` (`idproduk`) USING BTREE;

--
-- Indeks untuk tabel `tb_validasistok`
--
ALTER TABLE `tb_validasistok`
  ADD PRIMARY KEY (`no`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_cart`
--
ALTER TABLE `tb_cart`
  MODIFY `no` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `tb_cartmou`
--
ALTER TABLE `tb_cartmou`
  MODIFY `no` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `tb_login`
--
ALTER TABLE `tb_login`
  MODIFY `no` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `tb_mou`
--
ALTER TABLE `tb_mou`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `tb_register`
--
ALTER TABLE `tb_register`
  MODIFY `no` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `tb_stokbarang`
--
ALTER TABLE `tb_stokbarang`
  MODIFY `no` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `tb_transaksi`
--
ALTER TABLE `tb_transaksi`
  MODIFY `no` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `tb_validasistok`
--
ALTER TABLE `tb_validasistok`
  MODIFY `no` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
