-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 18, 2024 lúc 07:38 AM
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
('KH005', '345 Đường MNO, Quận 5, TP.HCM', 'hoangminhe@example.com', 'Hoàng Minh E', 'MALE', '0345678912'),
('KH006', 'Ha Noi', 'trnlh@gmail.com', 'Tranh Luu Huong', 'MALE', '0456987321');

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
(12, 'OFFLINE', '2024-03-18 08:42:48.000000', 'US002');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notes`
--

CREATE TABLE `notes` (
  `note_id` bigint(20) NOT NULL,
  `content` text NOT NULL,
  `noted_at` datetime(6) NOT NULL,
  `ticket_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `notes`
--

INSERT INTO `notes` (`note_id`, `content`, `noted_at`, `ticket_id`) VALUES
(2, 'vòi nước rỉ nước đã hai ngày', '2024-03-17 11:11:53.000000', 2),
(3, 'bóng đèn trần phòng khách bị cháy', '2024-03-17 12:53:38.000000', 3),
(4, 'gioăng vòi nước bị đứt nên chảy nước', '2024-03-17 14:17:59.000000', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` bigint(20) NOT NULL,
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
(2, '2024-03-17 11:11:53.000000', 'CLOSED', 'Hỏng vòi nước', 'US003', 'KH006'),
(3, '2024-03-17 12:53:38.000000', 'OPEN', 'Hỏng đèn trần', 'US003', 'KH006');

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
('US003', 'tranh luu huong', '$2a$10$9WJMCcb3VL8uRqFfB9PMguV4b22ly/wPE1fLe2BDfMxY/V/7XUb.6', 'SUPPORTER', 'trluuhuong'),
('US004', 'tranh luu huong', '$2a$10$xHX.6zYgBpIG59ukGpONAe0e2eHwGi2Nw1NFulRHL.VGmzMnUVHxu', 'MARKETING', 'trluuhuong');

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `notes`
--
ALTER TABLE `notes`
  MODIFY `note_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
