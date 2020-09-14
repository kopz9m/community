CREATE TABLE `sys`.`new_table` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL,
  `description` TEXT NULL,
  `gmt_create` BIGINT NULL,
  `gmt_modified` BIGINT NULL,
  `creator` INT NULL,
  `comment_count` INT NULL,
  `view_count` INT NULL,
  `like_count` INT NULL,
  `tag` VARCHAR(256) NULL,
  PRIMARY KEY (`id`));
