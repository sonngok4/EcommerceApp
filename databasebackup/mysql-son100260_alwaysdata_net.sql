-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: mysql-son100260.alwaysdata.net
-- Generation Time: Dec 25, 2024 at 04:18 AM
-- Server version: 10.11.9-MariaDB
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `son100260_db`
--
CREATE DATABASE IF NOT EXISTS `son100260_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `son100260_db`;

-- --------------------------------------------------------

--
-- Table structure for table `cart_items`
--

CREATE TABLE `cart_items` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `quantity` int(11) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `cart_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  `updated_at` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `category_name`, `description`, `created_at`, `updated_at`) VALUES
(7, 'Chairs', 'Description of chairs category', '2024-11-28 05:32:54.019424', '2024-11-28 05:32:54.019424'),
(8, 'Beds', 'Description of beds category', '2024-11-28 05:32:54.019424', '2024-11-28 05:32:54.019424'),
(9, 'Furniture', 'Description of furniture category', '2024-11-28 05:32:54.019424', '2024-11-28 05:32:54.019424'),
(10, 'Home Deco', 'Description of home decoration category', '2024-11-28 05:32:54.019424', '2024-11-28 05:32:54.019424'),
(11, 'Dressings', 'Description of dressings category', '2024-11-28 05:32:54.019424', '2024-11-28 05:32:54.019424'),
(12, 'Tables', 'Description of tables category', '2024-11-28 05:32:54.019424', '2024-11-28 05:32:54.019424');

-- --------------------------------------------------------

--
-- Table structure for table `image_metadata`
--

