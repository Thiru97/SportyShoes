CREATE DATABASE `sportyshoesdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

DROP table `products`;
DROP table `authorities`;
DROP table `users`;
DROP table `images`;



CREATE TABLE `products` (
  `image_id` int DEFAULT NULL,
  `price` int NOT NULL,
  `product_id` int NOT NULL AUTO_INCREMENT,
  `size` int NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `UK_aox0mf8fyerjsh7u4evl70r5e` (`image_id`),
  CONSTRAINT `FKn18ti2byyc5pbjr9cpjj7qkl9` FOREIGN KEY (`image_id`) REFERENCES `images` (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk91upmbueyim93v469wj7b2qh` (`user_id`),
  CONSTRAINT `FKk91upmbueyim93v469wj7b2qh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `image_url` varbinary(255) DEFAULT NULL,
  `image_data` blob,
  PRIMARY KEY (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products_order` (
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `FK8uycg3g3at1bfeemdfve4v4d` (`product_id`),
  CONSTRAINT `FK8uycg3g3at1bfeemdfve4v4d` FOREIGN KEY (`product_id`) REFERENCES `purchase_report` (`id`),
  CONSTRAINT `FKbburco49xjqrhmgaio3g9jrf1` FOREIGN KEY (`order_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `purchase_report` (
  `amount` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `purchase_date` date DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK31l0raummr9l2jcnse8b237bm` (`user_id`),
  CONSTRAINT `FK31l0raummr9l2jcnse8b237bm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_product` (
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`product_id`,`user_id`),
  KEY `FKctnnp6lvp5tkjb6fm0k0gmhns` (`user_id`),
  CONSTRAINT `FKctnnp6lvp5tkjb6fm0k0gmhns` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsu8beo945wpgaux2kwu677srs` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `user_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `sportyshoesdb`.`users`(`id`,`email`,`first_name`,`last_name`,`password`,`role`,
`user_url`)VALUES(1,admin@sportyshoes.com,John ,Doe,
$2a$12$SBFtHE78gzV8sBuqlLzgQ.KPIw7.SfB11pSZGROilSHb0HkcKYwzW,
ADMIN,http:localhost:8080/users/1);

INSERT INTO `sportyshoesdb`.`users`(`id`,`email`,`first_name`,`last_name`,`password`,`role`,
`user_url`)VALUES(2,janedoe@gmail.com,Jane ,Doe,
$2a$12$SBFtHE78gzV8sBuqlLzgQ.KPIw7.SfB11pSZGROilSHb0HkcKYwzW,
ADMIN,http:localhost:8080/users/2);


