-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 13, 2024 lúc 04:53 PM
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
  `customer_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee_activity_log`
--

CREATE TABLE `employee_activity_log` (
  `id` bigint(20) NOT NULL,
  `activity_type` varchar(255) NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employee_activity_log`
--

INSERT INTO `employee_activity_log` (`id`, `activity_type`, `timestamp`, `user_id`) VALUES
(1, 'OFFLINE', '2024-03-13 09:01:40.000000', 2),
(2, 'OFFLINE', '2024-03-13 09:54:13.000000', 2),
(3, 'OFFLINE', '2024-03-13 11:56:19.000000', 2),
(4, 'OFFLINE', '2024-03-13 15:27:14.000000', 2),
(5, 'OFFLINE', '2024-03-13 15:45:43.000000', 2),
(6, 'OFFLINE', '2024-03-13 15:46:31.000000', 2),
(7, 'OFFLINE', '2024-03-13 20:19:53.000000', 2),
(8, 'OFFLINE', '2024-03-13 20:34:06.000000', 2),
(9, 'OFFLINE', '2024-03-13 20:39:22.000000', 2),
(10, 'OFFLINE', '2024-03-13 20:46:02.000000', 2),
(11, 'OFFLINE', '2024-03-13 21:03:40.000000', 2),
(12, 'OFFLINE', '2024-03-13 21:21:49.000000', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `employee_notes` text DEFAULT NULL,
  `status` enum('OPEN','RESOLVED','CLOSED') NOT NULL,
  `assigned_to` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phonenumber` varchar(255) NOT NULL,
  `role` enum('ADMIN','EMPLOYEE') DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `birth_date`, `email`, `first_name`, `gender`, `last_name`, `password`, `phonenumber`, `role`, `username`) VALUES
(1, '2002-04-08', 'bao08042002@gmail.com', 'le', 'MALE', 'bao', '$2a$10$PUPVgOVGdPflPGy95I0xp.rZ2sJAwn0DgsM4QZ51.5v45nmjzF6wa', '0338171052', 'ADMIN', 'lebaodepzai'),
(2, '2002-04-08', 'leducbao0804@gmail.com', 'le', 'OTHER', 'bao', '$2a$10$rjWtzNIiyMMtxvU2y1249uFhy0ZmqZMRdm/gqOgjst0xpaRQ2K5Ru', '0338171052', 'EMPLOYEE', 'baodepzaivl');

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
-- AUTO_INCREMENT cho bảng `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `employee_activity_log`
--
ALTER TABLE `employee_activity_log`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
