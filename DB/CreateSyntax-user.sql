-- CREATE语法 - 'user'

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL COMMENT 'GitHub userId',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '新用户',
  `token` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `bio` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `avatar_url` varchar(200) DEFAULT NULL,
  `gmt_create` bigint DEFAULT NULL,
  `gmt_modified` bigint DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
