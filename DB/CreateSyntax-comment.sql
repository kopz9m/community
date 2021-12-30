-- CREATE语法 - 'comment'

CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL,
  `type` int NOT NULL,
  `commentator` bigint NOT NULL,
  `gmt_create` bigint NOT NULL,
  `gmt_modified` bigint NOT NULL,
  `like_count` bigint DEFAULT '0',
  `content` varchar(1024) DEFAULT NULL,
  `comment_count` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
