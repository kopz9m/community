-- CREATE语法 - 'notification'

CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `notifier` bigint NOT NULL,
  `receiver` bigint NOT NULL,
  `outerid` bigint NOT NULL,
  `type` int NOT NULL,
  `gmt_create` bigint NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  `notifier_name` varchar(100) DEFAULT NULL,
  `outer_title` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
