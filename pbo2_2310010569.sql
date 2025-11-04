-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 04, 2025 at 09:07 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo2_2310010569`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_data_kk`
--

CREATE TABLE `tbl_data_kk` (
  `no_kk` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nama` varchar(150) NOT NULL,
  `rt` varchar(20) NOT NULL,
  `rw` varchar(20) NOT NULL,
  `no_telp` varchar(20) NOT NULL,
  `desa` varchar(150) NOT NULL,
  `tgl_input` varchar(150) NOT NULL,
  `id_user` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tbl_data_kk`
--

INSERT INTO `tbl_data_kk` (`no_kk`, `nama`, `rt`, `rw`, `no_telp`, `desa`, `tgl_input`, `id_user`) VALUES
('2310010', 'rendi', '11', '11', '0822', 'banjarmasin', '22 november', '01');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_data_ktp`
--

CREATE TABLE `tbl_data_ktp` (
  `nik` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nama` varchar(150) NOT NULL,
  `rt` varchar(20) NOT NULL,
  `rw` varchar(20) NOT NULL,
  `no_telp` varchar(20) NOT NULL,
  `desa` varchar(150) NOT NULL,
  `tgl_input` varchar(150) NOT NULL,
  `id_user` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tbl_data_ktp`
--

INSERT INTO `tbl_data_ktp` (`nik`, `nama`, `rt`, `rw`, `no_telp`, `desa`, `tgl_input`, `id_user`) VALUES
('2310010', 'ridho', '22', '22', '0822', 'banjarmasin', '22 november', '01');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_laporan_kk`
--

CREATE TABLE `tbl_laporan_kk` (
  `no_kk` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nama` varchar(100) NOT NULL,
  `rt` varchar(20) NOT NULL,
  `rw` varchar(20) NOT NULL,
  `no_telp` varchar(20) NOT NULL,
  `desa` varchar(100) NOT NULL,
  `tgl_input` varchar(150) NOT NULL,
  `id_user` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tbl_laporan_kk`
--

INSERT INTO `tbl_laporan_kk` (`no_kk`, `nama`, `rt`, `rw`, `no_telp`, `desa`, `tgl_input`, `id_user`) VALUES
('2310010', 'yudha', '11', '11', '0822', 'banjarmasin', '22 nov', '01');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_laporan_ktp`
--

CREATE TABLE `tbl_laporan_ktp` (
  `nik` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nama` varchar(150) NOT NULL,
  `rt` varchar(20) NOT NULL,
  `rw` varchar(20) NOT NULL,
  `no_telp` varchar(20) NOT NULL,
  `desa` varchar(150) NOT NULL,
  `tgl_input` varchar(150) NOT NULL,
  `id_user` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tbl_laporan_ktp`
--

INSERT INTO `tbl_laporan_ktp` (`nik`, `nama`, `rt`, `rw`, `no_telp`, `desa`, `tgl_input`, `id_user`) VALUES
('2310010', 'enggar', '2', '2', '082233', 'banjarmasin', '4 november', '01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_data_kk`
--
ALTER TABLE `tbl_data_kk`
  ADD PRIMARY KEY (`no_kk`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
