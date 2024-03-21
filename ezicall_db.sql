-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 20, 2024 lúc 03:41 PM
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
('KH004', '012 Đường JKL, Quận 4, TP.HCM', 'phamhongd@example.com', 'Phạm Hồng D', 'FEMALE', '0987123456'),
('KH006', 'Ha Noi', 'trnlh@gmail.com', 'Tranh Luu Huong', 'MALE', '0456987321'),
('KH007', 'Ha Noi', 'bao@gmail.com', 'Le Duc Bao', 'MALE', '0531456846'),
('KH008', 'Ha Noi', 'tragiang@gmail.com', 'Nguyen Tra Giang', 'FEMALE', '0333146591'),
('KH009', 'Ha Noi', 'tragiang1@gmail.com', 'Nguyen Tra Giang', 'FEMALE', '0333146592');

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
(1, 'OFFLINE', '2024-03-16 00:20:11.000000', 'US002'),
(2, 'OFFLINE', '2024-03-16 23:48:45.000000', 'US003'),
(3, 'OFFLINE', '2024-03-17 12:09:15.000000', 'US003'),
(4, 'OFFLINE', '2024-03-17 12:55:53.000000', 'US003'),
(5, 'OFFLINE', '2024-03-17 13:22:26.000000', 'US003'),
(6, 'OFFLINE', '2024-03-17 14:28:19.000000', 'US003'),
(7, 'OFFLINE', '2024-03-17 14:31:44.000000', 'US003'),
(8, 'OFFLINE', '2024-03-17 14:33:09.000000', 'US003'),
(9, 'OFFLINE', '2024-03-17 15:05:30.000000', 'US003'),
(10, 'OFFLINE', '2024-03-17 15:36:43.000000', 'US003'),
(11, 'OFFLINE', '2024-03-17 15:40:17.000000', 'US003'),
(12, 'OFFLINE', '2024-03-18 08:42:48.000000', 'US002'),
(13, 'OFFLINE', '2024-03-19 17:38:33.000000', 'US002'),
(14, 'OFFLINE', '2024-03-20 09:00:03.000000', 'US002'),
(15, 'OFFLINE', '2024-03-20 09:32:37.000000', 'US002'),
(16, 'OFFLINE', '2024-03-20 09:48:19.000000', 'US002'),
(17, 'OFFLINE', '2024-03-20 15:41:38.000000', 'US003');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notes`
--

CREATE TABLE `notes` (
  `note_id` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `noted_at` datetime(6) NOT NULL,
  `ticket_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `notes`
--

INSERT INTO `notes` (`note_id`, `content`, `noted_at`, `ticket_id`) VALUES
('TK007001', 'hỏng chức năng lấy đá', '2024-03-20 11:44:17.000000', 'TK007'),
('TK007002', 'hỏng đèn báo', '2024-03-20 11:44:41.000000', 'TK007'),
('TK007003', 'không khử mùi', '2024-03-20 13:43:51.000000', 'TK007'),
('TK007004', 'chập IC', '2024-03-20 14:26:53.000000', 'TK007'),
('TK008001', 'không vào điện', '2024-03-20 14:18:37.000000', 'TK008'),
('TK008002', 'hỏng chức năng vắt', '2024-03-20 14:19:48.000000', 'TK008'),
('TK008003', 'chập IC', '2024-03-20 14:26:32.000000', 'TK008'),
('TK009001', 'không nhận điều khiển', '2024-03-20 14:27:58.000000', 'TK009'),
('TK010001', 'chập IC', '2024-03-20 16:07:31.000000', 'TK010'),
('TK011001', 'không nhận điều khiển', '2024-03-20 16:13:31.000000', 'TK011'),
('TK012001', 'chập IC', '2024-03-20 16:14:47.000000', 'TK012'),
('TK013001', 'chập IC', '2024-03-20 17:19:59.000000', 'TK013'),
('TK014001', 'không nhận điều khiển', '2024-03-20 17:20:58.000000', 'TK014');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `status` enum('OPEN','CLOSED') NOT NULL,
  `title` varchar(255) NOT NULL,
  `assigned_to` varchar(255) NOT NULL,
  `customer_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tickets`
--

INSERT INTO `tickets` (`ticket_id`, `created_at`, `status`, `title`, `assigned_to`, `customer_id`) VALUES
('2', '2024-03-17 11:11:53.000000', 'CLOSED', 'Hỏng vòi nước', 'US003', 'KH006'),
('3', '2024-03-17 12:53:38.000000', 'CLOSED', 'Hỏng đèn trần', 'US003', 'KH006'),
('4', '2024-03-19 02:43:24.000000', 'CLOSED', 'Hỏng tivi', 'US001', 'KH006'),
('5', '2024-03-19 15:25:04.000000', 'CLOSED', 'Hỏng máy tính', 'US001', 'KH006'),
('6', '2024-03-20 01:42:15.000000', 'CLOSED', 'Sơn tường', 'US001', 'KH006'),
('TK007', '2024-03-20 10:54:31.000000', 'CLOSED', 'Tủ lạnh', 'US001', 'KH006'),
('TK008', '2024-03-20 14:18:37.000000', 'OPEN', 'Máy giặt', 'US001', 'KH006'),
('TK009', '2024-03-20 14:27:58.000000', 'OPEN', 'Điều hòa', 'US001', 'KH006'),
('TK010', '2024-03-20 16:07:31.000000', 'OPEN', 'Máy rửa bát', 'US001', 'KH007'),
('TK011', '2024-03-20 16:13:31.000000', 'OPEN', 'Quạt trần', 'US001', 'KH007'),
('TK012', '2024-03-20 16:14:47.000000', 'OPEN', 'Lò vi sóng', 'US001', 'KH008'),
('TK013', '2024-03-20 17:19:59.000000', 'OPEN', 'Lò vi sóng', 'US001', 'KH009'),
('TK014', '2024-03-20 17:20:58.000000', 'OPEN', 'Quạt trần', 'US001', 'KH007');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','SUPPORTER','MARKETING') DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `password`, `role`, `username`) VALUES
('US001', 'jack', '$2a$10$3OPX27.m1IHdRhWP6NILzuZmXlVgHqQ39YH0i3u4ttI.ttGqGmlSq', 'ADMIN', 'jackkosai'),
('US002', 'le duc bao', '$2a$10$a54D/sDbp0Ygo8fNXlfoTuuOSueQ03530nFZzAxq/W7W5kjamP7ci', 'MARKETING', 'ducbaodepzaivl'),
('US003', 'tranh luu huong', '$2a$10$9oR2/xo4BT6D0m2rvD02zeIshzJN5gjMtRE9E6oIHQ8Nv8LXKl7LS', 'MARKETING', 'trnluuhuong');

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
-- Chỉ mục cho bảng `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `FK6t6b86e5gjrpg2rx5if0ikudf` (`ticket_id`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `employee_activity_log`
--
ALTER TABLE `employee_activity_log`
  ADD CONSTRAINT `FK7ednlhkhp8o6breecgeup86aj` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `FK6t6b86e5gjrpg2rx5if0ikudf` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`);

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
