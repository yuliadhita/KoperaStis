-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 25, 2024 at 06:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `koperastis`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `idBarang` int(11) NOT NULL,
  `namaBarang` varchar(255) NOT NULL,
  `harga` double NOT NULL,
  `kuantitas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`idBarang`, `namaBarang`, `harga`, `kuantitas`) VALUES
(1, 'Atasan PDA Pria', 150000, 6),
(2, 'Atasan PDA Wanita', 155000, 25),
(3, 'Bawahan PDA Pria', 100000, 48),
(4, 'Bawahan PDA Wanita', 150000, 38),
(5, 'Ikat pinggang STIS', 50000, 18),
(8, 'Bivakmut Laki-Laki', 35000, 23),
(9, 'Bivakmut Perempuan', 40000, 40);

-- --------------------------------------------------------

--
-- Table structure for table `pengadaanbarang`
--

CREATE TABLE `pengadaanbarang` (
  `idPengadaan` int(11) NOT NULL,
  `nip` int(11) NOT NULL,
  `idBarang` int(11) NOT NULL,
  `kuantitas` int(11) NOT NULL,
  `supplier` varchar(255) NOT NULL,
  `tanggalPengadaan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengadaanbarang`
--

INSERT INTO `pengadaanbarang` (`idPengadaan`, `nip`, `idBarang`, `kuantitas`, `supplier`, `tanggalPengadaan`) VALUES
(1, 2, 1, 10, 'Pabrik 1', '2024-06-13'),
(2, 2, 2, 10, 'Pabrik 2', '2024-06-13'),
(3, 2, 3, 10, 'Pabrik 3', '2024-06-13'),
(4, 2, 4, 10, 'Pabrik 1', '2024-06-13'),
(5, 2, 5, 10, 'Pabrik 2', '2024-06-13');

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `idPengguna` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `noTelp` varchar(15) NOT NULL,
  `alamat` varchar(254) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`idPengguna`, `idUser`, `nama`, `noTelp`, `alamat`) VALUES
(-1, -1, 'Pembeli Offline', '000000000', 'Otista 64C'),
(4, 4, 'Nafa Yunia', '081327326666', 'Jl. Ayub 15A'),
(5, 5, 'Alifia Putri', '0813273454545', 'Jl. Sendiri No 19'),
(7, 7, 'Bimo Putra', '081311112444', 'Jl. Kita Bersama No 10'),
(8, 8, 'Yulia Dhita Cahya Kumala', '082321235954', 'Jl. Otista 44A'),
(9, 9, 'Ajun Zanuar', '084645456523', 'Jl. Jalan Kesini 12D'),
(19, 19, 'user', '085462315446', 'Jalan i aja');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `idRole` int(11) NOT NULL,
  `namaRole` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`idRole`, `namaRole`) VALUES
(1, 'Staff'),
(2, 'Kepala Gudang'),
(3, 'Kasir');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `nip` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idRole` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `tahunMasuk` date NOT NULL,
  `noTelp` varchar(15) NOT NULL,
  `alamat` varchar(254) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`nip`, `idUser`, `idRole`, `nama`, `tahunMasuk`, `noTelp`, `alamat`) VALUES
(-1, -1, 3, 'Self-Service', '2023-06-01', '7777777', 'Online'),
(1, 1, 1, 'Yulia Dhita Cahya Kumala', '2024-06-02', '081327322213', 'Jl. Otista 3 No 64'),
(2, 2, 1, 'Yuniar Dewi', '2023-06-13', '081447324489', 'Jl. Jaya Sejahtera No 90'),
(3, 3, 1, 'Asiyah', '2022-06-13', '089854665998', 'Jl. Demuk 13C'),
(4, 11, 1, 'Jung Jihun', '2023-06-13', '084563431559', 'Jl. Jaya No 19'),
(5, 10, 1, 'Siti Nur', '2023-05-13', '08887455659', 'Jl. Sudah Malam 09A'),
(8, 16, 1, 'staff', '2024-06-20', '087895864553', 'Jl. Dulu sana 21A');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `idTransaksi` int(11) NOT NULL,
  `nip` int(11) DEFAULT NULL,
  `idBarang` int(11) NOT NULL,
  `idPengguna` int(11) DEFAULT NULL,
  `tanggalPembelian` date NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`idTransaksi`, `nip`, `idBarang`, `idPengguna`, `tanggalPembelian`, `jumlah`) VALUES
(2, 3, 1, -1, '2023-06-13', 2),
(3, 3, 2, -1, '2023-06-07', 2),
(4, 3, 3, -1, '2023-06-15', 2),
(5, 3, 4, -1, '2023-06-15', 2),
(6, 3, 5, -1, '2023-06-17', 2),
(7, -1, 2, 7, '2023-06-13', 3),
(8, 8, 9, -1, '2024-06-13', 1),
(9, -1, 9, 19, '2024-06-25', 1),
(10, -1, 8, 19, '2024-06-25', 1),
(11, -1, 8, 19, '2024-06-25', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `username`, `password`) VALUES
(-1, 'Master', 'Master'),
(1, 'yulia', 'yulia'),
(2, 'yuniar', 'yuniar'),
(3, 'asiyah', 'asiyah'),
(4, 'nafa', 'nafa'),
(5, 'alifia', 'alifia'),
(7, 'bimo', 'bimo'),
(8, 'dhita', 'dhita'),
(9, 'ajun', 'ajun'),
(10, 'siti', 'siti'),
(11, 'jihun', 'jihun'),
(16, 'staff', 'staff'),
(19, 'user', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`idBarang`);

--
-- Indexes for table `pengadaanbarang`
--
ALTER TABLE `pengadaanbarang`
  ADD PRIMARY KEY (`idPengadaan`),
  ADD KEY `idObat` (`idBarang`),
  ADD KEY `nip` (`nip`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`idPengguna`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`idRole`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`nip`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idRole` (`idRole`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`idTransaksi`),
  ADD KEY `nip` (`nip`),
  ADD KEY `idObat` (`idBarang`),
  ADD KEY `idPengguna` (`idPengguna`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `idBarang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `pengadaanbarang`
--
ALTER TABLE `pengadaanbarang`
  MODIFY `idPengadaan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `idPengguna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `idRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `nip` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `idTransaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pengadaanbarang`
--
ALTER TABLE `pengadaanbarang`
  ADD CONSTRAINT `pengadaanbarang_ibfk_1` FOREIGN KEY (`idBarang`) REFERENCES `barang` (`idBarang`),
  ADD CONSTRAINT `pengadaanbarang_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `staff` (`nip`);

--
-- Constraints for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD CONSTRAINT `pengguna_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`);

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`idBarang`) REFERENCES `barang` (`idBarang`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`idPengguna`) REFERENCES `pengguna` (`idPengguna`),
  ADD CONSTRAINT `transaksi_ibfk_3` FOREIGN KEY (`nip`) REFERENCES `staff` (`nip`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
