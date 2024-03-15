-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 15, 2024 lúc 11:05 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ezicall_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customers`
--

CREATE TABLE `customers` (
  `customer_id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customers`
--

INSERT INTO `customers` (`customer_id`, `address`, `email`, `full_name`, `gender`, `phone_number`) VALUES
('KH001', '123 Đường ABC, Quận 1, TP.HCM', 'nguyenvana@example.com', 'Nguyễn Văn A', 'MALE', '0123456789'),
('KH002', '456 Đường DEF, Quận 2, TP.HCM', 'tranthib@example.com', 'Trần Thị B', 'FEMALE', '0987654321'),
('KH003', '789 Đường GHI, Quận 3, TP.HCM', 'levanc@example.com', 'Lê Văn C', 'MALE', '0123987654'),
('KH004', '012 Đường JKL, Quận 4, TP.HCM', 'phamhongd@example.com', 'Phạm Hồng D', 'FEMALE', '0987123456');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee_activity_log`
--

CREATE TABLE `employee_activity_log` (
  `id` bigint(20) NOT NULL,
  `activity_type` varchar(255) NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employee_activity_log`
--

INSERT INTO `employee_activity_log` (`id`, `activity_type`, `timestamp`, `user_id`) VALUES
(1, 'OFFLINE', '2024-03-15 16:35:38.000000', 'US002');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `employee_notes` text DEFAULT NULL,
  `status` enum('OPEN','RESOLVED','CLOSED') NOT NULL,
  `assigned_to` varchar(255) NOT NULL,
  `customer_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` varchar(255) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phonenumber` varchar(255) NOT NULL,
  `role` enum('ADMIN','SUPPORTER','MARKETING') DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `birth_date`, `email`, `first_name`, `gender`, `last_name`, `password`, `phonenumber`, `role`, `username`) VALUES
('US001', '1997-04-08', 'jack97@gmail.com', 'trinh tran', 'FEMALE', 'phuong tuan', '$2a$10$ix2p7tcCC0M2.B0FrGFcquCSZQ1d5J80JiAuWkUlUzhkKrnIefF6q', '0338171052', 'ADMIN', 'jackkosai'),
('US002', '2002-04-08', 'bap2002@gmail.com', 'le', 'MALE', 'bao', '$2a$10$b43lGeqwh3.FcNmDKK77VeXLtPUjEHisZtYTubbnzyD7h7taW/IES', '0338171053', 'SUPPORTER', 'lebaodepzai'),
('US003', '2002-04-08', 'lebaodepzai@gmail.com', 'le', 'MALE', 'bao', '$2a$10$TqwwymN2Cwc1OLOBB2p3G.BJ.FwMzbzcQA74KebgygC2rGQGTc1F6', '0338171052', 'SUPPORTER', 'baodzvl1111');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`);

--
-- Chỉ mục cho bảng `employee_activity_log`
--
ALTER TABLE `employee_activity_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7ednlhkhp8o6breecgeup86aj` (`user_id`);

--
-- Chỉ mục cho bảng `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `FKmwsov8q6vll6krbx1co2ifp6t` (`assigned_to`),
  ADD KEY `FKi81xre2n3j3as1sp24j440kq1` (`customer_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `employee_activity_log`
--
ALTER TABLE `employee_activity_log`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `employee_activity_log`
--
ALTER TABLE `employee_activity_log`
  ADD CONSTRAINT `FK7ednlhkhp8o6breecgeup86aj` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FKi81xre2n3j3as1sp24j440kq1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  ADD CONSTRAINT `FKmwsov8q6vll6krbx1co2ifp6t` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