CREATE TABLE `image_metadata` (
  `id` bigint(20) NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `original_file_name` varchar(255) DEFAULT NULL,
  `uploaded_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `order_status` varchar(255) NOT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `shipping_city` varchar(255) DEFAULT NULL,
  `shipping_country` varchar(255) DEFAULT NULL,
  `shipping_postal_code` varchar(255) DEFAULT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total` decimal(38,2) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(38,2) NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `order_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `stock_quantity` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  `updated_at` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `shop_id`, `category_id`, `product_name`, `image_url`, `price`, `stock_quantity`, `description`, `created_at`, `updated_at`) VALUES
(3, 1, 7, 'Comfortable Office Chairsfdsf', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769492/sofa_qgalhb.png', 199.99, 50, 'Ergonomic office chair for comfort during long working hours', '2024-11-28 05:36:54.669006', '2024-12-20 15:02:20.000000'),
(4, 1, 7, 'Adjustable Mesh Chair', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769492/product-1_gsw59l.png', 149.99, 30, 'Breathable mesh back chair with adjustable height and armrests', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(5, 1, 7, 'Luxury Recliner Chair', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-2_j6xznr.png', 299.99, 20, 'Luxury recliner chair with soft leather upholstery and adjustable settings', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(6, 1, 8, 'Queen Size Bed', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-2_j6xznr.png', 499.99, 15, 'Queen size bed with memory foam mattress and wooden frame', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(7, 1, 8, 'King Size Bed', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-3_awtgmu.png', 799.99, 10, 'Spacious king size bed with premium quality bedding and frame', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(8, 1, 8, 'Bunk Bed Set', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769492/sofa_qgalhb.png', 350.00, 25, 'Space-saving bunk bed set ideal for kids rooms or dormitories', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(9, 1, 9, 'Modern Sofa Set', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769492/product-1_gsw59l.png', 899.99, 40, 'Sleek, modern 3-piece sofa set perfect for living rooms', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(10, 1, 9, 'Wooden Coffee Table', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-2_j6xznr.png', 149.99, 60, 'Sturdy wooden coffee table with modern design', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(11, 1, 9, 'TV Stand with Storage', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-2_j6xznr.png', 229.99, 35, 'Elegant TV stand with ample storage for electronics and media', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(12, 1, 10, 'Decorative Wall Clock', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-3_awtgmu.png', 49.99, 100, 'Stylish wall clock to add charm to your living room', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(13, 1, 10, 'Indoor Plant Pot', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769492/sofa_qgalhb.png', 19.99, 200, 'Elegant plant pots that brighten up your indoor spaces', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(14, 1, 11, 'Full-Length Mirror', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769492/product-1_gsw59l.png', 79.99, 150, 'Elegant full-length mirror for bedroom or dressing area', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(15, 1, 11, 'Dressing Table Set', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1732769491/product-2_j6xznr.png', 399.99, 40, 'Complete dressing table set with drawers and mirror', '2024-11-28 05:36:54.669006', '2024-11-28 05:36:54.669006'),
(29, 1, 7, 'test24456', 'https://res.cloudinary.com/dexnxc3im/image/upload/v1734681817/furni-products/tdvgzyzxgza9ska1ohv3.webp', 3.00, 2, 'asdasda', '2024-12-20 15:03:33.000000', '2024-12-20 15:03:33.000000');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL,
  `comment` text DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `rating` int(11) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(2, 'ROLE_ADMIN'),
(3, 'ROLE_MODERATOR'),
(1, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `shopping_carts`
--

CREATE TABLE `shopping_carts` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shops`
--

CREATE TABLE `shops` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `owner_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shops`
--

INSERT INTO `shops` (`id`, `address`, `name`, `owner_id`) VALUES
(1, '123 Main St', 'Shop A', 2),
(2, '456 Elm St', 'Shop B', 9);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `username`, `password`, `address`, `phone_number`, `city`, `country`, `postal_code`, `created_at`, `updated_at`) VALUES
(1, 'johnny@xyz.com', 'John', 'Saudian', 'johnsaudian03', '$2a$10$YOPwqrITu38qgWpH4.RU9e66BjzPe5W8Y/5/QBV8Fp3BDvJrV02xW', NULL, NULL, NULL, NULL, NULL, '2024-11-14 10:40:47.000000', '2024-11-14 10:40:47.000000'),
(2, 'son@gmail.com', 'Son', 'Ngo', 'son123', '$2a$10$rXmG0gBWE5Al9Ssm74DdxOJAjEuq/EgOzuNxYxvTRNdcoux.k/3mi', NULL, NULL, NULL, NULL, NULL, '2024-12-06 11:12:09.000000', '2024-12-06 11:12:09.000000'),
(3, 'tobeym@gmail.com', 'Tobey', 'Marguire', 'tobeymg123', '$2a$10$xsya6nNKnqvi3.r8S6XGBOvDLM0I73byAYPN2tTVi72m7U5mNWnMW', NULL, NULL, NULL, NULL, NULL, '2024-12-06 11:17:22.000000', '2024-12-06 11:17:22.000000'),
(9, 'son100260@donga.edu.vn', 'Ngo', 'Van Son', 'son100260', '$2a$10$HtAMvg3tVAbK7qYZBYYBZ.6cuc43mNkLT33Hn6EuaPEBMy13GBa7O', NULL, NULL, NULL, NULL, NULL, '2024-12-09 13:43:13.000000', '2024-12-09 13:43:13.000000');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 1),
(9, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  ADD KEY `FKojy3ibx281qswho045bw4q0da` (`cart_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image_metadata`
--
ALTER TABLE `image_metadata`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  ADD KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK81gagumt0r8y3rmudcgpbk42l` (`order_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKf55t6sm19p5lrihq24a6knota` (`product_name`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  ADD KEY `FK7kp8sbhxboponhx3lxqtmkcoj` (`shop_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  ADD KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `shopping_carts`
--
ALTER TABLE `shopping_carts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKt5ao4h91q3su6hi9d2haxdr2t` (`user_id`);

--
-- Indexes for table `shops`
--
ALTER TABLE `shops`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKar5yyuartm46e1brh920fpfiv` (`name`),
  ADD UNIQUE KEY `UK6x3im56qg96va2stnwgkk7vtm` (`owner_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `image_metadata`
--
ALTER TABLE `image_metadata`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `shopping_carts`
--
ALTER TABLE `shopping_carts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shops`
--
ALTER TABLE `shops`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `FKojy3ibx281qswho045bw4q0da` FOREIGN KEY (`cart_id`) REFERENCES `shopping_carts` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK7kp8sbhxboponhx3lxqtmkcoj` FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`),
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `shopping_carts`
--
ALTER TABLE `shopping_carts`
  ADD CONSTRAINT `FK3iw2988ea60alsp0gnvvyt744` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `shops`
--
ALTER TABLE `shops`
  ADD CONSTRAINT `FKrduswa89ayj0poad3l70nag19` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
